package com.mirdar.directNNfuction;

import java.util.ArrayList;

import com.mirdar.CustRequest;
import com.mirdar.Distance;
import com.mirdar.RouteSection;
import com.mirdar.SubArea;
import com.mirdar.TaxiRequest;
import com.mirdar.refinement.Cust;
import com.mirdar.refinement.Taxi;
import com.mirdar.ssa.Graph;

public class NNFuction {

	double speed = 14;
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
		for (int i = 0; i < custList.size(); i++) // 对每个cust进行细化操作
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
				// 记录离cust最近的taxi
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
//			t0 = getNNTaxiDis(cust, taxi,routeSections,10)/speed;
			//cust到最近的taxi的最短路径
			t0 = g.BFS(cust.vector.vectorID, taxiList.get(t).s.vectorID)/ speed; 
			//说明有不可达的点
			cust.t0 = t0;
			taxi.ti = t0;
			cust.nnTaxi = taxi;
			custs.add(cust);
			taxiList.set(t, null);
		}
		return custs;
	}

	// 得到离cust最近的taxi到cust的局部最短路径
	public double getNNTaxiDis(Cust cust, Taxi taxi,
			ArrayList<RouteSection> routeSections, double expand)
	{
		double minDis = 0;

		// 当最近的taxi与cust在一个vector
		if (cust.vector.vectorID == taxi.vector.vectorID)
			return minDis;

		double x1, y1, x2, y2;
		if (taxi.vector.x <= cust.vector.x)
		{
			x1 = taxi.vector.x - expand;
			x2 = cust.vector.x + expand;
		} else
		{
			x1 = cust.vector.x - expand;
			x2 = taxi.vector.x + expand;
		}
		if (taxi.vector.y <= cust.vector.y)
		{
			y1 = taxi.vector.y - expand;
			y2 = cust.vector.y + expand;
		} else
		{
			y1 = cust.vector.y - expand;
			y2 = taxi.vector.y + expand;
		}
		SubArea subArea = new SubArea();
		ArrayList<RouteSection> subRouteSection = subArea
				.getSubArea(routeSections, x1, x2, y1, y2);
		// System.out.println("subRouteSectionNNTaxi.size:
		// "+subRouteSection.size());
		Graph g = new Graph();
		int flagCust = 0;
		int flagNNTaxi = 0;
		for (int i = 0; i < subRouteSection.size(); i++)
		{
			g.addEdge(subRouteSection.get(i).I1.vectorID,
					subRouteSection.get(i).I2.vectorID,
					subRouteSection.get(i).length);
			if (subRouteSection.get(i).I1.vectorID == cust.vector.vectorID
					|| subRouteSection
							.get(i).I2.vectorID == cust.vector.vectorID)
				flagCust = 1;
			if (subRouteSection.get(i).I1.vectorID == taxi.vector.vectorID
					|| subRouteSection
							.get(i).I2.vectorID == taxi.vector.vectorID)
				flagNNTaxi = 1;
			// if((subRouteSection.get(i).I1.vectorID == cust.vector.vectorID &&
			// subRouteSection.get(i).I2.vectorID == taxi.vector.vectorID)
			// ||(subRouteSection.get(i).I2.vectorID == cust.vector.vectorID &&
			// subRouteSection.get(i).I1.vectorID == taxi.vector.vectorID))
			// System.out.println("have a directly way");

		}
		if (flagCust == 1 && flagNNTaxi == 1)
		{
			g.dijkstra(cust.vector.vectorID);
			if (g.cleanVertex(taxi.vector.vectorID).dist > 100000
					|| g.cleanVertex(taxi.vector.vectorID) == null)
			{
				expand = expand + 10;
				minDis = getNNTaxiDis(cust, taxi, routeSections, expand);
			} else
				minDis = g.cleanVertex(taxi.vector.vectorID).dist;
		} else
		{
			expand = expand + 10;
			minDis = getNNTaxiDis(cust, taxi, routeSections, expand);
		}
		return minDis;
	}
	
//	public double getGlobalSp(Cust cust, Taxi taxi,
//			ArrayList<RouteSection> routeSections, double localSp)
}
