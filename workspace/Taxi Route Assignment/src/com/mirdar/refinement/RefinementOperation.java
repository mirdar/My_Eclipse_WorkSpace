package com.mirdar.refinement;

import java.util.ArrayList;

import com.mirdar.CustRequest;
import com.mirdar.Distance;
import com.mirdar.RouteSection;
import com.mirdar.SubArea;
import com.mirdar.TaxiRequest;
import com.mirdar.ssa.Graph;

//��������
public class RefinementOperation {

	double speed = 50;
	double constant = 1;

	// �󲿷�ʱ����refinement������
	//�󲿷�ʱ����subArea��

	public void refinement(Cust cust, double t0, Taxi NNTaxi, double minProfit,
			ArrayList<Taxi> taxis, ArrayList<RouteSection> routeSection)
	{
		double r = 0;
		// ���ﵱr��С��ʱ���û���ҵ���,��Ϊ��Щ��ֱ�����ΪС�����ԭ���·�Χ����������
		// ԭ��Math.log(n)����eΪ��
//		r = speed * (t0 + Math.log(1 + ((NNTaxi.profit - minProfit) / constant))
//				/ Math.log(2));
//		System.out.println("t0:	"+t0);
		r = speed * (t0 + Math.log(1 + ((NNTaxi.profit - minProfit) / constant)));
//		System.out.println("r1: "+r);
		// System.out.println("r: "+r);
		ArrayList<Taxi> taxiss = new ArrayList<Taxi>();
		double minProfit2 = Double.MAX_VALUE;
		// ���r��Χ�ڵ�taxi����

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
			r = speed * (t0
					+ Math.log(1 + ((NNTaxi.profit - minProfit) / constant)));
			taxiss = new ArrayList<Taxi>();
			minProfit2 = Double.MAX_VALUE;
			// ���r��Χ�ڵ�taxi����
			for (int i = 0; i < taxis.size(); i++)
			{
				if (taxis.get(i).Lenght <= r + 0.01)
				{
					taxiss.add(taxis.get(i));
					if (taxis.get(i).profit < minProfit2)
						minProfit2 = taxis.get(i).profit;
				}
			}
		}
//		System.out.println("2: "+taxis.size());
//		System.out.println("r2: "+r);
//		long endTime3 = System.currentTimeMillis();
//		System.out.println("3.time in refinement: " + (endTime3 - startTime3)
//				+ "ms___________");

		// ����ʱ����ӡcust����taxi�����·��
//		long startTime4 = System.currentTimeMillis();
//		double x = cust.vector.x;
//		double y = cust.vector.y;
		SubArea subArea = new SubArea();
		// ArrayList<RouteSection> subRouteSection =
		// subArea.getSubArea(routeSection,x-r-1,x+r+1,y-r-1,y+r+1);
//		long startTime5 = System.currentTimeMillis();
//		System.out.println("r: "+r);
		ArrayList<RouteSection> subRouteSection = subArea
				.getSubArea2(routeSection, (r + 0.01)*(r+0.01), cust);
//		System.out.println("subRouteSection.size: "+subRouteSection.size());
//		long endTime5 = System.currentTimeMillis();
//		System.out.println("5.time in refinement: " + (endTime5 - startTime5)
//				+ "ms___________");
		// System.out.println("----- the numbe of routes in range D:
		// "+subRouteSection.size());
		// �÷�Χֻ��һ����
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
//			System.out.println("taxiss.get(t): "+taxiss.get(t));
			// ������ܻ��������Ϊtaxiss��С�п��ܲ�����0
			cust.nnTaxi = taxiss.get(t);
		} else
		{
			Graph g = new Graph();
			int flagCust = 0;
			int flagNNTaxi = 0;
//			long startTime6 = System.currentTimeMillis();
			
			for (int i = 0; i < subRouteSection.size(); i++)
			{
				g.addEdge(subRouteSection.get(i).I1.vectorID,
						subRouteSection.get(i).I2.vectorID,
						subRouteSection.get(i).length);
				if (subRouteSection.get(i).I1.vectorID == cust.vector.vectorID
						|| subRouteSection
								.get(i).I2.vectorID == cust.vector.vectorID)
					flagCust = 1;
				if (subRouteSection.get(i).I1.vectorID == NNTaxi.vector.vectorID
						|| subRouteSection
								.get(i).I2.vectorID == NNTaxi.vector.vectorID)
					flagNNTaxi = 1;

			}
			
//			long endTime6 = System.currentTimeMillis();
//			System.out.println("6.time in refinement: " + (endTime6 - startTime6)
//					+ "ms___________");
			
			if (flagNNTaxi != 1 || flagCust != 1)
			{
				System.out.println("NOoooooooooooooo");
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
				// ������ܻ��������Ϊtaxiss��С�п��ܲ�����0
				cust.nnTaxi = taxiss.get(t);
			} else
			{
//				long startTime7 = System.currentTimeMillis();
				g.dijkstra(cust.vector.vectorID);
//				long endTime7 = System.currentTimeMillis();
//				System.out.println("7.time in refinement: " + (endTime7 - startTime7)
//						+ "ms___________");
				
				// ���cust�Ƿ���ֱ�ӵ���taxiss�е�taxi,��ȥ��cust���ܵ���taxi
				// System.out.println("taxiss.size: "+taxiss.size());
//				long startTime8 = System.currentTimeMillis();
				double minDis = Double.MAX_VALUE;
				for (int j = 0; j < taxiss.size(); j++)
				{
					if (g.cleanVertex(taxiss.get(j).vector.vectorID) == null)
						continue;
					if (g.cleanVertex(
							taxiss.get(j).vector.vectorID).dist < minDis)
					{
						minDis = g.cleanVertex(
								taxiss.get(j).vector.vectorID).dist;
					}
				}

				t0 = minDis / speed;
				double minBalance = Double.MAX_VALUE;
				int m = 0;
				for (int i = 0; i < taxiss.size(); i++)
				{
					if (g.cleanVertex(taxiss.get(i).vector.vectorID) == null)
						continue;
					taxiss.get(i).Balance = getBalance(taxiss.get(i),
							g.cleanVertex(taxiss.get(i).vector.vectorID).dist,
							t0);
					taxiss.get(i).ti = g
							.cleanVertex(taxiss.get(i).vector.vectorID).dist
							/ speed;
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
//				long endTime8 = System.currentTimeMillis();
//				System.out.println("8.time in refinement: " + (endTime8 - startTime8)
//						+ "ms___________");
				
//				long endTime4 = System.currentTimeMillis();
//				System.out.println("4.time in refinement: "+(endTime4-startTime4)+"ms________");
				// quickSort(taxiss, 0, taxiss.size()-1);
				cust.nnTaxi = taxiss.get(m);
				/*
				 * int flag = 0; ArrayList<Taxi> taxiss2 = new
				 * ArrayList<Taxi>(); for(int j=0;j<taxiss.size();j++) {
				 * for(String vectorID : g.vertexMap.keySet()) {
				 * if(taxiss.get(j).vector.vectorID == vectorID &&
				 * g.vertexMap.get(vectorID).dist<10000) { flag++; //
				 * taxiss.get(j).Lenght = g.vertexMap.get(vectorID).dist;
				 * taxiss2.add(taxiss.get(j)); }
				 * 
				 * } } // System.out.println("flag: "+flag); //
				 * System.out.println("taxiss2.size after changed: "
				 * +taxiss2.size()); //�ҵ���쵽��cust��taxi
				 * 
				 * for(int i=0;i<taxiss2.size();i++) {
				 * if(g.cleanVertex(taxiss2.get(i).vector.vectorID).dist <
				 * minDis) { minDis =
				 * g.cleanVertex(taxiss2.get(i).vector.vectorID).dist; } } t0 =
				 * minDis/speed; double minBalance = Double.MAX_VALUE; int m =
				 * 0; for(int i=0;i<taxiss2.size();i++) { taxiss2.get(i).Balance
				 * = getBalance(taxiss2.get(i),
				 * g.cleanVertex(taxiss2.get(i).vector.vectorID).dist, t0);
				 * taxiss2.get(i).ti =
				 * g.cleanVertex(taxiss2.get(i).vector.vectorID).dist/speed;
				 * if(taxiss2.get(i).Balance < minBalance) { minBalance =
				 * taxiss2.get(i).Balance; m = i; }
				 * 
				 * if(taxiss2.get(i).Balance == minBalance) {
				 * if(taxiss2.get(i).ti < taxiss2.get(m).ti) { m = i; } } } //
				 * quickSort(taxiss2, 0, taxiss2.size()-1); cust.nnTaxi =
				 * taxiss2.get(m);
				 */
			}
		}
	}

	// ϸ�������ĳ�ʼ��,��һ��Ӧ��ʹ���Ƽ������
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
		for (int i = 0; i < custList.size(); i++) // ��ÿ��cust����ϸ������
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
				// ��¼��cust�����taxi
				if (Distance.distance2(custList.get(i).s,
						taxiList2.get(j).s) < minDis)
				{
					minDis = Distance.distance2(custList.get(i).s,
							taxiList2.get(j).s);
					t = flag2;
				}

				// ��ó�ʼ����СminProfit
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
			// System.out.println("*****CustID"+cust.custID);
			// System.out.println("cust: "+cust.vector.vectorID);
			// System.out.println("nnTaxi: "+taxis.get(t).vector.vectorID);
			// System.out.println("t: "+t);
			// System.out.println("flag1: "+flag1);
			// System.out.println("flag2: "+flag2);
			// System.out.println("flag: "+flag);
			// if(t > 99)
			// System.out.println("����");

//			long startTime1 = System.currentTimeMillis();
			 t0 = getNNTaxiDis(cust, taxis.get(t), routeSections, 10)/speed;
//			t0 = g.BFS(cust.vector.vectorID, taxis.get(t).vector.vectorID)/ speed;
//			long endTime1 = System.currentTimeMillis();
//			System.out.println("getNNTaxiDis.time in Refinement: "
//					+ (endTime1 - startTime1) + "ms_________");

			cust.t0 = t0;
			// cust.nnTaxi = taxis.get(t);
			// System.out.println("ŷʽ���룺
			// "+Distance.distance2(cust.vector,taxis.get(t).vector ));
			// System.out.println("dist: "+ speed * t0);
			// System.out.println("t0: "+t0);

//			long startTime2 = System.currentTimeMillis();
			refinement(cust, t0, taxis.get(t), minProfit, taxis, routeSections);
//			long endTime2 = System.currentTimeMillis();
//			System.out.println("refinement.time in Refinement: "
//					+ (endTime2 - startTime2) + "ms__________");

			custs.add(cust);
			// System.out.println("cust.nnTaxi.index: "+cust.nnTaxi.index);
			// System.out.println("taxiList2.get(cust.nnTaxi.index) =
			// "+taxiList2.get(cust.nnTaxi.index).taxiID);
			taxiList2.set(cust.nnTaxi.index, null);
			// System.out.println("taxiList2.get(cust.nnTaxi.index) =
			// "+taxiList2.get(cust.nnTaxi.index));
		}
		return custs;
	}

	// �õ���cust�����taxi��cust�ľֲ����·��
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
		// �������taxi��cust��һ��vector
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

	// ��֧�綨��

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

	// Balance����
	public double getBalance(Taxi taxi, double d, double t0)
	{
		return taxi.profit + constant * (Math.pow(Math.E, -t0 + d / speed) - 1);
	}

	// ��������
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
