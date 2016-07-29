package com.mirdar.datastruct;

import java.util.ArrayList;

public class Grid {

	public int grid_id; // ���ӵ�id
	public double x; // ���ӵĺ����꣬������
	public double y;

	public ArrayList<Record> pickup_records = new ArrayList<Record>(); // pickup��¼
	public ArrayList<Record> getoff_records = new ArrayList<Record>(); // getoff��¼

	public double aveNum = 0; // ����ø���ÿСʱ��ƽ������
	public int flag = 0; // ������Ǹø����Ƿ��γ�һ��hotGrid��

	public int connectdegree = 0; //��grid�ھӵ�hotgrid��

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
		aveNum = aveNum / 100;  //������һ�ٸ�Сʱ
		if (this.aveNum >= threshold)
			this.flag = 1;
		return this.flag;
	}

}
