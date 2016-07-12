package com.mirdar.datamin;

public class Test {

	public static void main(String[] args)
	{
		System.out.println(sim(1.5,1.7,1.4,1.6));
		System.out.println(sim(2,1.9,1.4,1.6));
		System.out.println(sim(1.6,1.8,1.4,1.6));
		System.out.println(sim(1.2,1.5,1.4,1.6));
		System.out.println(sim(1.5,1.0,1.4,1.6));
		
	}
	
	public static double sim(double x1, double y1, double x2, double y2)
	{
		return (x1*x2+y1*y2)/(Math.sqrt(x1*x1+y1*y1)*Math.sqrt(x2*x2+y2*y2));
	}
}
