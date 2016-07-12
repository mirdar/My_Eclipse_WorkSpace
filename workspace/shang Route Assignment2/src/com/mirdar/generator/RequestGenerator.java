package com.mirdar.generator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.mirdar.CustRequest;
import com.mirdar.Distance;
import com.mirdar.TaxiRequest;
import com.mirdar.Vector;
/*
 * 请求数据模拟生成器
 */
public class RequestGenerator {

	//taxiRequest生成器
	public ArrayList<TaxiRequest> taxiRequestGenerator(Map<String,Vector> map,
														ArrayList<TaxiRequest> taxiList,int n,int seed)
	{
//		Vector[] vectors = new Vector[map.size()];
//		vectors = (Vector[])map.values().toArray();
		Random random = new Random();
		for(int i=0;i<n;i++)
		{
			int s = random.nextInt(map.size());
			Vector rs = (Vector)map.values().toArray()[s];
			
			taxiList.get(i).setLocation(rs);
			
		}
		
		return taxiList;
	}
	//custRequest生成器
	public ArrayList<CustRequest> custRequestGenerator(Map<String,Vector> map,
														ArrayList<CustRequest> custList,int m,int seed)
	{
			Random random = new Random();
			for(int i=0;i<m;i++)
			{
					int s1 = random.nextInt(map.size());
					int s2 = random.nextInt(map.size());
					Vector rs1 = (Vector)map.values().toArray()[s1];
					Vector rs2 = (Vector)map.values().toArray()[s2];
					custList.get(i).initial(rs1, rs2);
					//生成的CustRequest发出的起始点请求与终点请求要至少10m
//					if(Distance.distance2(custList.get(i).s, custList.get(i).e) < 100)
//						i--;
					custList.get(i).profit = 8+random.nextDouble()*20;
			}

			return custList;
	}

	//生成固定ID的Taxi
	public ArrayList<TaxiRequest> taxiGenerator(int n)
	{
		ArrayList<TaxiRequest> taxiList = new ArrayList<TaxiRequest>();
		for(int i=1;i<=n;i++)
		{
			TaxiRequest taxi = new TaxiRequest();
			taxi.taxiID = i;
			taxiList.add(taxi);
		}
		return taxiList;
	}
	//生成固定ID的cust
	public ArrayList<CustRequest> custGenerator(int m)
	{
		ArrayList<CustRequest> custList = new ArrayList<CustRequest>();
		for(int i=1;i<=m;i++)
		{
			CustRequest cust = new CustRequest();
			cust.custID = i;
			custList.add(cust);
		}
		return custList;
	}
	
	
	
	
}
