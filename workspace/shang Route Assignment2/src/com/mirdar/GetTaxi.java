package com.mirdar;

import java.util.ArrayList;

//�õ������taxi����,����Ƕ�һ��custRequest���в�ѯ������
//���custRequest��ʱ���Ƿ񿪱��߳�
public class GetTaxi {

	CustRequest custRequest;
	//��custRequest�İ����б�
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
		
		//1)����KNN���custRequest�������һ����taxiRequests
		//2)Ȼ����������d,����Ϊ�뾶r0����ti=(r1-r0)/speed������speed���������ٶ�
		//3)�ٸ���f(ti)=|profit0 - min(profit)|�����r1��ֵ��Ȼ�����������Ϣ�õ�taxiSet
		//4)��taxiSet���о���.���r1��Χ�ڵ�min(profit),f(ti)=|profit0 - min(profit)|,
		//  ��ti=(r2-r0)/speed,����speed���������ٶ�,�õ�r2��Ȼ�����������Ϣ�õ�taxiSet2��
		//	�ظ�4������ֱ��f(ti)<|profit0 - min(profit)|
		//5)������taxiSet��balanceֵ�����򣬵õ�custRequest�İ���TR(taxiRequest)�б�
		//6)������Ƿ���ڳ�ͻ���⡣����Ȼ������Ƽ���������balanceֵ
		
		
		
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
		
		//����ÿ��TaxiRequest��custRequest��ʱ�䣨�ȹ������·����Ȼ������ٶȼ���ʱ�䣩
		//Ȼ��õ���С��ʱ��t0,������|t - t0|<= T���й���
		//��ÿ��taxi����Balanceֵ
		//������Balance����
		
		return taxiSet2;
	}
	
}
