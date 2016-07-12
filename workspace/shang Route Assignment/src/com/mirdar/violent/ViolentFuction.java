package com.mirdar.violent;

import java.util.ArrayList;

import com.mirdar.CustRequest;
import com.mirdar.Distance;
import com.mirdar.RouteSection;
import com.mirdar.SubArea;
import com.mirdar.TaxiRequest;
import com.mirdar.refinement.Cust;
import com.mirdar.refinement.Taxi;
import com.mirdar.ssa.Graph;

public class ViolentFuction {

	double speed = 500;
	double constant = 1;
	public ArrayList<Cust> violentSearch(ArrayList<TaxiRequest> taxilist,
			ArrayList<CustRequest> custList,
			ArrayList<RouteSection> routeSections)
	{
		ArrayList<TaxiRequest> taxiList2 = new ArrayList<TaxiRequest>();
		for (int k = 0; k < taxilist.size(); k++)
		{
			taxiList2.add(taxilist.get(k));
		}

		ArrayList<Cust> custs = new ArrayList<Cust>();
		Graph g = new Graph();
		for (int i = 0; i < routeSections.size(); i++)
		{
			g.addEdge(routeSections.get(i).I1.vectorID,
					routeSections.get(i).I2.vectorID,
					routeSections.get(i).length);

		}
		
		for (int i = 0; i < custList.size(); i++)
		{
			//为什么每次的dijkstra算法时间不一样
//			long startTime1 = System.currentTimeMillis();
			g.dijkstra(custList.get(i).s.vectorID);
//			long endTime1 = System.currentTimeMillis();
//			System.out.println("Dijkstra.time in Vilent: "+(endTime1-startTime1)+"ms+++++++++++");
			
			Cust cust = new Cust(custList.get(i).custID, custList.get(i).profit,custList.get(i).s);
			ArrayList<Taxi> taxis = new ArrayList<Taxi>();
			double minBalance = Double.MAX_VALUE;
			double t0 = 0;
//			long startTime2 = System.currentTimeMillis();
			for (int j = 0; j < taxiList2.size(); j++)
			{
				if (taxiList2.get(j) == null)
					continue;
				Taxi taxi = new Taxi(taxiList2.get(j).taxiID,taxiList2.get(j).s,
						(Distance.distance2(custList.get(i).s,taxiList2.get(j).s)),
						taxiList2.get(j).profitViolent);
				taxi.index = j;
				taxis.add(taxi);
//				flag++;
			}
			
			double nnSp = Double.MAX_VALUE;
			for(int j1=0;j1<taxis.size();j1++)
			{
				if(g.cleanVertex(taxis.get(j1).vector.vectorID).dist < nnSp)
				{
					nnSp = g.cleanVertex(taxis.get(j1).vector.vectorID).dist;
				}
			}
			t0 = nnSp/speed;
//			t0 = getNNTaxiDis(cust, taxis.get(t), routeSections, 10) / speed;
			int m = 0;
			for (int k = 0; k < taxis.size(); k++)
			{
				taxis.get(k).Balance = getBalance(taxis.get(k),
						g.cleanVertex(taxis.get(k).vector.vectorID).dist, t0);
				taxis.get(k).ti = g.cleanVertex(taxis.get(k).vector.vectorID).dist/speed;
				if (taxis.get(k).Balance < minBalance)
				{
					minBalance = taxis.get(k).Balance;
					m = k;
				}
				
				if (taxis.get(k).Balance == minBalance)
				{
					if(taxis.get(k).ti < taxis.get(m).ti)
					{
						m = k;
					}
				}
			}
//			long endTime2 = System.currentTimeMillis();
//			System.out.println("otherOperation.time in Violen: "+(endTime2-startTime2)+"ms+++++++++");
//			if(sameBalance >= 1)
//				System.out.println("sameBalanceViolent: "+sameBalance);
			cust.nnTaxi = taxis.get(m);
			custs.add(cust);
			taxiList2.set(taxis.get(m).index, null);
		}

		return custs;
	}

	public double getBalance(Taxi taxi, double d, double t0)
	{
		return taxi.profit + constant * (Math.pow(Math.E, -t0 + d/speed) - 1);
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
}
