package com.mirdar.taxi1;

import java.util.ArrayList;

//�õ������taxi����,����Ƕ�һ��custRequest���в�ѯ������
//���custRequest��ʱ���Ƿ񿪱��߳�
public class GetTaxi {

	CustRequest custRequest1;
	//��custRequest�İ����б�
	ArrayList<TaxiRequest> custTaxiSet1;
	TaxiRequest taxiRequest1;
	
//	public GetTaxi(int k,ArrayList<TaxiRequest> taxiRequests,CustRequest custRequest)
//	{
//		this.custRequest = custRequest;
//		ArrayList<TaxiRequest> taxiSet = knn(k,taxiRequests);
//		custTaxiSet = sortedTaxi(taxiSet);
//		this.taxiRequest = custTaxiSet.get(0);
//	}
	//���cust����ĵ�
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
	//�õ�ŷʽ����㵽cust����̾��룬����û�б�������·��
	public double getNNTaxiDis(TaxiRequest nnTaxi,CustRequest custRequest
			,ArrayList<RouteSection> routeSections,ArrayList<Vector> vectors)
	{
		double nnTaxiDis = 0;
		double x1,y1,x2,y2;
		//���taxiRequest��custRequest��ɵĳ���������
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
		System.out.println("nnTaxi��custRequestŷʽ���룺"+Distance.distance(nnTaxi.s, custRequest.s));
		SubArea subArea = new SubArea();
		ArrayList<RouteSection> subRouteSections = subArea.getSubArea(routeSections,x1,x2,y1,y2);
		ArrayList<Vector> subVector = subArea.getSubAreaV(vectors, x1,x2,y1,y2);
		System.out.println("�������е�vector��Ŀ�� "+subVector.size());
		System.out.println("�������е�RouteSectons����Ŀ�� "+subRouteSections.size());
		//����������·�������Ƿ���ȷ
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
				System.out.println("nnTaxi�������� "+i+"  vectorID�� "+nnTaxi.s.I.vectorID);
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
//		//���taxiRequest��custRequest��ɵĳ���������
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
//			//������Y����չѰ�Ҹ���ĵ�
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
//		//��routeVector�й�����route
//		
//		return route;
//	}
//	
//	//��Դ�������·���㷨�ҳ���Щ�ڵ������·��
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
//		//����vectors��routeList�����ڽӾ���
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
//		//��Դ�������·���㷨
//		
//		return vectors;
//	}
//		
//	//�õ��ɴ�·���ĺ�ѡ�ڵ�
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
//		//1)����KNN���custRequest�������һ����taxiRequests
//		//2)Ȼ����������d,����Ϊ�뾶r0����ti=(r1-r0)/speed������speed���������ٶ�
//		//3)�ٸ���f(ti)=|profit0 - min(profit)|�����r1��ֵ��Ȼ�����������Ϣ�õ�taxiSet
//		//4)��taxiSet���о���.���r1��Χ�ڵ�min(profit),f(ti)=|profit0 - min(profit)|,
//		//  ��ti=(r2-r0)/speed,����speed���������ٶ�,�õ�r2��Ȼ�����������Ϣ�õ�taxiSet2��
//		//	�ظ�4������ֱ��f(ti)<|profit0 - min(profit)|
//		//5)������taxiSet��balanceֵ�����򣬵õ�custRequest�İ���TR(taxiRequest)�б�
//		//6)������Ƿ���ڳ�ͻ���⡣����Ȼ������Ƽ���������balanceֵ
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
//		//����ÿ��TaxiRequest��custRequest��ʱ�䣨�ȹ������·����Ȼ������ٶȼ���ʱ�䣩
//		//Ȼ��õ���С��ʱ��t0,������|t - t0|<= T���й���
//		//��ÿ��taxi����Balanceֵ
//		//������Balance����
//		
//		return taxiSet2;
//	}
//	
}
