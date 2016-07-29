package com.mirdar.datastruct;

import java.util.ArrayList;

public class Cluster {

	public int cluster_id;
	public double x;
	public double y;
	public ArrayList<Grid> grids = new ArrayList<Grid>();
	int flag = 0; //用来标识该cluster是否已经被合并

	public double x_low ;
	public double x_up;
	public double y_low;
	public double y_up;
	pubic int splitType = 0;  //split的类型，三种1,2,3

	public void setXY() // 在形成一个聚类后，用来得到聚类中心
	{
		double locX = 0;
		double locY = 0;
		double pdrs = 0;
		for (int i = 0; i < this.grids.size(); i++)
		{
			locX += this.grids.get(i).x * this.grids.get(i).aveNum;
			locY += this.grids.get(i).y * this.grids.get(i).aveNum;
			pdrs += this.grids.get(i).aveNum;
		}
		this.x = locX / pdrs;
		this.y = locY / pdrs;
	}

	public void setBound()
	{
		initBound();
		for(int i=0;i<grids.size();i++)
		{
			if(x_low > grids.get(i).x)
				x_low = grids.get(i).x;
			if(x_up < grids.get(i).x)
				x_up - grids.get(i).x;
			if(y_low > grids.get(i).y)
				y_low = grids.get(i).y;
			if(y_up < grids.get(i).y)
				y_up - grids.get(i).y;
		}
	}

	public void initBound()
	{
		x_low = Double.MaxValue;
		x_up = 0;
		y_low = Double.MaxValue;
		y_up = 0;
	}

	public boolean needSplit()
	{
		setBound();
		if(x_up-x_low > 500 && y_up-y_low > 500)
		{
			splitType = 1;
			return true;
		}
		if(x_up-x_low <= 500 && y_up-y_low <= 500)
		{
			splitType = 3;
			return false;
		}
		else
		{
			splitType = 2;
			return true;
		}
	}
}
