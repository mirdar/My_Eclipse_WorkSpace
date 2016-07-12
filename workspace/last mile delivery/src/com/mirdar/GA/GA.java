package com.mirdar.GA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/*
 * 1. ÿ������ֿ�����
 * 2. ÿ��·�����������
 * GAҪ������������·�ߵ�ʱ��Ҫ�涨·�ߵĳ��ȣ���Ȼ�䳬��720��������Ϊ��Ч·��
 * ��Ӌ��fitnessʱ����ν����Ҳ�Ǹ����⣬ѡ�����Ž��뷽ʽ
 */
public class GA {

	public int length ; //Ⱦɫ�峤��
	public int maxNum = 140; //ÿ�����������
	public int popSize = 100; //��Ⱥ��С
	public double pc = 0.88; //������
	public double pm = 0.1; //������
	public int maxGen = 50; //����Ⱦɫ����Ӧ������Ϊ���Ⱦɫ����ٱ���ֹ
	public ArrayList<Chomo> bestChomo = new ArrayList<Chomo>();//����������õ�Ⱦɫ��
	public Map<Integer,Point> dataMap;
	
	//ȫ������һ����
	public Map<String,Integer> len = new HashMap<String,Integer>();
	public Map<String,Map<Integer,Point>> allDataMap;
	
	public Map<String,ArrayList<Line>> lines = new HashMap<String,ArrayList<Line>>(); //����ֻ��¼��һ������
	
	public int allTime = 0;
	
	public int recordSize = 0;
	
	public static void main(String[] args)
	{
		
		String filename = "F:/ML/last mile delivery/A066.csv";
		GA ga = new GA();
		ga.dataMap = ga.readData(filename);
		System.out.println("spot_id  num  lon  lan  ID");
		for(Integer key : ga.dataMap.keySet()) //��ӡ����
		{
			Point point = ga.dataMap.get(key);
			System.out.println(point.getPointName()+"  "+point.getGoods_num()+"  "+
							point.getLon()+"  "+point.getLan()+"  "+point.getPointId());
		}
		ArrayList<Chomo> pop = ga.getInitPop(); //��ʼȺ�����ɳɹ�
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
		
		
		
		
		/*for(int i=0;i< ga.bestChomo.size();i++)//��ӡÿ����Ⱥ�е���ý�
		{
			int[] x = ga.bestChomo.get(i).getChomo();
			for(int j=0;j<x.length;j++)
			{
				System.out.print(x[j]+" ");
			}
			System.out.println();
		}*/
		
		//�������Ե�
		/*Chomo chomo1 = new Chomo();
		Chomo chomo2 = new Chomo();
		int[] c1 = {1,2,4,3,7,6,5,9,8};
		int[] c2 = {1,3,4,6,7,2,5,8,9};
		chomo1.setChomo(c1);
		chomo2.setChomo(c2);
		Chomo chomo3 = ga.cross(chomo1, chomo2);
		ga.variation(chomo3);*/
	}
	
	public Chomo getChomo() //����������еõ�һ��Ⱦɫ�壬��Ϊû�г������ƣ�����û����֤Ⱦɫ��
	{
		int[] sequence = new int[length];
		Chomo chomo = new Chomo();
		for(int i = 0; i < length; i++){ 
		sequence[i] = i+1;
		}
		Random random = new Random();
		for(int i = 0; i < length; i++)
		{ //�����������
			int p = random.nextInt(length);
			int tmp = sequence[i];
			sequence[i] = sequence[p];
			sequence[p] = tmp;
		}
		random = null;
		chomo.setChomo(sequence);
		chomo.setFit(fitness(chomo));//����Ӧ��
		return chomo;
	}
	
	
	public ArrayList<Chomo> getInitPop() //��ʼ���õ�һ����Ⱥ
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
	 //ѡ������,������Ӧ�Ⱥ���ѡ�������뽻��أ�����ѡ��
	public ArrayList<Chomo> select(ArrayList<Chomo> pop)
	{
		double[] p = new double[pop.size()]; //���ʺ���
		double[] fit = new double[pop.size()];
		
		ArrayList<Chomo> crossPool = new ArrayList<Chomo>();
		quickSort(pop, 0, pop.size()-1); //��������
		ArrayList<Chomo> popSort = pop;
		if(bestChomo.size() == 0)
			bestChomo.add(popSort.get(popSort.size()-1)); //ÿһ����õ�Ⱦɫ�嶼������
		else
			if(bestChomo.get(bestChomo.size()-1).getFit()<=popSort.get(popSort.size()-1).getFit())
				bestChomo.add(popSort.get(popSort.size()-1));
		double fitSum = 0;
		for(int i=0;i<pop.size();i++)
		{
			fitSum+= popSort.get(i).getFit();
			fit[i] = popSort.get(i).getFit();
		}
		for(int i=0;i<fit.length;i++) ////ͨ��fit����˥������
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
		for(int i=0;i<popSize;i++) //���²���popSize���������Ⱥ
		{
			double rand = new Random().nextDouble();
			for(int m=0;m<p.length;m++) //���ݸ��ʷֲ������Ӧ�ĸ���
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
	
	public ArrayList<Chomo> crossAndVar(ArrayList<Chomo> pop) //����������
	{
		 ArrayList<Chomo> newPop1 = new  ArrayList<Chomo>();
		 for(int i=0;i<pop.size()/2;i++) //����ż����������������Ϊż��,����
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
//		 for(int i=0;i<pop.size();i++) //�������һ��������һ����ϣ��������ײ����ֲ�����
//		 {
//			 newPop1.add(pop.get(i));
//		 }
//		 quickSort(newPop1, 0, newPop1.size()-1); //��������
//		 pop = new ArrayList<Chomo>();
//		 for(int i=newPop1.size()-1;i>newPop1.size()/2;i--)
//		 {
//			 pop.add(newPop1.get(i));
//		 }
		 pop = newPop1;
		 ArrayList<Chomo> newPop2 = new  ArrayList<Chomo>();
		 for(int i=0;i<pop.size();i++) //����
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
	
	public Chomo cross(Chomo chomo1,Chomo chomo2) //�������ӣ�˳�򽻲�
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
		for(int i=rand1;i<=rand2;i++)//����Ⱦɫ��1��������
		{
			code1[i] = chomo1.getChomo()[i];
		}
		for(int j=0;j<chomo2.getChomo().length;j++)
		{
			code2[j] = 0;
			for(int i=rand1;i<=rand2;i++)
			{
				if(chomo2.getChomo()[j] == code1[i]) //��Ⱦɫ��2�н�Ⱦɫ��1��������Ԫ������Ϊ-1
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
		chomo.setFit(fitness(chomo));//����Ӧ��
		return chomo;
	}
	public Chomo variation(Chomo chomo) //�������ӣ�2-��������
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
		chomo2.setFit(fitness(chomo2));//����Ӧ��
		return chomo2;
	}
	
	//���fitness�����ŵ㲻����Ӧ�ý�ʱ��������Ϊ�ͷ�����룬��������ΪӲ������������������
	public double  fitness(Chomo cho) //������Ӧ��
	{
		int[] chomo = cho.chomo;
		double fit = 0; //��Ӧ��
		int carNum =0; //������Ŀ
		int num = 0; //ÿ�������ۼƼ�����ÿ����������
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
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i-1]));//��·���������ص�����
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i]));//����һ����·����ʼ
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
					fit+=dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]));//��·�����붼���
					eachFit+= dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]))+Math.round(3*Math.sqrt(dataMap.get(chomo[i]).goods_num)+5);
					path.path.add(chomo[i]);
					if(i!=chomo.length-1 && eachFit+dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1])) > 720)
						eachFit+= dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1]));
				}
				else
				{
					fit+= dis(dataMap.get(0),dataMap.get(chomo[i])); //��ʼ���
					eachFit+= dis(dataMap.get(0),dataMap.get(chomo[i]))+Math.round(3*Math.sqrt(dataMap.get(chomo[i]).goods_num)+5);
					path.path.add(chomo[i]);
				}
			}
		}
		cho.path.add(path);
		fit+= dis(dataMap.get(0),dataMap.get(chomo[chomo.length-1]));
		//������û���������٣�����ֱ���ڳ�����Ŀǰ����һ��Ȩ��
//		fit+=+carNum; //��ʱ����ʱ�䣬�Լ��涨������Ŀ�����˺���Ҫ�ı�fitness����
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
//			System.out.println("Path"+i+"·����Ϊ: "+time);
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
//		System.out.println("�ܹ�Ҫͣ��ʱ�䣺"+time);
		int arriveTime = 0;
//		System.out.println("paths.size: "+paths.size());
		ArrayList<Line> lineList = new ArrayList<Line>();
		lines.put(dataMap.get(0).pointName, lineList); //һ�����������·��
		for(int i=0;i<paths.size();i++)
		{
			int firstTime = 0;
			int endTime = 0;
			Line line = new Line(); //һ��·��
			Record record = new Record(); //·���е�һ����¼
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
//					System.out.println("��"+p.path.get(j)+"��ʱ�䣺"+arriveTime+" ");
//					printRecord(dataMap.get(p.path.get(j)).pointName,
//							arriveTime,
//							(int)(arriveTime+3*Math.sqrt(dataMap.get(p.path.get(j)).goods_num)+5),
//									"-",dataMap.get(p.path.get(j)).goods_num);
					
					if(j == p.path.size()-1) //·����ֻ��һ����
					{
						arriveTime+= dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.endToStart = dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.spot_id = dataMap.get(p.path.get(j)).pointName;
						endTime = arriveTime;
						line.time = arriveTime; //ÿ����·���ܿ���
//						System.out.println("��"+p.path.get(j)+"��ʱ�䣺"+arriveTime+" ");
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
//					System.out.println("��"+p.path.get(j)+"��ʱ�䣺"+arriveTime+" ");
					if(j == p.path.size()-1)
					{
						line.endToStart = dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.spot_id = dataMap.get(p.path.get(j)).pointName;
						arriveTime+= dis(dataMap.get(0),dataMap.get(p.path.get(j)));
						line.time = arriveTime; //ÿ����·���ܿ���
						endTime = arriveTime;
//						System.out.println("��"+p.path.get(j)+"��ʱ�䣺"+arriveTime+" ");
					}
				}
			}
			
			line.time = endTime - firstTime;
			lineList.add(line);
//			System.out.println();
		}
		allTime+= arriveTime;
//		System.out.println("��Ҫ��ʱ��Ϊ��"+arriveTime+"/720 = "+(1+arriveTime/720)+"�����Ա");
//		System.out.println(dis(dataMap.get(63),dataMap.get(63)));
		return (1+arriveTime/720);
	}
	//���Ըĳ����ļ���׷������
	public void printRecord(String addr,int arriveTime,int departureTime,String amount,int num)
	{
		System.out.println(addr+" "+arriveTime+" "+departureTime+" "+amount+num);
	}
	
	
	public int dis(Point pointA,Point pointB) //����֮��ľ���
	{
		int cost = 0;
		cost = (int) Math.round(2*6378137*Math.asin(Math.sqrt(Math.pow(Math.sin(Math.PI/180.0*(pointA.getLan()-pointB.getLan())/2),2)+
				Math.cos(Math.PI/180.0*pointA.getLan())*Math.cos(Math.PI/180.0*pointB.getLan())*
				Math.pow(Math.sin(Math.PI/180.0*(pointA.getLon()-pointB.getLon())/2),2)))/250);
		
		return cost;
	}
	//���ƣ������ҵ�ÿһ��������fitness�ĸ���
	public void quickSort(ArrayList<Chomo> pop,int s,int e)
	{
		if(s < e)
		{
			int m = partition(pop, s, e);
			quickSort(pop,s,m-1);
			quickSort(pop, m+1, e);
		}
	}
	
	public int partition(ArrayList<Chomo> pop,int s,int e) //�Ե�һ��Ԫ����Ϊ�ָ�Ԫ��
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
		int i = 0; //�����idΪ0
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
				point.setPointId(i); //��Ϊÿ�����͵�ֻ��һ������������ֱ��ӳ��id
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
		length = i-1; //Ⱦɫ�峤��
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
		int i = 0; //�����idΪ0
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
				point.setPointId(i); //��Ϊÿ�����͵�ֻ��һ������������ֱ��ӳ��id
				
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
	
	public boolean validChomo(Chomo chomo) //��֤Ⱦɫ���Ƿ�ﵽҪ��
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
	
	public void normalization(ArrayList<Chomo> pop) //��һ��
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
	

	
	
	/*public void  printAllocation(int[] chomo) //������Ӧ��
	{
		int fit = 0; //��Ӧ��
		int carNum =1; //������Ŀ
		int num = 0; //ÿ�������ۼƼ�����ÿ����������
		int sum = 0;
		int eachFit = 0;
		for(int i=0;i<chomo.length;i++)
		{
			num+=dataMap.get(chomo[i]).getGoods_num();
			if(num>140 || eachFit > 720)
			{
				
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i-1]));//��·���������ص�����
				fit+= dis(dataMap.get(0),dataMap.get(chomo[i]));//����һ����·����ʼ
				sum+= num-dataMap.get(chomo[i]).getGoods_num();
				System.out.println("����"+carNum+"���أ� "+(num-dataMap.get(chomo[i]).getGoods_num()));
				System.out.println();
				num = dataMap.get(chomo[i]).getGoods_num();
				carNum++;
				eachFit = dis(dataMap.get(0),dataMap.get(chomo[i]));
//				System.out.print(chomo[i]+" ");
				System.out.println("��"+chomo[i]+"��ʱ�䣺"+fit+" ");
			}
			else
			{
				if(i-1>=0)
				{
					fit+=dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]));//��·�����붼���
//					System.out.print(chomo[i]+" ");
					eachFit+= dis(dataMap.get(chomo[i-1]),dataMap.get(chomo[i]));
					if(i!=chomo.length-1 && eachFit+dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1])) > 720)
						eachFit+= dis(dataMap.get(chomo[i]),dataMap.get(chomo[i+1]));
					System.out.println("��"+chomo[i]+"��ʱ�䣺"+fit+" ");
				}
				else
				{
					fit+= dis(dataMap.get(0),dataMap.get(chomo[i])); //��ʼ���
//					System.out.print(chomo[i]+" ");
					eachFit+= dis(dataMap.get(0),dataMap.get(chomo[i]));
					System.out.println("��"+chomo[i]+"��ʱ�䣺"+fit+" ");
				}
			}
		}
		sum+= num;
		System.out.println("����"+carNum+"���أ� "+num);
		System.out.println("���������� "+carNum);
		//�ټ���ÿ�����ͣ��ʱ�䣬ͣ��ʱ�����޷�����ģ�������͵�Ļ�������й�
		//����ͣ��ʱ��󣬾Ϳ���Ϊÿ�������ŷ����������
		//������֤һ�£������ʱ������Ƿ�׼ȷ
		fit+= dis(dataMap.get(0),dataMap.get(chomo[chomo.length-1]));
		System.out.println("�����һ�����Ļ����������ģ� "+fit); 
		System.out.println("sum: "+sum);
	}
	*/
	
	/*public int printResult(int[] chomo)
	{
		ArrayList<Path> paths = new ArrayList<Path>();
		Path path = new Path();
		
//		int carNum =1; //������Ŀ
		int num = 0; //ÿ�������ۼƼ�����ÿ����������
		for(int i=0;i<chomo.length;i++)
		{
			num+=dataMap.get(chomo[i]).getGoods_num();
			if(num>140)
			{
				paths.add(path);
				path = new Path();
				path.path.add(chomo[i]);
//				System.out.println("����"+carNum+"���أ� "+(num-dataMap.get(chomo[i]).getGoods_num()));
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
