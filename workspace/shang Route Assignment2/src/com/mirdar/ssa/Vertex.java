package com.mirdar.ssa;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	public String name;
	public List<Edge> adj;
	public double dist;
	public Vertex prev;
	//Dijkstra算法中用来检测该该顶点是否被访问过
	public int scratch;
	
	public Vertex(String nm)
	{
		name = nm;
		adj = new LinkedList<Edge>();
		reset();
	}
	
	public void reset()
	{
		dist = Graph.INFINITY;
		prev = null;
		scratch = 0;
	}
}
