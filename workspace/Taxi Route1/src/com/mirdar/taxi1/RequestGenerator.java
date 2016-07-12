package com.mirdar.taxi1;

import java.util.ArrayList;
import java.util.Random;
/*
 * ��������ģ��������
 */
public class RequestGenerator {

	//taxiRequest������
	public ArrayList<TaxiRequest> taxiRequestGenerator(ArrayList<RouteSection> routeSectionList,
														ArrayList<TaxiRequest> taxiList,int n)
	{
		Random random = new Random();
		for(int i=0;i<n;i++)
		{
			int s = random.nextInt(routeSectionList.size());
			RouteSection rs = routeSectionList.get(s);
			taxiList.get(i).setLocation(rs);
			
		}
		
		return taxiList;
	}
	//custRequest������
	public ArrayList<CustRequest> custRequestGenerator(ArrayList<RouteSection> routeSectionList,
														ArrayList<CustRequest> custList,int m)
	{
			Random random = new Random();
			for(int i=0;i<m;i++)
			{
					int s1 = random.nextInt(routeSectionList.size());
					int s2 = random.nextInt(routeSectionList.size());
					RouteSection rs1 = routeSectionList.get(s1);
					RouteSection rs2 = routeSectionList.get(s2);
					custList.get(i).initial(rs1, rs2);
					//���ɵ�CustRequest��������ʼ���������յ�����Ҫ����10m
					if(Distance.distance(custList.get(i).s, custList.get(i).e) < 10)
						i--;
			}

			return custList;
	}

	//���ɹ̶�ID��Taxi
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
	//���ɹ̶�ID��cust
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
