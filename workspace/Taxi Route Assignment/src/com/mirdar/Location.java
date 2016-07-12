package com.mirdar;

public class Location {
	
	//R时道路网格中的一个路段，其中R1=(I1,I2),道路网格G=(I,R)
	RouteSection R;
	//R路段两个交叉点其中一个
	public Vector I;
	//rate时R路段离I点的距离与R长度的比例
	double rate;
	//经纬度，可由Location计算得到
	double x;
	double y;
	
	public Location(RouteSection R)
	{
		this.R = R;
		initial();
	}
	
	void initial()
	{
		double x = Math.random();
		if(x <= 0.5)
		{
			this.I = R.I1;
		}
		else
		{
			this.I = R.I2;
		}
		rate = Math.random();
		
		x = I.x + (R.I2.x - I.x)*rate;
		y = I.y + (R.I2.y - I.y)*rate;
	}
}
