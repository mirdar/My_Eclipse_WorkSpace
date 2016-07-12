package com.mirdar.taxi1;

import java.io.IOException;
import java.util.ArrayList;

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
//		print(routeSections,9);
		
		////��� 4000<= x <= 6000 , 2000<= y <= 4000 ��������·��
		SubArea subArea = new SubArea();
		ArrayList<RouteSection> subRouteSections = subArea.getSubArea(routeSections,4000,5000,2000,3000);
//		ArrayList<Vector> subVector = subArea.getSubAreaV(vectors, 4000,5000,2000,3000);
		System.out.println("subRouteSectionsSize: "+subRouteSections.size());
//		System.out.println("subVector: "+subVector.size());
		
//		printRouteSecton(subRouteSections,1);
		
	/*	//����taxiRequest
		RequestGenerator generator = new RequestGenerator();
		ArrayList<TaxiRequest> taxiList = generator.taxiGenerator(1000);
		ArrayList<TaxiRequest> taxiRequestList = generator.taxiRequestGenerator(subRouteSections, taxiList, 1000);
		System.out.println("Generator the numble of taxi: "+taxiList.size());
		System.out.println("Generator the numble of taxiRequest: "+taxiRequestList.size());
		System.out.println();
		printTaxi(taxiList, 0);
		printTaxi(taxiRequestList,0);
		
		//����custRequest
		ArrayList<CustRequest> custList = generator.custGenerator(100);
		ArrayList<CustRequest> custRequestList = generator.custRequestGenerator(subRouteSections, custList, 100);
		System.out.println("Generator the numble of cust: "+custList.size());
		System.out.println("Generator the numble of custRequest: "+custRequestList.size());
		System.out.println("---------------------------------------");
		System.out.println("start: ");
		printLocation(custRequestList.get(0).s);
		System.out.println("end: ");
		printLocation(custRequestList.get(0).e);
		System.out.println("the distance s and e(called profit): "+custRequestList.get(0).profit);
		
		//�����Ƿ����ҵ������taxiRequest
		System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
		GetTaxi gT = new GetTaxi();
		TaxiRequest nnTaxi = gT.getTaxi(taxiRequestList, custRequestList.get(0));
		double nnTaxiDis = gT.getNNTaxiDis(nnTaxi, custRequestList.get(0), subRouteSections, subVector);
		System.out.println("nnTaxi��cust��ŷʽ���룺 "+Distance.distance(nnTaxi.s, custRequestList.get(0).s));
		System.out.println("�ɹ��ҵ�nnTaxiDis�����·����"+nnTaxiDis);
		System.out.println("nnTaxi.vectorID: "+nnTaxi.s.I.vectorID);
		System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
		
		//�����ڽӾ���ֱ��ʹ��ȫ�����ݳ����ڴ�
		int[] a = new int[subVector.size()];
	
		Node[] nodes = new Node[subVector.size()];
		for(int i=0;i<subVector.size();i++)
		{
			a[i] = subVector.get(i).vectorID;
			nodes[i] = new Node();
			nodes[i].nodeID = i;
			nodes[i].vector = subVector.get(i);
		}
		AdjMatrix am = new AdjMatrix();
		double[][] matrix = am.getAdiMatrix(subVector, subRouteSections,a);
		System.out.println("matrixsize: "+matrix.length);
		int k = 0;
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix.length;j++)
			{
//				if(matrix[i][i] == 0)
//					k++;
				if(matrix[i][j] != 0&& matrix[i][j] != 10000)
					k++;
			}
		System.out.println("k: "+k);
		System.out.println("nodes.lenght1: "+nodes.length);
		nodes = am.getNodes(nodes,matrix);
		System.out.println("nodes.lenght2: "+nodes.length);
		System.out.println("nodes[0]: "+nodes[0].nodeID+" vector: "+nodes[0].vector.vectorID
				+"ArrayList<Vector>.size(): "+nodes[0].vectorList.size());
		
		//�����Ƿ�ɹ�����nodes
		int flag = 0;
		for(int i=0;i<nodes.length;i++)
		{
				flag = flag + nodes[i].vectorList.size();
		}
		System.out.println("flag: "+flag/2+" == "+subRouteSections.size()+" ?");
		
		//ͨ��nodes���ڽӾ���ʹ��Dijkstra�㷨
		System.out.println("*****************************************");
		Dijkstra dijkstra = new Dijkstra();
		double[] dis = dijkstra.dijkstra(subVector, subRouteSections, subVector.get(100));
		System.out.println("dis.length: "+dis.length);
		
		for(int i=2299;i<2309;i++)
		{
			double m = Distance.distance2(nodes[100].vector, nodes[i].vector);
			System.out.println();
			System.out.println("ԭʼ���룺"+matrix[100][i]);
			System.out.println("d["+i+"] = "+dis[i]);
			System.out.println("�����ŷʽ���룺 "+m);
		}
		//mm��¼ֱ�ӿɴ��·����Ŀ
		int mm = 0;
		for(int i=0;i<dis.length;i++)
		{
			if(matrix[100][i] ==  dis[i])
				mm++;
				
		}
		System.out.println("mm--------------"+mm);
		int maxDis = 0;
		//����ж��ٽڵ㲻�ɴ�,���������request��ʱ��������ɲ��ɴ�Ľڵ�
		for(int i=0;i<dis.length;i++)
		{
			if(dis[i] == 10000)
				maxDis++;
				
		}
		System.out.println("maxDis: "+ maxDis);
		System.out.println("*****************************************");
		
		//just test something

		double d = Distance.distance2(subRouteSections.get(9).I1, subRouteSections.get(9).I2);
		System.out.println((int)(d-subRouteSections.get(9).length));
		System.out.println( Math.sqrt(2*2000*2000));
	
	
	}
	
	//��ӡ·����Ϣ
	public static void printRouteSecton(ArrayList<RouteSection> routeSections, int i)
	{
		System.out.println("routeID: "+routeSections.get(i).routeSectionID);
		System.out.println("vectorI1: ("+routeSections.get(i).I1.vectorID
										+"   "+routeSections.get(i).I1.x
										+","+routeSections.get(i).I1.y+")");
		System.out.println("vectorI2: ("+routeSections.get(i).I2.vectorID
				+"   "+routeSections.get(i).I2.x
				+","+routeSections.get(i).I2.y+")");
		System.out.println("RouteSectionLenght: "+routeSections.get(i).length);
	}
	
	//��ӡ���⳵������Ϣ
	public static void printTaxi(ArrayList<TaxiRequest> taxiList, int i)
	{
		System.out.println("taxiID: "+taxiList.get(i).taxiID);
		printLocation(taxiList.get(i).s);
	}
	
	//��ӡ����λ����Ϣ
	public static void printLocation(Location location)
	{
		System.out.println("Location RouteSection infomation:"+
							" R: "+location.R.routeSectionID+
							" R.I1: "+location.R.I1.vectorID+
							" R.I2: "+location.R.I2.vectorID+
							" R.Lenght: "+location.R.length);
		System.out.println("R.I1.x: "+location.R.I1.x+" R.I1.y: "+location.R.I1.y);
		System.out.println("R.I2.x: "+location.R.I2.x+" R.I2.y: "+location.R.I2.y);
		System.out.println("Location I: "+location.I.vectorID);
		System.out.println("Location x: "+location.x);
		System.out.println("Location y: "+location.y);
	}
	*/
	
	
	
	}
}
