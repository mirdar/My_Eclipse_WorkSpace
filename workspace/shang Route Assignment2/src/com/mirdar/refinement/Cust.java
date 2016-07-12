package com.mirdar.refinement;

import java.util.ArrayList;
import java.util.List;

import com.mirdar.Vector;

public class Cust {

	public int custID;
	public Vector vector;
	public List<Taxi> taxiSet;
	public double profit;
	public double t0;
	public Taxi nnTaxi;
	
	public Cust(int custID,double profit,Vector vector)
	{
		this.custID = custID;
		this.profit = profit;
		this.vector = vector;
		taxiSet = new ArrayList<Taxi>();
	}
	
	public void setNNTaxi(Taxi nnTaxi,double t0)
	{
		this.nnTaxi = nnTaxi;
		this.t0 = t0;
	}
}
