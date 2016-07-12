package com.mirdar.ssa;

public class Path implements Comparable<Path>{

	public Vertex dest;
	public double cost;
	
	public Path(Vertex d,double c)
	{
		dest = d;
		cost = c;
	}
	
	public int compareTo(Path rhs)
	{
		double otherCost = rhs.cost;
		
		return cost < otherCost ? -1 : cost > otherCost ? 1 : 0;
	}
}
