package com.mirdar.taxi1;

public class Distance {

	public static double distance(Location taxi_loc, Location cust_loc)
	{
		double d;
		
		d = Math.sqrt((taxi_loc.x-cust_loc.x)
						*(taxi_loc.x-cust_loc.x)+
						(taxi_loc.y-cust_loc.y)
						*(taxi_loc.y-cust_loc.y));
		
		return d;
	}
	
	public static double distance2(Vector v1, Vector v2)
	{
		double d;
		
		d = Math.sqrt((v1.x-v2.x)
						*(v1.x-v2.x)+
						(v1.y-v2.y)
						*(v1.y-v2.y));
		
		return d;
	}
}
