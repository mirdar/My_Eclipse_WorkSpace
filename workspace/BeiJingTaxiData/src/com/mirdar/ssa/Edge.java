package com.mirdar.ssa;

public class Edge {

	public Vertex dest; //��һ���ڵ�
	public double cost; //·�γ���
	public Edge(Vertex d, double c)
	{
		dest = d;
		cost = c;
	}
}
