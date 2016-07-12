package com.mirdar.refinement;

import java.util.ArrayList;

import com.mirdar.CustRequest;
import com.mirdar.Distance;
import com.mirdar.RouteSection;
import com.mirdar.SubArea;
import com.mirdar.TaxiRequest;
import com.mirdar.ssa.Graph;
import com.mirdar.ssa.Vertex;

/*
 * 1. 在此基础上进行修改，缩短refinement的运行时间，优化代码(时间花在getNNTaxi上)（完成）
 * 2. 将规定速度变成根据道路speed作为速度
 * 		a. 在求出最近距离taxi的t0后，之后在求r的时候speed被固定（变成范围内最大速度），这就会放到r
 * 		b. 每次要取出subarea的最大速度，就要扫描得到该区域内的road section，取出最大的speed（也放大了范围）
 * 3. 加入历史数据作为道路时间
 */

//精化操作
public class RefinementOperation { 

	double speed = 500; //500m/min的速度，此时结果与旧金山的差不多
	double constant = 1;

	// 大部分时间在refinement操作中
	//大部分时间在subArea上

	public void refinement(Cust cust, double t0, Taxi NNTaxi, double minProfit,
			ArrayList<Taxi> taxis, ArrayList<RouteSection> routeSection)
	{
		double r = 0;
		// 这里当r很小的时候会没法找到点,因为有些可直达点因为小数点的原因导致范围内搜索不到
		// 原来Math.log(n)是以e为底
//		r = speed * (t0 + Math.log(1 + ((NNTaxi.profit - minProfit) / constant))
//				/ Math.log(2));
//		System.out.println("t0:	"+t0);
		r = speed * (t0 + Math.log(1 + ((NNTaxi.profit - minProfit) / constant)));
//		System.out.println("r1: "+r);
		// System.out.println("r: "+r);
		ArrayList<Taxi> taxiss = new ArrayList<Taxi>();
		double minProfit2 = Double.MAX_VALUE;
		// 获得r范围内的taxi集合

		for (int i = 0; i < taxis.size(); i++)
		{
			if (taxis.get(i).Lenght <= r + 0.01)
			{
				taxiss.add(taxis.get(i));
				if (taxis.get(i).profit < minProfit2)
					minProfit2 = taxis.get(i).profit;
				
			}
		}
//		System.out.println("1: "+taxis.size());
//		long startTime3 = System.currentTimeMillis();
		while (minProfit2 != minProfit)
		{
			minProfit = minProfit2;
			r = speed * (t0 + Math.log(1 + ((NNTaxi.profit - minProfit) / constant)));
//			taxiss = new ArrayList<Taxi>();
			ArrayList<Taxi> taxiss2 = new ArrayList<Taxi>(); //范围不断缩小，车辆也不断缩小
			minProfit2 = Double.MAX_VALUE;
			// 获得r范围内的taxi集合
			for (int i = 0; i < taxiss.size(); i++)
			{
				if (taxiss.get(i).Lenght <= r + 0.01)
				{
					taxiss2.add(taxiss.get(i));
					if (taxiss.get(i).profit < minProfit2)
						minProfit2 = taxiss.get(i).profit;
				}
			}
			taxiss = taxiss2;
		}

		SubArea subArea = new SubArea();
		
		ArrayList<RouteSection> subRouteSection = subArea
				.getSubArea2(routeSection, (r + 0.01)*(r+0.01), cust);
		// 该范围只有一个点
		if (subRouteSection.size() == 0)
		{
			int t = 0;
			double profitss = Double.MAX_VALUE;
			for (int i = 0; i < taxiss.size(); i++)
			{
				if (taxiss.get(i).profit < profitss)
				{
					profitss = taxiss.get(i).profit;
					t = i;
				}
			}
			taxiss.get(t).ti = 0;
			// 这里可能会产生误差，因为taxiss大小有可能不等于0
			cust.nnTaxi = taxiss.get(t);
		} else
		{
			Graph g = new Graph();
//			int flagCust = 0;
//			int flagNNTaxi = 0;
//			long startTime6 = System.currentTimeMillis();
			
			for (int i = 0; i < subRouteSection.size(); i++)
			{
				g.addEdge(subRouteSection.get(i).I1.vectorID,
						subRouteSection.get(i).I2.vectorID,
						subRouteSection.get(i).length);
//				if (subRouteSection.get(i).I1.vectorID == cust.vector.vectorID
//						|| subRouteSection
//								.get(i).I2.vectorID == cust.vector.vectorID)
//					flagCust = 1;
//				if (subRouteSection.get(i).I1.vectorID == NNTaxi.vector.vectorID
//						|| subRouteSection
//								.get(i).I2.vectorID == NNTaxi.vector.vectorID)
//					flagNNTaxi = 1;

			}
			
			
//			if (flagNNTaxi != 1 || flagCust != 1)
//			{
//				System.out.println("NOoooooooooooooo");
//				int t = 0;
//				double profitss = Double.MAX_VALUE;
//				for (int i = 0; i < taxiss.size(); i++)
//				{
//					if (taxiss.get(i).profit < profitss)
//					{
//						profitss = taxiss.get(i).profit;
//						t = i;
//					}
//				}
//				taxiss.get(t).ti = 0;
//				// 这里可能会产生误差，因为taxiss大小有可能不等于0
//				cust.nnTaxi = taxiss.get(t);
//			} else
//			{
				g.dijkstra(cust.vector.vectorID);
				
				// 检测cust是否能直接到达taxiss中的taxi,并去除cust不能到的taxi
				// System.out.println("taxiss.size: "+taxiss.size());
				double minDis = Double.MAX_VALUE;
				for (int j = 0; j < taxiss.size(); j++)
				{
					Vertex v = g.cleanVertex(taxiss.get(j).vector.vectorID);
					if (v == null)
						continue;
					if (v.dist < minDis)
					{
						minDis = v.dist;
					}
				}

				t0 = minDis / speed;
				double minBalance = Double.MAX_VALUE;
				int m = 0;
				for (int i = 0; i < taxiss.size(); i++)
				{
					Vertex v = g.cleanVertex(taxiss.get(i).vector.vectorID);
					if (v == null)
						continue;
					taxiss.get(i).Balance = getBalance(taxiss.get(i),v.dist,t0);
					taxiss.get(i).ti = v.dist/ speed;
					if (taxiss.get(i).Balance < minBalance)
					{
						minBalance = taxiss.get(i).Balance;
						m = i;
					}

					if (taxiss.get(i).Balance == minBalance)
					{
						if (taxiss.get(i).ti < taxiss.get(m).ti)
						{
							m = i;
						}
					}
				}
				cust.nnTaxi = taxiss.get(m);
			}
//		}
	}

	// 细化操作的初始化,第一次应该使用推荐最近的
	public ArrayList<Cust> refinementOperation(ArrayList<TaxiRequest> taxiList,
			ArrayList<CustRequest> custList,
			ArrayList<RouteSection> routeSections)
	{
		ArrayList<TaxiRequest> taxiList2 = new ArrayList<TaxiRequest>();
		for (int k = 0; k < taxiList.size(); k++)
		{
			taxiList2.add(taxiList.get(k));
		}
		/*Graph g = new Graph();
		for (int i = 0; i < routeSections.size(); i++)
		{
			g.addEdge(routeSections.get(i).I1.vectorID,
					routeSections.get(i).I2.vectorID,
					routeSections.get(i).length);
		}*/
		// System.out.println("taxiList size(): "+taxiList2.size());
		ArrayList<Cust> custs = new ArrayList<Cust>();
		for (int i = 0; i < custList.size(); i++) // 对每个cust进行细化操作
		{
			double t0 = 0;
			double minDis = Double.MAX_VALUE;
			int t = 0;
			double minProfit = Double.MAX_VALUE;
			Cust cust = new Cust(custList.get(i).custID, custList.get(i).profit,
					custList.get(i).s);
			ArrayList<Taxi> taxis = new ArrayList<Taxi>();
			int flag2 = 0;
			// int flag1 = 0;
			// int flag = 0;
			for (int j = 0; j < taxiList2.size(); j++)
			{
				// flag++;
				if (taxiList2.get(j) == null)
				{
					// flag1++;
					continue;
				}
				// 记录离cust最近的taxi
				if (Distance.distance2(custList.get(i).s,
						taxiList2.get(j).s) < minDis)
				{
					minDis = Distance.distance2(custList.get(i).s,
							taxiList2.get(j).s);
					t = flag2;
				}

				// 获得初始的最小minProfit
				if (taxiList2.get(j).profit < minProfit)
					minProfit = taxiList2.get(j).profit;
				// Math.floor((Distance.distance2(custList.get(i).s,taxiList2.get(j).s)))
				Taxi taxi = new Taxi(taxiList2.get(j).taxiID,
						taxiList2.get(j).s,
						(Distance.distance2(custList.get(i).s,
								taxiList2.get(j).s)),
						taxiList2.get(j).profit);
				taxi.index = j;
				taxis.add(taxi);
				flag2++;
			}

			t0 = getNNTaxiDis(cust, taxis.get(t), routeSections, 1500)/speed; //很多时间花在了这里
//			t0 = g.BFS(cust.vector.vectorID, taxis.get(t).vector.vectorID)/ speed;

			cust.t0 = t0;

			refinement(cust, t0, taxis.get(t), minProfit, taxis, routeSections);

			custs.add(cust);
			taxiList2.set(cust.nnTaxi.index, null);
		}
		return custs;
	}

	// 得到离cust最近的taxi到cust的局部最短路径，这里取expand为多少也是很重要的
	public double getNNTaxiDis(Cust cust, Taxi taxi,
			ArrayList<RouteSection> routeSections, double expand)
	{
		double minDis = 0;
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
		// 当最近的taxi与cust在一个vector
		if (flagCust == 1 && flagNNTaxi == 1)
		{
			g.dijkstra(cust.vector.vectorID);
			// double di = g.BFS(cust.vector.vectorID,taxi.vector.vectorID);
			if (g.cleanVertex(taxi.vector.vectorID).dist > 100000)
			// if(di > 10000)
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

	// 分支界定法

	/*
	 * public double getNNTaxiDis2(Cust cust,Taxi taxi,ArrayList<RouteSection>
	 * routeSection) { Graph g = new Graph(); for(int
	 * i=0;i<routeSection.size();i++) {
	 * g.addEdge(routeSection.get(i).I1.vectorID,
	 * routeSection.get(i).I2.vectorID, routeSection.get(i).length); } double
	 * dist = g.BFS(cust.vector.vectorID, taxi.vector.vectorID);
	 * 
	 * return dist; }
	 */

	// Balance更新
	public double getBalance(Taxi taxi, double d, double t0)
	{
		return taxi.profit + constant * (Math.pow(Math.E, -t0 + d / speed) - 1);
	}

	// 快速排序
	public void quickSort(ArrayList<Taxi> taxis, int p, int r)
	{
		if (p < r)
		{
			int q = partition(taxis, p, r);
			quickSort(taxis, p, q - 1);
			quickSort(taxis, q + 1, r);
		}
	}
	public int partition(ArrayList<Taxi> taxis, int m, int n)
	{
		Taxi taxi = taxis.get(m);
		int i = m;
		for (int j = m + 1; j <= n; j++)
		{
			if (taxis.get(j).Balance <= taxi.Balance)
			{
				i = i + 1;
				Taxi temp = taxis.get(i);
				taxis.set(i, taxis.get(j));
				taxis.set(j, temp);
			}
		}
		Taxi temp = taxis.get(m);
		taxis.set(m, taxis.get(i));
		taxis.set(i, temp);

		return i;
	}

}
