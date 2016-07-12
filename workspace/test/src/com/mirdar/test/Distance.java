package com.mirdar.test;

public class Distance {

	public double distance(Vector v1, Vector v2)
	{
		double d;
		
		d = Math.sqrt((v1.getX()-v2.getX())*(v1.getX()-v2.getX())+
						(v1.getY()-v2.getY())*(v1.getY()-v2.getY()));
		
		return d;
	}
}
