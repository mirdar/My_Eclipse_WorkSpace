package com.mirdar.fast;

import java.util.ArrayList;

import com.mirdar.CustRequest;
import com.mirdar.Distance;
import com.mirdar.RouteSection;
import com.mirdar.SubArea;
import com.mirdar.TaxiRequest;
import com.mirdar.refinement.Cust;
import com.mirdar.refinement.Taxi;
import com.mirdar.ssa.Graph;
import com.mirdar.ssa.Vertex;

public class FastFunction {

	double speed = 500;
	public ArrayList<Cust> fastFun(ArrayList<TaxiRequest> taxiList,
			ArrayList<CustRequest> custList,
			ArrayList<RouteSection> routeSections)
	{
		ArrayList<TaxiRequest> taxiList2 = new ArrayList<TaxiRequest>();
		for (int k = 0; k < taxiList.size(); k++)
		{
			taxiList2.add(taxiList.get(k));
		}

		ArrayList<Cust> custs = new ArrayList<Cust>();
		for (int i = 0; i < custList.size(); i++) // 对每个cust进行细化操作
		{
			double r = 0;
			double minDis = Double.MAX_VALUE;
			int t = 0;
			Cust cust = new Cust(custList.get(i).custID, custList.get(i).profit,
					custList.get(i).s);
			ArrayList<Taxi> taxis = new ArrayList<Taxi>();
			for (int j = 0; j < taxiList2.size(); j++)
			{
				if (taxiList2.get(j) == null)
					continue;
				// 记录离cust最近的taxi
				if (Distance.distance2(custList.get(i).s,
						taxiList2.get(j).s) < minDis)
				{
					minDis = Distance.distance2(custList.get(i).s,
							taxiList2.get(j).s);
					t = j;
				}

				Taxi taxi = new Taxi(taxiList2.get(j).taxiID,
						taxiList2.get(j).s,
						(Distance.distance2(custList.get(i).s,
								taxiList2.get(j).s)),
						taxiList2.get(j).profit);
				taxi.index = j;
				taxis.add(taxi);
			}
			r = getNNTaxiDis2(cust, taxis.get(t), routeSections, 500);

			if (r == 0)
			{
				taxis.get(t).ti = 0;
				cust.nnTaxi = taxis.get(t);
				custs.add(cust);
				taxiList2.set(t, null);
			} else
			{
				SubArea subArea = new SubArea();
				ArrayList<RouteSection> subRouteSection = subArea.getSubArea2(
						routeSections, (r + 0.01) * (r + 0.01), cust);
				ArrayList<Taxi> taxiss = new ArrayList<Taxi>();
				// 获得r范围内的taxi集合
				for (int m = 0; m < taxis.size(); m++)
				{
					if (taxis.get(m).Lenght <= r + 0.01)
					{
						taxiss.add(taxis.get(m));
					}
				}

				Graph g = new Graph();
				for (int m = 0; m < subRouteSection.size(); i++)
				{
					g.addEdge(subRouteSection.get(m).I1.vectorID,
							subRouteSection.get(m).I2.vectorID,
							subRouteSection.get(m).length);

				}
				g.dijkstra(cust.vector.vectorID);

				double minDis2 = Double.MAX_VALUE;
				int t2 = 0;
				for (int j = 0; j < taxiss.size(); j++)
				{
					Vertex v = g.cleanVertex(taxiss.get(j).vector.vectorID);
					if (v == null)
						continue;
					if (v.dist < minDis2)
					{
						minDis2 = v.dist;
						t2 = j;
					}
				}
				taxiss.get(t2).ti = minDis2 / speed;
				cust.nnTaxi = taxiss.get(t2);
				custs.add(cust);
				taxiList2.set(cust.nnTaxi.index, null);
			}
		}
		return custs;
	}

	// 将递归写成循环的形式
	public double getNNTaxiDis2(Cust cust, Taxi taxi,
			ArrayList<RouteSection> routeSections, double expand)
	{
		double minDis = 0;
		if (cust.vector.vectorID == taxi.vector.vectorID)
			return minDis;
		SubArea subArea = new SubArea();
		ArrayList<RouteSection> subRouteSection = new ArrayList<RouteSection>();
		Graph g = new Graph();
		boolean bool = nnTaxiFun(g, taxi, cust, expand, subArea,
				subRouteSection, routeSections);
		while (!bool)
		{
			expand = expand + 500;
			subArea = new SubArea();
			subRouteSection = new ArrayList<RouteSection>();
			g = new Graph();
			bool = nnTaxiFun(g, taxi, cust, expand, subArea, subRouteSection,
					routeSections);
		}
		g.dijkstra(cust.vector.vectorID);
		while (g.cleanVertex(taxi.vector.vectorID).dist > 100000)
		{
			expand = expand + 500;
			subArea = new SubArea();
			subRouteSection = new ArrayList<RouteSection>();
			g = new Graph();
			nnTaxiFun(g, taxi, cust, expand, subArea, subRouteSection,
					routeSections);
			g.dijkstra(cust.vector.vectorID);
		}
		minDis = g.cleanVertex(taxi.vector.vectorID).dist;
		return minDis;
	}

	public boolean nnTaxiFun(Graph g, Taxi taxi, Cust cust, double expand,
			SubArea subArea, ArrayList<RouteSection> subRouteSection,
			ArrayList<RouteSection> routeSections)
	{
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
		int flagCust = 0;
		int flagNNTaxi = 0;
		subRouteSection = subArea.getSubArea(routeSections, x1, x2, y1, y2);
		flagCust = 0;
		flagNNTaxi = 0;
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
		}
		if (flagCust == 1 && flagNNTaxi == 1)
		{
			return true;
		}
		return false;
	}
}
