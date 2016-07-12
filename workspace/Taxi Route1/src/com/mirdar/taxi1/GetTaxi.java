package com.mirdar.taxi1;

import java.util.ArrayList;

//得到合理的taxi集合,这个是对一个custRequest进行查询，考虑
//多个custRequest的时候，是否开辟线程
public class GetTaxi {

	CustRequest custRequest1;
	//该custRequest的伴随列表
	ArrayList<TaxiRequest> custTaxiSet1;
	TaxiRequest taxiRequest1;
	
//	public GetTaxi(int k,ArrayList<TaxiRequest> taxiRequests,CustRequest custRequest)
//	{
//		this.custRequest = custRequest;
//		ArrayList<TaxiRequest> taxiSet = knn(k,taxiRequests);
//		custTaxiSet = sortedTaxi(taxiSet);
//		this.taxiRequest = custTaxiSet.get(0);
//	}
	//获得cust最近的点
	public TaxiRequest getTaxi(ArrayList<TaxiRequest> taxiRequests,CustRequest custRequest)
	{
		double minDis = 10000;
		int t = 0;
		for(int i=0;i<taxiRequests.size();i++)
		{
			double dis = Distance.distance(taxiRequests.get(i).s, custRequest.s);
			if(dis < minDis)
			{
				minDis = dis;
				t = i;
			}
			
		}
		TaxiRequest nnTaxi = taxiRequests.get(t);
		
//		nnTaxi = taxiRequests.get(0);
//		for(int i=1;i<taxiRequests.size();i++)
//		{
//			if(Distance.distance(taxiRequests.get(i).s, custRequest.s)
//					< Distance.distance(nnTaxi.s, custRequest.s))
//					{
//						nnTaxi = taxiRequests.get(i);
//					}
//		}
		return nnTaxi;
	}
	//得到欧式最近点到cust的最短距离，但是没有保存具体的路径
	public double getNNTaxiDis(TaxiRequest nnTaxi,CustRequest custRequest
			,ArrayList<RouteSection> routeSections,ArrayList<Vector> vectors)
	{
		double nnTaxiDis = 0;
		double x1,y1,x2,y2;
		//获得taxiRequest与custRequest组成的长方形区域
		if(nnTaxi.s.x <= custRequest.s.x)
		{
			x1 = nnTaxi.s.x - 10;
			x2 = custRequest.s.x + 10;
		}
		else
		{
			x1 = custRequest.s.x - 10;
			x2 = nnTaxi.s.x + 10;
		}
		if(nnTaxi.s.y <= custRequest.s.y)
		{
			y1 = nnTaxi.s.y - 10;
			y2 = custRequest.s.y + 10;
		}
		else
		{
			y1 = custRequest.s.y - 10;
			y2 = nnTaxi.s.y + 10;
		}
		System.out.println("nnTaxi与custRequest欧式距离："+Distance.distance(nnTaxi.s, custRequest.s));
		SubArea subArea = new SubArea();
		ArrayList<RouteSection> subRouteSections = subArea.getSubArea(routeSections,x1,x2,y1,y2);
		ArrayList<Vector> subVector = subArea.getSubAreaV(vectors, x1,x2,y1,y2);
		System.out.println("子区域中的vector数目： "+subVector.size());
		System.out.println("子区域中的RouteSectons的数目： "+subRouteSections.size());
		//用来检测最短路径数据是否正确
		for(int i=0;i<subRouteSections.size();i++)
		{
			System.out.println("RouteSectionId: "+subRouteSections.get(i).routeSectionID+" vectorID1: "+subRouteSections.get(i).I1.vectorID+
								" vectorID2: "+subRouteSections.get(i).I2.vectorID);
		}
		Dijkstra dijkstra1 = new Dijkstra();
		double[] d = dijkstra1.dijkstra(subVector, subRouteSections, custRequest.s.I);
		
		for(int i=0;i<subVector.size();i++)
		{
			if(nnTaxi.s.I.vectorID == subVector.get(i).vectorID)
			{
				nnTaxiDis = d[i];
				System.out.println("nnTaxi的索引： "+i+"  vectorID： "+nnTaxi.s.I.vectorID);
				System.out.println("custID: "+custRequest.s.I.vectorID);
			}
		}
		
		return nnTaxiDis;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public ArrayList<RouteSection> getRoute(ArrayList<Vector> vectorList, CustRequest custRequest,
//																			TaxiRequest taxiRequest)
//	{
//		ArrayList<RouteSection> route = new ArrayList<RouteSection>();
//		ArrayList<Vector> routeVector = new ArrayList<Vector>();
//		double x1,y1,x2,y2;
//		//获得taxiRequest与custRequest组成的长方形区域
//		if(taxiRequest.s.x <= custRequest.s.x)
//		{
//			x1 = taxiRequest.s.x;
//			x2 = custRequest.s.x;
//		}
//		else
//		{
//			x1 = custRequest.s.x;
//			x2 = taxiRequest.s.x;
//		}
//		if(taxiRequest.s.y <= custRequest.s.y)
//		{
//			y1 = taxiRequest.s.y;
//			y2 = custRequest.s.y;
//		}
//		else
//		{
//			y1 = custRequest.s.y;
//			y2 = taxiRequest.s.y;
//		}
//		
//		double height = y2 - y1;
//		
//		while(routeVector == null)
//		{
////			routeVector = dijkstra(vectorList, custRequest, taxiRequest,
////									 x1, x2, y1, y2);
//			//不断向Y轴扩展寻找更多的点
//			if(y1-height > 2000)
//				y1 = y1 - height;
//			else
//				y1 = 2000;
//			
//			if(y2+height < 4000)
//				y2 = y2 + height;
//			else
//				y2 = 4000;
//		}
//		//从routeVector中构建出route
//		
//		return route;
//	}
//	
//	//单源单点最多路径算法找出这些节点中最短路径
//	public ArrayList<Vector> dijkstra(ArrayList<Vector> vectorList,ArrayList<RouteSection> routeSections,
//				CustRequest custRequest,TaxiRequest taxiRequest,double x1,double x2,double y1,double y2)
//	{
//		ArrayList<RouteSection> routeList = new ArrayList<RouteSection>();
//		Vector start,end;
//		if(taxiRequest.s.R.I1.x>=x1&&taxiRequest.s.R.I1.x<=x2)
//			start = taxiRequest.s.R.I1;
//		else
//			start = taxiRequest.s.R.I2;
//		if(custRequest.s.R.I1.x>=x1&&custRequest.s.R.I1.x<=x2)
//			end = custRequest.s.R.I1;
//		else
//			end = custRequest.s.R.I2;
//		
//		SubArea subArea = new SubArea();
//		routeList = subArea.getSubArea(routeSections,x1, x2, y1, y2);
//		ArrayList<Vector> vectors = getTaxiCandidate(vectorList, custRequest, taxiRequest, x1, x2, y1, y2);
//		
//		int[] a = new int[vectors.size()];
//		double[][] b = new double[vectors.size()][vectors.size()];
//		for(int i=0;i<vectors.size();i++)
//			for(int j=0;j<vectors.size();j++)
//				b[i][j] = 0;
//		//根据vectors与routeList构建邻接矩阵
//		for(int i=0;i<vectors.size();i++)
//		{
//			a[i] = vectors.get(i).vectorID;
//		}
//		for(int j=0;j<routeList.size();j++)
//		{
//			int k1 = -1,k2 = -1;
//			for(int i=0;i<vectors.size();i++)
//			{
//				
//				if(routeList.get(j).I1.vectorID == a[i])
//					k1 = i;
//				if(routeList.get(j).I2.vectorID == a[i])
//					k2 = i;
//			}
//			if(k1 !=-1&&k2 !=-1)
//			{
//				b[k1][k2] = routeList.get(j).length;
//				b[k2][k1] = routeList.get(j).length;
//			}
//		}
//		
//			
//		
//		//单源单点最短路径算法
//		
//		return vectors;
//	}
//		
//	//得到可达路径的候选节点
//	public ArrayList<Vector> getTaxiCandidate(ArrayList<Vector> vectorList,CustRequest custRequest,
//											TaxiRequest taxiRequest,double x1,double x2,double y1,double y2)
//	{
//		
//		ArrayList<Vector> vectors = new ArrayList<Vector>();
//		for(int i=0;i<vectorList.size();i++)
//		{
//			if(vectorList.get(i).x>x1 &&vectorList.get(i).x<x2
//					&&vectorList.get(i).y>y1 && vectorList.get(i).y<y2)
//						vectors.add(vectorList.get(i));
//		}
//		
//		return vectors;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	public ArrayList<TaxiRequest> knn(int k,ArrayList<TaxiRequest> taxiRequests)
//	{
//		ArrayList<TaxiRequest> taxiSet = new ArrayList<TaxiRequest>();
//		
//		//1)利用KNN获得custRequest的最近的一个个taxiRequests
//		//2)然后计算出距离d,设置为半径r0，令ti=(r1-r0)/speed，其中speed是最慢的速度
//		//3)再根据f(ti)=|profit0 - min(profit)|，求出r1的值，然后根据坐标信息得到taxiSet
//		//4)对taxiSet进行精化.获得r1范围内的min(profit),f(ti)=|profit0 - min(profit)|,
//		//  令ti=(r2-r0)/speed,其中speed是最慢的速度,得到r2，然后根据坐标信息得到taxiSet2，
//		//	重复4操作，直到f(ti)<|profit0 - min(profit)|
//		//5)最后计算taxiSet的balance值并排序，得到custRequest的伴随TR(taxiRequest)列表
//		//6)最后检查是否存在冲突问题。。。然后进行推荐，并更新balance值
//		
//		
//		
//		return taxiSet;
//	}
//	
////	public double distance(TaxiRequest taxiRequsest)
////	{
////		double distance;
////		distance = Math.sqrt((taxiRequsest.s.x - custRequest.s.x)
////								*(taxiRequsest.s.x - custRequest.s.x)
////							   +(taxiRequsest.s.y - custRequest.s.y)
////							   	*(taxiRequsest.s.y - custRequest.s.y));
////		return 	distance;
////	}
////	
//	public ArrayList<TaxiRequest> sortedTaxi(ArrayList<TaxiRequest> taxiRequests)
//	{
//		ArrayList<TaxiRequest> taxiSet2 = new ArrayList<TaxiRequest>();
//		
//		//计算每个TaxiRequest到custRequest的时间（先构造最短路径，然后根据速度计算时间）
//		//然后得到最小的时间t0,并利用|t - t0|<= T进行过滤
//		//对每个taxi更新Balance值
//		//并根据Balance排序
//		
//		return taxiSet2;
//	}
//	
}
