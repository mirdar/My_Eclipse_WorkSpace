package com.mirdar.GA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/*
 * 1. 每个网点分开讨论
 * 2. 每条路径都会回网点
 * GA要重新做，生成路线的时候要规定路线的长度，不然其超过720，超过的为无效路径
 * 在計算fitness时，如何解编码也是个问题，选择最优解码方式
 */
public class GA {

	public int length ; //染色体长度
	public int maxNum = 140; //每辆车最大容量
	public int popSize = 100; //种群大小
	public double pc = 0.88; //交叉率
	public double pm = 0.1; //变异率
	public int maxGen = 50; //或者染色体适应度提升为最初染色体多少倍截止
	public ArrayList<Chomo> bestChomo = new ArrayList<Chomo>();//用来保存最好的染色体
	public Map<Integer,Point> dataMap;
	
	//全部数据一起处理
	public Map<String,Integer> len = new HashMap<String,Integer>();
	public Map<String,Map<Integer,Point>> allDataMap;
	
	public Map<String,ArrayList<Line>> lines = new HashMap<String,ArrayList<Line>>(); //这里只记录了一个网点
	
	public int allTime = 0;
	
	public int recordSize = 0;
	
	public static void main(String[] args)
	{
		
		String filename = "F:/ML/last mile delivery/A066.csv";
		GA ga = new GA();
		ga.dataMap = ga.readData(filename);
		System.out.println("spot_id  num  lon  lan  ID");
		for(Integer key : ga.dataMap.keySet()) //打印数据
		{
			Point point = ga.dataMap.get(key);
			System.out.println(point.getPointName()+"  "+point.getGoods_num()+"  "+
							point.getLon()+"  "+point.getLan()+"  "+point.getPointId());
		}
		ArrayList<Chomo> pop = ga.getInitPop(); //初始群体生成成功
		System.out.println("ininPop.size: "+pop.size());
		
		ArrayList<Chomo> newPop = new ArrayList<Chomo>();
		for(int i=0;i<ga.maxGen;i++)
		{
			newPop = ga.select(pop);
			newPop = ga.crossAndVar(newPop);
			pop = newPop;
		}
		
		/*newPop = ga.select(pop);
		newPop = ga.crossAndVar(newPop);
		pop = newPop;
		while(! ga.validChomo(ga.bestChomo.get(ga.bestChomo.size()-1).getChomo()))
		{
			newPop = ga.select(pop);
			newPop = ga.crossAndVar(newPop);
			pop = newPop;
		}*/
		
		
		System.out.println(ga.bestChomo.size());
		ga.printChomo(ga.bestChomo.get(ga.bestChomo.size()-1));
//		ga.printAllocation(ga.bestChomo.get(0).getChomo());
//		ga.printAllocation(ga.bestChomo.get(ga.bestChomo.size()-1).getChomo());
//		ga.printResult(ga.bestChomo.get(ga.bestChomo.size()-1).getChomo());
		System.out.println(ga.fitness(ga.bestChomo.get(0)));
		System.out.println(ga.fitness(ga.bestChomo.get(ga.bestChomo.size()-1)));
		System.out.println(ga.allTime);
		System.out.println(ga.lines.size());
		for(String key : ga.lines.keySet())
		{
			System.out.println(key+" "+ga.lines.get(key).size());
			for(int i=0;i<ga.lines.get(key).size();i++)
				System.out.println(ga.lines.get(key).get(i).spot_id);
		}
		
		
		
		
		/*for(int i=0;i< ga.bestChomo.size();i++)//打印每个种群中的最好解
		{
			int[] x = ga.bestChomo.get(i).getChomo();
			for(int j=0;j<x.length;j++)
			{
				System.out.print(x[j]+" ");
			}
			System.out.println();
		}*/
		
		//用来测试的
		/*Chomo chomo1 = new Chomo();
		Chomo chomo2 = new Chomo();
		int[] c1 = {1,2,4,3,7,6,5,9,8};
		int[] c2 = {1,3,4,6,7,2,5,8,9};
		chomo1.setChomo(c1);
		chomo2.setChomo(c2);
		Chomo chomo3 = ga.cross(chomo1, chomo2);
		ga.variation(chomo3);*/
	}
	
	public Chomo getChomo() //利用随机排列得到一个染色体，因为没有车辆限制，所以没有验证染色体
	{
		int[] sequence = new int[length];
		Chomo chomo = new Chomo();
		for(int i = 0; i < length; i++){ 
		sequence[i] = i+1;
		}
		Random random = new Random();
		for(int i = 0; i < length; i++)
		{ //产生随机排列
			int p = random.nextInt(length);
			int tmp = sequence[i];
			sequence[i] = sequence[p];
			sequence[p] = tmp;
		}
		random = null;
		chomo.setChomo(sequence);
		chomo.setFit(fitness(chomo));//求适应度
		return chomo;
	}
	
	
	public ArrayList<Chomo> getInitPop() //初始化得到一个种群
	{
		ArrayList<Chomo> pop = new ArrayList<Chomo>();
		for(int i=0;i<popSize;i++)
		{
			Chomo chomo = getChomo();
			pop.add(chomo);
		}
//		normalization(pop);
		return pop;
	}
	 //选择算子,根据适应度函数选择个体进入交叉池，轮盘选择
	public ArrayList<Chomo> select(ArrayList<Chomo> pop)
	{
		double[] p = new double[pop.size()]; //概率函数
		double[] fit = new double[pop.size()];
		
		ArrayList<Chomo> crossPool = new ArrayList<Chomo>();
		quickSort(pop, 0, pop.size()-1); //升序排列
		ArrayList<Chomo> popSort = pop;
		if(bestChomo.size() == 0)
			bestChomo.add(popSort.get(popSort.size()-1)); //每一轮最好的染色体都被保存
		else
			if(bestChomo.get(bestChomo.size()-1).getFit()<=popSort.get(popSort.size()-1).getFit())
				bestChomo.add(popSort.get(popSort.size()-1));
		double fitSum = 0;
		for(int i=0;i<pop.size();i++)
		{
			fitSum+= popSort.get(i).getFit();
			fit[i] = popSort.get(i).getFit();
		}
		for(int i=0;i<fit.length;i++) ////通过fit计算衰减概率
		{
			if(i-1>=0)
			{
				if(i !=fit.length-1)
					p[i]=p[i-1] + fit[i]/fitSum;
				else
					p[i] = 1;
			}
			else 
				p[i]= fit[i]/fitSum; 
		}
		for(int i=0;i<popSize;i++) //重新产生popSize个个体的种群
		{
			double rand = new Random().nextDouble();
			for(int m=0;m<p.length;m++) //根据概率分布加入对应的个体
			{
				if(m == 0)
				{
					if(rand <= p[m])
						crossPool.add(popSort.get(m));
				}
				else
				{
					if(rand > p[m-1] && rand <= p[m])
						crossPool.add(popSort.get(m));
				}
					
			}
		}
		return crossPool;
	}
	
	public ArrayList<Chomo> crossAndVar(ArrayList<Chomo> pop) //交叉变异操作
	{
		 ArrayList<Chomo> newPop1 = new  ArrayList<Chomo>();
		 for(int i=0;i<pop.size()/2;i++) //分奇偶数，但是这里设置为偶数,交叉
		 {
			 double rand = new Random().nextDouble();
			 if(rand<=pc)
			 {
				 Chomo chomo1 = cross(pop.get(i),pop.get(i+pop.size()/2));
				 newPop1.add(chomo1);
				 Chomo chomo2 = cross(pop.get(i+pop.size()/2),pop.get(i));
				 newPop1.add(chomo2);
			 }
			 else
			 {
				 newPop1.add(pop.get(i));
				 newPop1.add(pop.get(i+pop.size()/2));
			 }
		 }
//		 normalization(newPop1);
//		 for(int i=0;i<pop.size();i++) //将交叉的一代与变异的一代混合，可能容易产生局部最优
//		 {
//			 newPop1.add(pop.get(i));
//		 }
//		 quickSort(newPop1, 0, newPop1.size()-1); //升序排列
//		 pop = new ArrayList<Chomo>();
//		 for(int i=newPop1.size()-1;i>newPop1.size()/2;i--)
//		 {
//			 pop.add(newPop1.get(i));
//		 }
		 pop = newPop1;
		 ArrayList<Chomo> newPop2 = new  ArrayList<Chomo>();
		 for(int i=0;i<pop.size();i++) //变异
		 {
			 double rand = new Random().nextDouble();
			 if(rand<=pm)
			 {
				 Chomo chomo = variation(pop.get(i));
				 newPop2.add(chomo);
			 }
			 else
			 {
				 newPop2.add(pop.get(i));
			 }
		 }
		 
//		 normalization(newPop2);
		 return newPop2;
	}
	
	public Chomo cross(Chomo chomo1,Chomo chomo2) //交叉算子，顺序交叉
	{
		Chomo chomo = new Chomo();
		int[] code1 = new int[chomo1.getChomo().length];
		int[] code2 = new int[chomo2.getChomo().length];
		Random random1 = new Random();
		Random random2 = new Random();
		int rand1 = random1.nextInt(chomo1.getChomo().length);
		int rand2 = random2.nextInt(chomo1.getChomo().length); 
		if(rand1 >= rand2)
		{
			int temp = rand1;
			rand1 = rand2;
			rand2 = temp;
		}
		for(int i=rand1;i<=rand2;i++)//产生染色体1的子序列
		{
			code1[i] = chomo1.getChomo()[i];
		}
		for(int j=0;j<chomo2.getChomo().length;j++)
		{
			code2[j] = 0;
			for(int i=rand1;i<=rand2;i++)
			{
				if(chomo2.getChomo()[j] == code1[i]) //从染色体2中将染色体1中子序列元素设置为-1
					code2[j] = -1;
			}
			if(code2[j] != -1)
				code2[j] = chomo2.getChomo()[j];
		}
		int j=0;
		for(int i=0;i<code1.length;i++)
		{
			if(i == rand1)
			{
				i = rand2 + 1;
				if(i>=code1.length)
					break;
			}
			while(code2[j] == -1)
			{
				j++;
			}
			code1[i] = code2[j];
			j++;
		}
		chomo.setChomo(code1);
		chomo.setFit(fitness(chomo));//求适应度
		return chomo;
	}
	public Chomo variation(Chomo chomo) //变异算子，2-交换变异
	{
		Chomo chomo2 = new Chomo();
		int[] code = new int[chomo.getChomo().length];
		int rand1 = new Random().nextInt(code.length);
		int rand2 = new Random().nextInt(code.length);
		while(rand1 == rand2)
		{
			rand2 = new Random().nextInt(code.length);
		}
		for(int i=0;i<code.length;i++)
		{
			code[i] = chomo.getChomo()[i];
		}
		int temp = code[rand1];
		code[rand1] = code[rand2];
		code[rand2] = temp;
//		for(int i=0;i<code.length;i++)
//			System.out.print(code[i]+" ");
		chomo2.setChomo(code);
		chomo2.setFit(fitness(chomo2));//求适应度
		return chomo2;
	}
	
	//这个fitness设置优点不合理，应该将时间限制作为惩罚项加入，而不是作为硬性条件！！！！！！
	public double  fitness(Chomo cho) //计算适应度
	{
		int[] chomo = cho.chomo;
		double fit = 0; //适应度
		int carNum =0; //车辆数目
		int num = 0; //每辆车的累计件数，每辆车后都清零
		int eachFit = 0;
		Path path = new Path();
		for(int i=0;i<chomo.length;i++)
		{
			num+=dataMap.get(chomo[i]).getGoods_num();
			// 
			if(num>140 || eachFit > 720)
			{
				cho.path.add(path);
				path = new Path();
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i-1]));//子路径结束，回到网点
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i]));//另外一条子路径开始
				if(eachFit-dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]))+
						dis(dataMap.get(0),dataMap.get(chomo[i-1])) > 720)
				{
					fit+= 10000000*(eachFit-dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]))+
							dis(dataMap.get(0),dataMap.get(chomo[i-1])) - 720);
				}
				eachFit = (int) (dis(dataMap.get(0),dataMap.get(chomo[i]))+Math.round(3*Math.sqrt(dataMap.get(chomo[i]).goods_num)+5));
				num = dataMap.get(chomo[i]).getGoods_num();
				path.path.add(chomo[i]);
				carNum++;
			}
			else
			{
				if(i>=1)
				{
					fit+=dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]));//子路径距离都相加
					eachFit+= dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]))+Math.round(3*Math.sqrt(dataMap.get(chomo[i]).goods_num)+5);
					path.path.add(chomo[i]);
					if(i!=chomo.length-1 && eachFit+dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1])) > 720)
						eachFit+= dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1]));
				}
				else
				{
					fit+= dis(dataMap.get(0),dataMap.get(chomo[i])); //初始情况
					eachFit+= dis(dataMap.get(0),dataMap.get(chomo[i]))+Math.round(3*Math.sqrt(dataMap.get(chomo[i]).goods_num)+5);
					path.path.add(chomo[i]);
				}
			}
		}
		cho.path.add(path);
		fit+= dis(dataMap.get(0),dataMap.get(chomo[chomo.length-1]));
		//这里我没有消除量纲，而是直接在车辆数目前乘以一个权重
//		fit+=+carNum; //暂时考虑时间，以及规定车辆数目，加了后需要改变fitness函数
		return 10/fit;
	}
	
	public int printChomo(Chomo chomo)
	{
		
		for(int i=0;i<chomo.path.size();i++)
		{
			int time=0;
			for(int j=0;j<chomo.path.get(i).path.size();j++)
			{
				if(j == 0)
					time+= dis(dataMap.get(0),
						dataMap.get(chomo.path.get(i).path.get(j)))+3*Math.sqrt(dataMap.get(chomo.path.get(i).path.get(j)).goods_num)+5;
				else
					time+= dis(dataMap.get(chomo.path.get(i).path.get(j-1)),
							dataMap.get(chomo.path.get(i).path.get(j)))+
							3*Math.sqrt(dataMap.get(chomo.path.get(i).path.get(j)).goods_num)+5;
//				if(j == chomo.path.size()-1)
//					time+= dis(dataMap.get(0),
//							dataMap.get(chomo.path.get(i).path.get(j)));
				
			}
//			System.out.println("Path"+i+"路径长为: "+time);
			recordSize+= chomo.path.get(i).path.size();
		}
		
		return printPaths(chomo.path);
	}

	public int printPaths(ArrayList<Path> paths)
	{
//		int time = 0;
//		for(Integer key : dataMap.keySet())
//		{
//			if(key != 0)
//				time+= 3*Math.sqrt(dataMap.get(key).goods_num)+5;
//		}
//		System.out.println("总共要停留时间："+time);
		int arriveTime = 0;
//		System.out.println("paths.size: "+paths.size());
		ArrayList<Line> lineList = new ArrayList<Line>();
		lines.put(dataMap.get(0).pointName, lineList); //一个网点的所有路线
		for(int i=0;i<paths.size();i++)
		{
			int firstTime = 0;
			int endTime = 0;
			Line line = new Line(); //一条路线
			Record record = new Record(); //路线中的一条记录
			Path p = paths.get(i);
			for(int j=0;j<p.path.size();j++)
			{
				record = new Record();
				record.place_id=dataMap.get(0).pointName;
				record.arriveTime=arriveTime;
				record.departureTime=arriveTime;
				record.num=dataMap.get(p.path.get(j)).goods_num;
				record.order_id=dataMap.get(p.path.get(j)).order_id;
				line.line.add(record);
				firstTime = arriveTime;
//				printRecord(dataMap.get(0).pointName, arriveTime, arriveTime, "",dataMap.get(p.path.get(j)).goods_num);
//				System.out.print(p.path.get(j)+" ");
			}
//			System.out.println();
			for(int j=0;j<p.path.size();j++)
			{
				if(j == 0)
				{
					
					arriveTime+= dis(dataMap.get(0),dataMap.get(p.path.get(j)));
					record = new Record();
					record.place_id=dataMap.get(p.path.get(j)).pointName;
					record.arriveTime=arriveTime;
					arriveTime+=Math.round(3*Math.sqrt(dataMap.get(p.path.get(j)).goods_num)+5);
					record.departureTime=arriveTime;
					record.num=-dataMap.get(p.path.get(j)).goods_num;
					record.order_id=dataMap.get(p.path.get(j)).order_id;
//					System.out.println("到"+p.path.get(j)+"的时间："+arriveTime+" ");
//					printRecord(dataMap.get(p.path.get(j)).pointName,
//							arriveTime,
//							(int)(arriveTime+3*Math.sqrt(dataMap.get(p.path.get(j)).goods_num)+5),
//									"-",dataMap.get(p.path.get(j)).goods_num);
					
					if(j == p.path.size()-1) //路线中只有一个点
					{
						arriveTime+= dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.endToStart = dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.spot_id = dataMap.get(p.path.get(j)).pointName;
						endTime = arriveTime;
						line.time = arriveTime; //每条线路的总开销
//						System.out.println("到"+p.path.get(j)+"的时间："+arriveTime+" ");
					}
					line.line.add(record);
				}
				else
				{
					arriveTime+= dis(dataMap.get(p.path.get(j-1)),dataMap.get(p.path.get(j)));
					record = new Record();
					record.place_id=dataMap.get(p.path.get(j)).pointName;
					record.arriveTime=arriveTime;
//					printRecord(dataMap.get(p.path.get(j)).pointName,
//						arriveTime,
//						(int)(arriveTime+3*Math.sqrt(dataMap.get(p.path.get(j)).goods_num)+5),
//						"-",dataMap.get(p.path.get(j)).goods_num);
					arriveTime+=Math.round(3*Math.sqrt(dataMap.get(p.path.get(j)).goods_num)+5);
					record.departureTime=arriveTime;
					record.num=-dataMap.get(p.path.get(j)).goods_num;
					record.order_id=dataMap.get(p.path.get(j)).order_id;
					line.line.add(record);
//					System.out.println("到"+p.path.get(j)+"的时间："+arriveTime+" ");
					if(j == p.path.size()-1)
					{
						line.endToStart = dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.spot_id = dataMap.get(p.path.get(j)).pointName;
						arriveTime+= dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.time = arriveTime; //每条线路的总开销
						endTime = arriveTime;
//						System.out.println("到"+p.path.get(j)+"的时间："+arriveTime+" ");
					}
				}
			}
			
			line.time = endTime - firstTime;
			lineList.add(line);
//			System.out.println();
		}
		allTime+= arriveTime;
//		System.out.println("需要的时间为："+arriveTime+"/720 = "+(1+arriveTime/720)+"个快递员");
//		System.out.println(dis(dataMap.get(63),dataMap.get(63)));
		return (1+arriveTime/720);
	}
	//可以改成向文件中追加内容
	public void printRecord(String addr,int arriveTime,int departureTime,String amount,int num)
	{
		System.out.println(addr+" "+arriveTime+" "+departureTime+" "+amount+num);
	}
	
	
	public int dis(Point pointA,Point pointB) //两点之间的距离
	{
		int cost = 0;
		cost = (int) Math.round(2*6378137*Math.asin(Math.sqrt(Math.pow(Math.sin(Math.PI/180.0*(pointA.getLan()-pointB.getLan())/2),2)+
				Math.cos(Math.PI/180.0*pointA.getLan())*Math.cos(Math.PI/180.0*pointB.getLan())*
				Math.pow(Math.sin(Math.PI/180.0*(pointA.getLon()-pointB.getLon())/2),2)))/250);
		
		return cost;
	}
	//快牌，用来找到每一代中最大的fitness的个体
	public void quickSort(ArrayList<Chomo> pop,int s,int e)
	{
		if(s < e)
		{
			int m = partition(pop, s, e);
			quickSort(pop,s,m-1);
			quickSort(pop, m+1, e);
		}
	}
	
	public int partition(ArrayList<Chomo> pop,int s,int e) //以第一个元素作为分割元素
	{
		int m = s;
		Chomo cho = pop.get(s);
		for(int i=s+1;i<=e;i++)
		{
			if(pop.get(i).getFit() <= cho.getFit())
			{
				m++;
				Chomo chomo = pop.get(i);
				pop.set(i, pop.get(m));
				pop.set(m, chomo);
			}
		}
		
		Chomo chomo = pop.get(m);
		pop.set(m, pop.get(s));
		pop.set(s, chomo);
		
		return m;
	}
	
	public Map<Integer,Point> readData(String filename)
	{
		Map<Integer,Point> map = new HashMap<Integer,Point>();
		
		File file = new File(filename);
		BufferedReader reader = null;
		int i = 0; //网点的id为0
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Point point = new Point();
				point.setPointName(strings[0]);
				point.setGoods_num(Integer.parseInt(strings[1]));
				point.setLon(Double.parseDouble(strings[2]));
				point.setLan(Double.parseDouble(strings[3]));
				point.setPointId(i); //因为每个配送点只有一个，所以这里直接映射id
				map.put(point.getPointId(), point);
				i++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		length = i-1; //染色体长度
		return map;
	}
	
	public Map<String,Map<Integer,Point>> readAllData(String filename)
	{
		Map<String,Map<Integer,Point>> mapAll = new HashMap<String,Map<Integer,Point>>();
		Map<Integer,Point> map = null;
		File file = new File(filename);
		BufferedReader reader = null;
		String siteName = null;
		String lastName = "rasd";
		int i = 0; //网点的id为0
		int j = 0;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Point point = new Point();
				siteName = strings[0];
				if(!lastName.equals(siteName) && !lastName.equals("rasd"))
					len.put(lastName,i-1);
				if(!mapAll.containsKey(siteName))
				{
					map = new HashMap<Integer,Point>();
					mapAll.put(siteName, map);
					i = 0;
					j++;
				}
				lastName = siteName;
				point.setPointName(strings[1]);
				point.setGoods_num(Integer.parseInt(strings[2]));
				point.setLon(Double.parseDouble(strings[3]));
				point.setLan(Double.parseDouble(strings[4]));
				point.order_id = strings[5];
				point.setPointId(i); //因为每个配送点只有一个，所以这里直接映射id
				
				map.put(point.getPointId(), point);
				i++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		len.put(siteName,i-1);
		return mapAll;
	}
	
	public boolean validChomo(Chomo chomo) //验证染色体是否达到要求
	{
		printChomo(chomo);
		int j = 0;
		String keys = null;
		for(String key : lines.keySet())
		{
			keys = key;
			for(int i=0;i<lines.get(key).size();i++)
			{
				if(lines.get(key).get(i).time <= 720)
				{
					j++;
				}
			}
		}
		if(j == lines.get(keys).size())
			return true;
		return false;
		
	}
	
	public void normalization(ArrayList<Chomo> pop) //归一化
	{
		double[] minMax = maxAndMin(pop);
		double diff = minMax[1] - minMax[0];
		for(int i=0;i<pop.size();i++)
		{
			pop.get(i).setFit((minMax[1]-pop.get(i).getFit())/diff);
		}
	}
	
	public double[] maxAndMin(ArrayList<Chomo> pop)
	{
		double[] maxMin = new double[2];
		maxMin[0] = Double.MAX_VALUE;
		maxMin[1] = 0.0;
		for(int i=0;i<pop.size();i++)
		{
			double fit = pop.get(i).getFit();
			if(maxMin[0] > fit)
			{
				maxMin[0] = fit;
			}
			if(maxMin[1] < fit)
			{
				maxMin[1] = fit;
			}
		}
		return maxMin;
	}
	

	
	
	/*public void  printAllocation(int[] chomo) //计算适应度
	{
		int fit = 0; //适应度
		int carNum =1; //车辆数目
		int num = 0; //每辆车的累计件数，每辆车后都清零
		int sum = 0;
		int eachFit = 0;
		for(int i=0;i<chomo.length;i++)
		{
			num+=dataMap.get(chomo[i]).getGoods_num();
			if(num>140 || eachFit > 720)
			{
				
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i-1]));//子路径结束，回到网点
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i]));//另外一条子路径开始
				sum+= num-dataMap.get(chomo[i]).getGoods_num();
				System.out.println("车辆"+carNum+"载重： "+(num-dataMap.get(chomo[i]).getGoods_num()));
				System.out.println();
				num = dataMap.get(chomo[i]).getGoods_num();
				carNum++;
				eachFit = dis(dataMap.get(0),dataMap.get(chomo[i]));
//				System.out.print(chomo[i]+" ");
				System.out.println("到"+chomo[i]+"的时间："+fit+" ");
			}
			else
			{
				if(i-1>=0)
				{
					fit+=dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]));//子路径距离都相加
//					System.out.print(chomo[i]+" ");
					eachFit+= dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]));
					if(i!=chomo.length-1 && eachFit+dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1])) > 720)
						eachFit+= dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1]));
					System.out.println("到"+chomo[i]+"的时间："+fit+" ");
				}
				else
				{
					fit+= dis(dataMap.get(0),dataMap.get(chomo[i])); //初始情况
//					System.out.print(chomo[i]+" ");
					eachFit+= dis(dataMap.get(0),dataMap.get(chomo[i]));
					System.out.println("到"+chomo[i]+"的时间："+fit+" ");
				}
			}
		}
		sum+= num;
		System.out.println("车辆"+carNum+"载重： "+num);
		System.out.println("车辆数量： "+carNum);
		//再加上每个点的停留时间，停留时间是无法避免的，与该配送点的货物件数有关
		//加上停留时间后，就可以为每个网点大概分配多少人了
		//首先验证一下，这里的时间计算是否准确
		fit+= dis(dataMap.get(0),dataMap.get(chomo[chomo.length-1]));
		System.out.println("如果用一辆车的话，车间消耗： "+fit); 
		System.out.println("sum: "+sum);
	}
	*/
	
	/*public int printResult(int[] chomo)
	{
		ArrayList<Path> paths = new ArrayList<Path>();
		Path path = new Path();
		
//		int carNum =1; //车辆数目
		int num = 0; //每辆车的累计件数，每辆车后都清零
		for(int i=0;i<chomo.length;i++)
		{
			num+=dataMap.get(chomo[i]).getGoods_num();
			if(num>140)
			{
				paths.add(path);
				path = new Path();
				path.path.add(chomo[i]);
//				System.out.println("车辆"+carNum+"载重： "+(num-dataMap.get(chomo[i]).getGoods_num()));
				num = dataMap.get(chomo[i]).getGoods_num();
//				carNum++;
			}
			else
			{
				path.path.add(chomo[i]);
			}
			if(num<=140 && chomo.length-1==i)
			{
				paths.add(path);
			}
		}
		return printPaths(paths);
	}*/
	
}
