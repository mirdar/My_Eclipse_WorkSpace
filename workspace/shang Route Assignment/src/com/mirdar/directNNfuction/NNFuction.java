package com.mirdar.directNNfuction;

import java.util.ArrayList;

import com.mirdar.CustRequest;
import com.mirdar.Distance;
import com.mirdar.RouteSection;
import com.mirdar.TaxiRequest;
import com.mirdar.refinement.Cust;
import com.mirdar.refinement.Taxi;
import com.mirdar.ssa.Graph;

public class NNFuction {

	double speed = 500;
	public ArrayList<Cust> recomNN(ArrayList<TaxiRequest> taxilist,
			ArrayList<CustRequest> custList,
			ArrayList<RouteSection> routeSections)
	{
		ArrayList<TaxiRequest> taxiList = new ArrayList<TaxiRequest>();
		for (int k = 0; k < taxilist.size(); k++)
		{
			taxiList.add(taxilist.get(k));
		}
		Graph g = new Graph();
		for (int i = 0; i < routeSections.size(); i++)
		{
			g.addEdge(routeSections.get(i).I1.vectorID,
					routeSections.get(i).I2.vectorID,
					routeSections.get(i).length);
		}
		ArrayList<Cust> custs = new ArrayList<Cust>();
		for (int i = 0; i < custList.size(); i++) // ��ÿ��cust����ϸ������
		{
			double t0 = 0;
			double minDis = Double.MAX_VALUE;
			int t = 0;
			Cust cust = new Cust(custList.get(i).custID, custList.get(i).profit,
					custList.get(i).s);
			for (int j = 0; j < taxiList.size(); j++)
			{
				if (taxiList.get(j) == null)
					continue;
				// ��¼��cust�����taxi
				if (Distance.distance2(custList.get(i).s,
						taxiList.get(j).s) < minDis)
				{
					minDis = Distance.distance2(custList.get(i).s,
							taxiList.get(j).s);
					t = j;
				}
			}

			Taxi taxi = new Taxi(taxiList.get(t).taxiID, taxiList.get(t).s,
					(Distance.distance2(custList.get(i).s, taxiList.get(t).s)),
					taxiList.get(t).profitDirect);
			taxi.index = t;
			// t0 = getNNTaxiDis(cust, taxi,routeSections,10)/speed;
			// cust�������taxi�����·��
			t0 = g.BFS(cust.vector.vectorID, taxiList.get(t).s.vectorID)
					/ speed;
			// ˵���в��ɴ�ĵ�
			cust.t0 = t0;
			taxi.ti = t0;
			cust.nnTaxi = taxi;
			custs.add(cust);
			taxiList.set(t, null);
		}
		return custs;
	}
}
