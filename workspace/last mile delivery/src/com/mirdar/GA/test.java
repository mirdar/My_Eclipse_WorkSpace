package com.mirdar.GA;

import java.util.ArrayList;

/*
 * 可以得到所有电商订单的规划，如果要优化，就得一个个网点进行优化，然后再是处理O2o订单
 * 
 */
public class test {

	public static void main(String[] args)
	{
		/*ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(1);
		a.add(1);
		System.out.println(a.size());
		for(int i=0;i<a.size();i++)
		{
			System.out.println(a.get(i));
		}*/
		
		String filename = "F:/ML/last mile delivery/branch_data.csv";
		GA ga = new GA();
		int cars = 0;
		ga.allDataMap = ga.readAllData(filename);
		System.out.println(ga.allDataMap.size()+"  "+ga.len.size());
		for(String key1 : ga.allDataMap.keySet())
		{
			System.out.println("网点为："+key1);
			System.out.println("染色体长度："+ga.len.get(key1));
			ga.dataMap = ga.allDataMap.get(key1);
			ga.length = ga.len.get(key1);
			ga.bestChomo = new ArrayList<Chomo>();
//			System.out.println("spot_id  num  lon  lan  ID");
//			for(Integer key : ga.dataMap.keySet()) //打印数据
//			{
//				Point point = ga.dataMap.get(key);
//				System.out.println(point.getPointName()+"  "+point.getGoods_num()+"  "+
//								point.getLon()+"  "+point.getLan()+"  "+point.getPointId());
//			}
			ArrayList<Chomo> pop = ga.getInitPop(); //初始群体生成成功
//			System.out.println("ininPop.size: "+pop.size());
//			ga.quickSort(pop, 0, pop.size()-1);
//			for(int i=0;i<pop.size();i++)
//			{
//				System.out.println(pop.get(i).getFit());
//			}
			
			ArrayList<Chomo> newPop = new ArrayList<Chomo>();
			for(int i=0;i<ga.maxGen;i++)
			{
				newPop = ga.select(pop);
				newPop = ga.crossAndVar(newPop);
				pop = newPop;
			}
//			System.out.println("bestChomo.size: "+ga.bestChomo.size());
//			System.out.println("bestChomo.length: "+ga.bestChomo.get(ga.bestChomo.size()-1).getChomo().length);
//			ga.printAllocation(ga.bestChomo.get(ga.bestChomo.size()-1).getChomo());
			
			cars+= ga.printChomo(ga.bestChomo.get(ga.bestChomo.size()-1));
		}
		System.out.println("电商订单总共需要快递员数量为： "+cars);
		System.out.println(ga.allTime);
	}
}
