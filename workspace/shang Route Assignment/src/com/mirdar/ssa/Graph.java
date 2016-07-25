package com.mirdar.ssa;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class Graph {

	public static final double INFINITY = Double.MAX_VALUE;
	public Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

	public void addEdge(String sourceName, String destName, double cost)
	{
		Vertex v = getVertex(sourceName);
		Vertex w = getVertex(destName);
		v.adj.add(new Edge(w, cost));
		// no direction edge
		w.adj.add(new Edge(v, cost));
	}

	// 通过查询某个vertex打印出其cost与最短路径
	public Vertex printPath(String destName)
	{
		Vertex w = vertexMap.get(destName);
		if (w == null)
			throw new NoSuchElementException();
		else if (w.dist == INFINITY)
			System.out.println(destName + "is unreachable");
		else
		{
			System.out.println("(Cost is: " + w.dist + ")");
			printPath(w);
			System.out.println();
		}
		return w;
	}
	public Vertex cleanVertex(String destName)
	{
		Vertex w = vertexMap.get(destName);
		return w;
	}

	public void dijkstra(String startName)
	{
		// 优先级队列
		PriorityQueue<Path> pq = new PriorityQueue<Path>();

		Vertex start = vertexMap.get(startName);
		if (start == null)
			throw new NoSuchElementException("Start vertex not found.");
		clearAll();
		pq.add(new Path(start, 0));
		start.dist = 0;

		int nodesSeen = 0;
		while (!pq.isEmpty() && nodesSeen < vertexMap.size())
		{
			Path vrec = pq.remove();
			Vertex v = vrec.dest;
			if (v.scratch != 0)
				continue;
			v.scratch = 1;
			nodesSeen++;

			for (Edge e : v.adj)
			{
				if (e.dest.scratch != 0)
					continue;
				Vertex w = e.dest;
				double cvw = e.cost;

				if (cvw < 0)
					throw new GraphException("Graph has negative edges.");

				if (w.dist > v.dist + cvw)
				{
					w.dist = v.dist + cvw;
					w.prev = v;
					pq.add(new Path(w, w.dist));
				}
			}
		}
	}

	public double BFS(String startName, String endName)
	{
		PriorityQueue<Path> pq = new PriorityQueue<Path>();
		double bestW = Double.MAX_VALUE;
		Vertex start = vertexMap.get(startName);
		if (start == null)
			throw new NoSuchElementException("Start vertex not found.");
		clearAll();
		pq.add(new Path(start, 0));
		start.dist = 0;
		if (startName.equals(endName))
		{
			return 0;
		}

		while (!pq.isEmpty())
		{
			Path vrec = pq.remove();
			Vertex v = vrec.dest;
			// 界定
			if (vrec.cost >= bestW && bestW != Double.MAX_VALUE)
				break;
			if (v.scratch != 0)
				continue;
			v.scratch = 1;
			// 分支
			for (Edge e : v.adj)
			{
				if (e.dest.scratch != 0)
					continue;
				Vertex w = e.dest;
				double cvw = e.cost;

				if (cvw < 0)
					throw new GraphException("Graph has negative edges.");

				if (w.dist > v.dist + cvw)
				{
					w.dist = v.dist + cvw;
					w.prev = v;
					if (w.name.equals(endName) && w.dist < bestW)
					{
						bestW = w.dist;
					}
					pq.add(new Path(w, w.dist));
				}
			}
		}

		return bestW;
	}

	private Vertex getVertex(String vertexName)
	{
		Vertex v = vertexMap.get(vertexName);
		if (v == null)
		{
			v = new Vertex(vertexName);
			vertexMap.put(vertexName, v);
		}

		return v;
	}

	// 递归打印最短路径
	private void printPath(Vertex dest)
	{
		if (dest.prev != null)
		{
			printPath(dest.prev);
			System.out.println(" to ");
		}
		System.out.println(dest.name);
	}
	/*
	 * printPath的非递归版本 private void printPath(Vertex dest) { while(dest.prev !=
	 * null) { System.out.println(dest.name); dest =dest.prev;
	 * System.out.println(" <- "); }
	 * 
	 * }
	 * 
	 */

	// initializes operation
	private void clearAll()
	{
		for (Vertex v : vertexMap.values())
			v.reset();
	}

	@SuppressWarnings("serial")
	class GraphException extends RuntimeException {
		public GraphException(String name)
		{
			super(name);
		}
	}
}
