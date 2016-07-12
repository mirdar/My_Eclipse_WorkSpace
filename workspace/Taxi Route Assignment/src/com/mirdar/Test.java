package com.mirdar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mirdar.directNNfuction.NNFuction;
import com.mirdar.generator.RequestGenerator;
import com.mirdar.refinement.Cust;
import com.mirdar.refinement.RefinementOperation;
import com.mirdar.refinement.Taxi;
import com.mirdar.sort.QuickSort;
import com.mirdar.ssa.Graph;
import com.mirdar.violent.ViolentFuction;

public class Test {

	public static void main(String[] args) throws IOException
	{
		//���ļ��ж�ȡȫ������
				String filePath = "F:/";
				ReadData readData = new ReadData();
				ArrayList<Vector> vectors = readData.readVector(
						filePath + "Nodes.txt");
				ArrayList<RouteSection> routeSections = readData.readSection(
						filePath + "Edges.txt", vectors);
				
				System.out.println("vectorSize: "+vectors.size());
				System.out.println("routeSectionSize: "+routeSections.size());
//				print(routeSections,9);
				
				////��� 4000<= x <= 6000 , 2000<= y <= 4000 ��������·��
				SubArea subArea = new SubArea();
//				ArrayList<RouteSection> subRouteSections = subArea.getSubArea(routeSections,6000,7000,6000,8000);
//				ArrayList<RouteSection> subRouteSections = subArea.getSubArea(routeSections,4000,6000,2000,6000);
//				System.out.println("subRouteSections.size: "+subRouteSections.size());
				//�ɽ�ɽ���е�·����
				long startTime = System.currentTimeMillis();
				ArrayList<RouteSection> subRouteSections1 = subArea.getSubArea(routeSections,3750,4700,1700,4500);
				long endTime = System.currentTimeMillis();
				System.out.println("test.time in refinement: " + (endTime - startTime)
						+ "ms****************");
				ArrayList<RouteSection> subRouteSections2 = subArea.getSubArea(routeSections,3300,3750,1700,3000);
				ArrayList<RouteSection> subRouteSections = new ArrayList<RouteSection>();
				for(int i=0;i<subRouteSections1.size();i++)
					subRouteSections.add(subRouteSections1.get(i));
				for(int i=0;i<subRouteSections2.size();i++)
					subRouteSections.add(subRouteSections2.get(i));
				Map<String,Vector> vectorMap = new HashMap<String,Vector>();
				System.out.println("subRouteSections1.size: "+subRouteSections1.size());
				System.out.println("subRouteSections2.size: "+subRouteSections2.size());
				System.out.println("subRouteSectionsList.size: "+subRouteSections.size());
				String[] vectorName = new String[19905];
//				String[] vectorName = new String[32706];
//				String[] vectorName = new String[57692];
				int j = 0;
				Graph g = new Graph();
				for(int i=0;i<subRouteSections.size();i++)
				{
					g.addEdge(subRouteSections.get(i).I1.vectorID, subRouteSections.get(i).I2.vectorID,
							subRouteSections.get(i).length);
					if(vectorMap.get(subRouteSections.get(i).I1.vectorID) == null)
					{
						vectorName[j] = subRouteSections.get(i).I1.vectorID;
						j++;
						vectorMap.put(subRouteSections.get(i).I1.vectorID, subRouteSections.get(i).I1);
					}
					if(vectorMap.get(subRouteSections.get(i).I2.vectorID) == null)
					{
						vectorName[j] = subRouteSections.get(i).I2.vectorID;
						j++;
						vectorMap.put(subRouteSections.get(i).I2.vectorID, subRouteSections.get(i).I2);
					}
				}	
				System.out.println("the numble of vertex in Graph is "+ g.vertexMap.size());
				System.out.println("vectorMap.size: "+ vectorMap.size());
				System.out.println("vectorName.size: "+ j);
				System.out.println("vectorName[0]: "+vectorName[0]);
				System.out.println("vectorName[1]: "+vectorName[1]);
				System.out.println("ŷʽ���룺 "+Distance.distance2(vectorMap.get(vectorName[0]), vectorMap.get(vectorName[1])));
				g.dijkstra(vectorName[0]);
				g.printPath(vectorName[1]);
				int flag = 0;
				//�������˲���ͨ�������õ���vectorMap�е�Vector�����໥��ͨ��
				for(int i=0;i<vectorName.length;i++)
				{
					if(g.cleanVertex(vectorName[i]).dist >10000)
					{
						flag++;
						vectorMap.remove(vectorName[i]);
					}
				}
				System.out.println("flag: "+flag);
				System.out.println("vectorMap: "+vectorMap.size());
				System.out.println(((Vector)vectorMap.values().toArray()[0]).vectorID);
				/*System.out.println("subRouteSections.size: "+subRouteSections.size());
				String[] vectorName = new String[32706];
				int j = 0;
				Graph g = new Graph();
				for(int i=0;i<subRouteSections.size();i++)
				{
					g.addEdge(subRouteSections.get(i).I1.vectorID, subRouteSections.get(i).I2.vectorID,
							subRouteSections.get(i).length);
					if(vectorMap.get(subRouteSections.get(i).I1.vectorID) == null)
					{
						vectorName[j] = subRouteSections.get(i).I1.vectorID;
						j++;
						vectorMap.put(subRouteSections.get(i).I1.vectorID, subRouteSections.get(i).I1);
					}
					if(vectorMap.get(subRouteSections.get(i).I2.vectorID) == null)
					{
						vectorName[j] = subRouteSections.get(i).I2.vectorID;
						j++;
						vectorMap.put(subRouteSections.get(i).I2.vectorID, subRouteSections.get(i).I2);
					}
				}	
				System.out.println("the numble of vertex in Graph is "+ g.vertexMap.size());
				System.out.println("vectorMap.size: "+ vectorMap.size());
				System.out.println("vectorName[0]: "+vectorName[0]);
				System.out.println("ŷʽ���룺 "+Distance.distance2(vectorMap.get(vectorName[0]), vectorMap.get(vectorName[1000])));
				g.dijkstra(vectorName[0]);
				g.printPath(vectorName[1000]);*/
				
				//����ϸ������
				RefinementOperation ro = new RefinementOperation();
				/*ArrayList<Taxi> taxis = new ArrayList<Taxi>();
				for(int i=0;i<2;i++)
				{
					Taxi taxi = new Taxi(i,null,1,2);
					taxi.Balance = 22-i;
					taxis.add(taxi);
				}
				System.out.println(taxis.get(1).Balance);
				ro.quickSort(taxis, 0, 1);
				System.out.println(taxis.get(1).Balance);*/
				
				//����request������
//				File file = new File("F:/route assignment/"+"outputData.txt");
//				if(file.exists())
//				{
//				System.out.println("�ļ��Ѵ��ڣ���ɾ��������");
//				System.exit(0);
//				}
//				file.createNewFile();
//				BufferedWriter bw = new BufferedWriter(new FileWriter(file,true)); 
				int n=500;
				int m=50;
				RequestGenerator rg = new RequestGenerator();
				ArrayList<TaxiRequest> taxiSet = rg.taxiGenerator(n);
//				ArrayList<TaxiRequest> taxiReq = rg.taxiRequestGenerator(vectorMap, taxiSet, 1000);
				ArrayList<CustRequest> custSet = rg.custGenerator(m);
//				ArrayList<CustRequest> custReq = rg.custRequestGenerator(vectorMap, custSet,100,1);
//				System.out.println("taxi.size: "+taxiReq.size()+"--- cust.size: "+custReq.size());
//				System.out.println("custReq[0]: "+custReq.get(0).custID+" vID: "+custReq.get(0).s.vectorID);
//				ArrayList<Cust> custs = ro.refinementOperation(taxiReq, custReq, subRouteSectionList);
//				System.out.println("cust.size: "+custs.size());
//				printCust(custs.get(99));
				
				HSSFWorkbook wb = new HSSFWorkbook();
				//�����µ�sheet����excel�ı���
				HSSFSheet sheet=wb.createSheet("experiment");
				//��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
				HSSFRow row1=sheet.createRow(0);
				//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
//				HSSFCell cell=row1.createCell(0);
					row1.createCell(0).setCellValue("�������");
					row1.createCell(1).setCellValue("�������");
					row1.createCell(2).setCellValue("��׼��");
					row1.createCell(3).setCellValue("�ȴ�ʱ���ܺ�(min)");
					row1.createCell(4).setCellValue("ƽ��ÿ��cust�ȴ�ʱ�䣨min��");
					row1.createCell(5).setCellValue("����ʱ���ܺ�(ms)");
					row1.createCell(6).setCellValue("ƽ��ÿ��cust����������ʱ�䣨ms��");
					row1.createCell(7).setCellValue("ÿ�η�������ʱ��");
				int[] va = {1,10,50,100,200,500}; 
				int xsl = 1;
			for(int v=0;v<va.length;v++)
			{
				for(int i=0;i<taxiSet.size();i++)
					taxiSet.get(i).clear();
				double t1 = 0;
				double t2 = 0;
				double t3 = 0;
				int seedLen = va[v];
				int[] seed = new int[seedLen];
				int[] seed2 = new int[seedLen];
				for(int i=0;i<seed.length;i++)
				{
					seed[i] = i;
					seed2[i] = seed.length + i;
				}
				NNFuction nnFuction = new NNFuction();
				ViolentFuction violentFunction = new ViolentFuction();
				long runTime1 = 0;
				long runTime2 = 0;
				long runTime3 = 0;
				QuickSort sort = new QuickSort();
				ArrayList<Taxi> taxis1 = new ArrayList<Taxi>();
				ArrayList<Taxi> taxis2 = new ArrayList<Taxi>();
				//����100��
				for(int i=0;i<seed.length;i++)
				{
					ArrayList<TaxiRequest> taxiSet2 = taxiSet;
					ArrayList<CustRequest> custSet2 = custSet;
					ArrayList<TaxiRequest> taxiReq = rg.taxiRequestGenerator(vectorMap, taxiSet2, n,seed[i]);
					ArrayList<CustRequest> custReq = rg.custRequestGenerator(vectorMap, custSet2,m,seed2[i]);
					
					long startTime2 = System.currentTimeMillis();
					ArrayList<Cust> custs2 = nnFuction.recomNN(taxiReq, custReq, subRouteSections);
					long endTime2 = System.currentTimeMillis();
					runTime2 = runTime2 + endTime2-startTime2;
					
					sort.quickSort(custReq, 0, custReq.size()-1);
					long startTime1 = System.currentTimeMillis();
					ArrayList<Cust> custs = ro.refinementOperation(taxiReq, custReq, subRouteSections);
					long endTime1 = System.currentTimeMillis();
					runTime1 = runTime1 + endTime1-startTime1;
						
					long startTime3 = System.currentTimeMillis();
					ArrayList<Cust> custs3 = violentFunction.violentSearch(taxiReq, custReq, subRouteSections);
					long endTime3 = System.currentTimeMillis();
					runTime3 = runTime3 + endTime3-startTime3;
					
					for(int j1=0;j1<custs.size();j1++)
					{
						taxiSet.get(custs.get(j1).nnTaxi.index).profit = 
								taxiSet.get(custs.get(j1).nnTaxi.index).profit + custs.get(j1).profit;
						taxiSet.get(custs2.get(j1).nnTaxi.index).profitDirect = 
								taxiSet.get(custs2.get(j1).nnTaxi.index).profitDirect + custs2.get(j1).profit;
						taxiSet.get(custs3.get(j1).nnTaxi.index).profitViolent = 
								taxiSet.get(custs3.get(j1).nnTaxi.index).profitViolent + custs3.get(j1).profit;
						t1 = t1 + custs.get(j1).nnTaxi.ti;
						t2 = t2 + custs2.get(j1).t0;
						t3 = t3 + custs3.get(j1).nnTaxi.ti;
						taxis1.add(custs.get(j1).nnTaxi);
						taxis2.add(custs3.get(j1).nnTaxi);
					}
				}
				
				//�Ƚ�����ʱ���뷽�û�бȽϹ˿͵ȴ�ʱ��
				
				double average = 0;
				double averageDirect = 0;
				double averageViolent = 0;
				for(int i=0;i<taxiSet.size();i++)
				{
					average =average + taxiSet.get(i).profit;
					averageDirect =averageDirect + taxiSet.get(i).profitDirect;
					averageViolent = averageViolent + taxiSet.get(i).profitViolent;
				}
				System.out.println("totalProfit: "+average);
				System.out.println("totalDirectProfit: "+averageDirect);
				System.out.println("averageViolent: "+averageViolent);
				average = average/taxiSet.size();
				averageDirect = averageDirect/taxiSet.size();
				averageViolent = averageViolent/taxiSet.size();
				System.out.println("average: "+average);
				System.out.println("averageDirect: "+averageDirect);
				System.out.println("averageViolent: "+averageViolent);
				double var = 0;
				double varDirect = 0;
				double varViolent = 0;
				for(int i=0;i<taxiSet.size();i++)
				{
					var = var + (taxiSet.get(i).profit - average)*(taxiSet.get(i).profit - average);
					varDirect = varDirect + (taxiSet.get(i).profitDirect - averageDirect)
							*(taxiSet.get(i).profitDirect - averageDirect);
					varViolent = varViolent + (taxiSet.get(i).profitViolent - averageViolent)
							*(taxiSet.get(i).profitViolent - averageViolent);
				}
				System.out.println("var: "+var/taxiSet.size());
				System.out.println("varDirect: "+varDirect/taxiSet.size());
				System.out.println("varViolent: "+varViolent/taxiSet.size());
				System.out.println("std: "+Math.sqrt(var/taxiSet.size()));
				System.out.println("runtime1: "+runTime1+" ms");
				System.out.println("stdDirect: "+Math.sqrt(varDirect/taxiSet.size()));
				System.out.println("runtime2: "+runTime2+" ms");
				System.out.println("stdViolent: "+Math.sqrt(varViolent/taxiSet.size()));
				System.out.println("runtime3: "+runTime3+" ms");
				System.out.println("wait time t1: "+t1);
				System.out.println("wait time t2: "+t2);
				System.out.println("wait time t3: "+t3);
				
				//����refinement��violent�����Ƽ���taxi��ϸ΢�Ĳ��
				/*
				 * �����ǵ�����balance��ȵ�taxiֵ��ͬһ����ʱ���Ƽ���taxi��ͬ
				 * 
				 */
				System.out.println("taxis1.size(): "+taxis1.size());
				double flagRandV = 0;
				for(int i=0;i<taxis1.size();i++)
				{
					if(taxis1.get(i).index == taxis2.get(i).index)
					{
						flagRandV++;
					}
				}
				
				System.out.println("flagRandV: "+flagRandV);
				
				for(int i=0;i<5;i++)
				{
					if(i == 3) continue;
					System.out.println("i= "+ i);
				}
				
				
				HSSFRow row=sheet.createRow(xsl);
				//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
//				HSSFCell cell=row1.createCell(0);
					row.createCell(0).setCellValue(seedLen);
					row.createCell(1).setCellValue(1);   
					row.createCell(2).setCellValue(Math.sqrt(var/taxiSet.size()));
					row.createCell(3).setCellValue(t1);
					row.createCell(4).setCellValue(t1/(m*seedLen));
					row.createCell(5).setCellValue(runTime1);
					row.createCell(6).setCellValue(runTime1/(m*seedLen));
					row.createCell(7).setCellValue(runTime1/seedLen);
					xsl++;
				HSSFRow row2=sheet.createRow(xsl);
					//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
//					HSSFCell cell=row1.createCell(0);
					row2.createCell(0).setCellValue("");
					row2.createCell(1).setCellValue(2);  
					row2.createCell(2).setCellValue(Math.sqrt(varDirect/taxiSet.size()));
					row2.createCell(3).setCellValue(t2);
					row2.createCell(4).setCellValue(t2/(m*seedLen));
					row2.createCell(5).setCellValue(runTime2);
					row2.createCell(6).setCellValue(runTime2/(m*seedLen));
					row2.createCell(7).setCellValue(runTime2/seedLen); 
					xsl++;
				HSSFRow row3=sheet.createRow(xsl);
						//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
//						HSSFCell cell=row1.createCell(0);
					row3.createCell(0).setCellValue("");
					row3.createCell(1).setCellValue(3);  
					row3.createCell(2).setCellValue(Math.sqrt(varViolent/taxiSet.size()));
					row3.createCell(3).setCellValue(t3);
					row3.createCell(4).setCellValue(t3/(m*seedLen));
					row3.createCell(5).setCellValue(runTime3);
					row3.createCell(6).setCellValue(runTime3/(m*seedLen));
					row3.createCell(7).setCellValue(runTime3/seedLen);
					xsl++;
			}
			OutputStream output=new FileOutputStream("F:\\experiment8.xls");
		    wb.write(output);
			output.close();
			wb.close();
	}
	
	public static void printCust(Cust cust)
	{
		System.out.println("this is a cust infomation");
		System.out.println("	CustID: "+cust.custID); 
		System.out.println("	custVector: "+cust.vector.vectorID);
		System.out.println("	Cust.NNTaxi.taxiID: "+cust.nnTaxi.taxiID); 
		System.out.println("	CustProfit: "+cust.profit);
		System.out.println("	Cust.List<Taxi>: "+cust.taxiSet.size());
		System.out.println("	Cust.t0: "+cust.t0); 
		System.out.println("TaxiSet:-------------------");
		for(int i=0;i<cust.taxiSet.size();i++)
			printTaxi(cust.taxiSet.get(i));
	}
	
	public static void printTaxi(Taxi taxi)
	{
		System.out.println("this is a taxi infomation");
		System.out.println("	taxiID: "+taxi.taxiID);
		System.out.println("	taxiVector: "+taxi.vector.vectorID);
		System.out.println("	taxiProfit: "+taxi.profit); 
		System.out.println("	taxi.ti: "+taxi.ti); 
		System.out.println("	the shortest path form taxi to cust: "+taxi.Lenght);
		System.out.println("	the Balance of this taxi for this cust: "+taxi.Balance);
		
	} 
	
}
