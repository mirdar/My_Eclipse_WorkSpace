package com.mirdar.ssa;

public class Edge {

	public Vertex dest; //下一个节点
	public double cost; //路段长度
	public Edge(Vertex d, double c)
	{
		dest = d;
		cost = c;
	}
}
