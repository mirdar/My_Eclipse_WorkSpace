package com.mirdar.taxi1;

import java.util.ArrayList;

//�Ѿ������ڴ���
public class AdjMatrix {

	double MAX_VALUE = 10000;
	public double[][] getAdiMatrix(ArrayList<Vector> vectors,ArrayList<RouteSection> routeSections,int[] a)
	{
		
		double[][] b = new double[vectors.size()][vectors.size()];
		
		for(int i=0;i<vectors.size();i++)
			for(int j=0;j<vectors.size();j++)
				b[i][j] = 0;
		
		//����vectors��routeList�����ڽӾ���
		//�ж��ر�
		for(int j=0;j<routeSections.size();j++)
		{
			int k1 = -1,k2 = -1;
			for(int i=0;i<vectors.size();i++)
			{
				if(routeSections.get(j).I1.vectorID == a[i])
					k1 = i;
				if(routeSections.get(j).I2.vectorID == a[i])
					k2 = i;
			}
			if(k1 !=-1&&k2 !=-1)
			{
//				if(b[k1][k2] > 0)
//					System.out.println("���ر�");
				if(b[k1][k2] == 0)
				{
					b[k1][k2] = routeSections.get(j).length;
					b[k2][k1] = routeSections.get(j).length;
				}
				if(b[k1][k2] > routeSections.get(j).length)
				{
					b[k1][k2] = routeSections.get(j).length;
					b[k2][k1] = routeSections.get(j).length;
				}
			}
		}
		for(int i=0;i<vectors.size();i++)
			for(int j=i+1;j<vectors.size();j++)
			{
				if(b[i][j] == 0)
				{
					b[i][j] = MAX_VALUE;
					b[j][i] = MAX_VALUE;
				}
			}
		
		return b;
	}
	
	//��ÿ��Vector���ҵ����ڽӵ�vector��Ȼ����Խ��еݹ������·��
	public Node[] getNodes(Node[] nodes,double[][] matrix)
	{
		Node[] node = nodes;
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix.length;j++)
			{
				if(matrix[i][j] > 0&&matrix[i][j] < 10000)
				{
					node[i].vectorList.add(node[j].vector);
				}
			}
		
//		for(int j=0;j<routeSections.size();j++)
//		{
//			for(int i=0;i<node.length;i++)
//			{
//				if(routeSections.get(j).I1.vectorID == node[i].vector.vectorID)
//				{
//					node[i].vectorList.add(routeSections.get(j).I2);
//				}
//					
//				if(routeSections.get(j).I2.vectorID == node[i].vector.vectorID)
//				{
//					node[i].vectorList.add(routeSections.get(j).I1);
//				}
//			}
//		}
		
		//����Ƿ��ж��ر�
		/*int flag = 0;
		for(int i=0;i<node.length;i++)
		{
			ArrayList<Vector> vectors = node[i].vectorList;
			for(int j=0;j<vectors.size();j++)
				for(int k=j+1;k<vectors.size();k++)
					if(vectors.get(j).vectorID == vectors.get(k).vectorID)
						flag++;
		}*/
//		System.out.println("�ж��رߣ� "+flag);
		return node;
	}
	
	
}
