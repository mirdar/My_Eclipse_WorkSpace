package com.mirdar.datastruct;

import java.util.ArrayList;

public class Grid {

	public int grid_id; // 格子的id
	public double x; // 格子的横坐标，纵坐标
	public double y;

	public ArrayList<Record> pickup_records = new ArrayList<Record>(); // pickup记录
	public ArrayList<Record> getoff_records = new ArrayList<Record>(); // getoff记录

	public double aveNum = 0; // 计算该格子每小时的平均人数
	public int flag = 0; // 用来标记该格子是否形成一个hotGrid，

	public int connectdegree = 0; //该grid邻居的hotgrid的

	public int setHotGrid(int threshold)
	{
		for(int i=0;i<pickup_records.size();i++)
		{
			aveNum++;
		}
		for(int i=0;i<getoff_records.size();i++)
		{
			aveNum++;
		}
		aveNum = aveNum / 100;  //假设有一百个小时
		if (this.aveNum >= threshold)
			this.flag = 1;
		return this.flag;
	}

}
