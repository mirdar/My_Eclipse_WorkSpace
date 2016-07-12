package com.mirdar;

import java.util.ArrayList;

//得到合理的taxi集合,这个是对一个custRequest进行查询，考虑
//多个custRequest的时候，是否开辟线程
public class GetTaxi {

	CustRequest custRequest;
	//该custRequest的伴随列表
	ArrayList<TaxiRequest> custTaxiSet;
	TaxiRequest taxiRequest;
	
	public GetTaxi(int k,ArrayList<TaxiRequest> taxiRequests,CustRequest custRequest)
	{
		this.custRequest = custRequest;
		ArrayList<TaxiRequest> taxiSet = knn(k,taxiRequests);
		custTaxiSet = sortedTaxi(taxiSet);
		this.taxiRequest = custTaxiSet.get(0);
	}
	
	public ArrayList<TaxiRequest> knn(int k,ArrayList<TaxiRequest> taxiRequests)
	{
		ArrayList<TaxiRequest> taxiSet = new ArrayList<TaxiRequest>();
		
		//1)利用KNN获得custRequest的最近的一个个taxiRequests
		//2)然后计算出距离d,设置为半径r0，令ti=(r1-r0)/speed，其中speed是最慢的速度
		//3)再根据f(ti)=|profit0 - min(profit)|，求出r1的值，然后根据坐标信息得到taxiSet
		//4)对taxiSet进行精化.获得r1范围内的min(profit),f(ti)=|profit0 - min(profit)|,
		//  令ti=(r2-r0)/speed,其中speed是最慢的速度,得到r2，然后根据坐标信息得到taxiSet2，
		//	重复4操作，直到f(ti)<|profit0 - min(profit)|
		//5)最后计算taxiSet的balance值并排序，得到custRequest的伴随TR(taxiRequest)列表
		//6)最后检查是否存在冲突问题。。。然后进行推荐，并更新balance值
		
		
		
		return taxiSet;
	}
	
	public double distance(TaxiRequest taxiRequsest)
	{
		double distance;
		distance = Math.sqrt((taxiRequsest.s.x - custRequest.s.x)
								*(taxiRequsest.s.x - custRequest.s.x)
							   +(taxiRequsest.s.y - custRequest.s.y)
							   	*(taxiRequsest.s.y - custRequest.s.y));
		return 	distance;
	}
	
	public ArrayList<TaxiRequest> sortedTaxi(ArrayList<TaxiRequest> taxiRequests)
	{
		ArrayList<TaxiRequest> taxiSet2 = new ArrayList<TaxiRequest>();
		
		//计算每个TaxiRequest到custRequest的时间（先构造最短路径，然后根据速度计算时间）
		//然后得到最小的时间t0,并利用|t - t0|<= T进行过滤
		//对每个taxi更新Balance值
		//并根据Balance排序
		
		return taxiSet2;
	}
	
}
