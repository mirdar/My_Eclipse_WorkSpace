package com.mirdar.refinement;

import com.mirdar.Vector;

public class Taxi {
	
	public int index;
	public int taxiID;
	public Vector vector;
	public double Lenght;
	public double profit;
	public double Balance;
	public double ti;
	
	public Taxi(int taxiID,Vector vector,double Lenght,double profit)
	{
		this.taxiID = taxiID;
		this.vector = vector;
		this.Lenght = Lenght;
		this.profit = profit;
	}
	
}
