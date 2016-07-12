package com.mirdar.taxi1;

import java.util.ArrayList;

public class Dijkstra {

	//根据subVector的顺序重新将Vector进行编号保存在node中，然后进行Dijkstra算法
	public double[] dijkstra(ArrayList<Vector> subVector,ArrayList<RouteSection> subRouteSections,
								Vector start)
	{
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
//		System.out.println("matrixsize: "+matrix.length);
//		int k = 0;
//		for(int i=0;i<matrix.length;i++)
//			for(int j=0;j<matrix.length;j++)
//			{
//				if(matrix[i][j] == 0)
//					k++;
//			}
//		System.out.println("k-----: "+k);
//		System.out.println("nodes.lenght1: "+nodes.length);
		nodes = am.getNodes(nodes,matrix);
//		System.out.println("nodes.lenght2: "+nodes.length);
//		System.out.println("nodes[0]: "+nodes[0].nodeID+" vector: "+nodes[0].vector.vectorID
//				+"ArrayList<Vector>.size(): "+nodes[0].vectorList.size());
		
		
		
		
		
		//d用来保存最后的距离
		double[] d = new double[matrix.length];
		//d1用来标记已经访问过的节点
		double[] d1 = new double[matrix.length];
		int s = 0;
		int flag = nodes.length - 1;
		for(int i=0;i<nodes.length;i++)
		{
			if(start.vectorID == nodes[i].vector.vectorID)
			{
//				queue.add(nodes[i]);
				s = i;
			}
		}
		//s到其他点的距离
		for(int i=0;i<matrix.length;i++)
		{
			d[i] = matrix[s][i];
			d1[i] = matrix[s][i];
		}
		nodes[s].flag = true;
		while(flag > 0)
		{
			double min = 10000;
			int t = 0;
			//检查一下是否每个节点只遍历了一次
			//用d1来记录寻找到的距离S源点最近的节点
			for(int i=0;i<d.length;i++)
			{
				if( i == s)
					i++;
				if(d1[i] < min)
				{
					min = d1[i];
					t = i;
				}
			}
			d1[t] = 10000;
			nodes[t].flag = true;
			//降距过程对已经遍历的点又降距了，用一个列表保存遍历过的点，降距过程中进行检查
			ArrayList<Vector> vectors = nodes[t].vectorList;
			//降距操作
			for(int j=0;j<vectors.size();j++)
			{
				for(int i=0;i<nodes.length;i++)
				{
					if(vectors.get(j).vectorID == nodes[i].vector.vectorID)
					{
						if(!nodes[i].flag)
							if(d[i] > d[t] + matrix[t][i])
							{
								d[i] = d[t] + matrix[t][i];
								d1[i] = d[i];
							}
					}
				}
			}
			flag--;
		}
		return d;
	}
	//构造单源单点的最短路径
	
	
	
	
	
	
	
	
	
	
	
	
}
