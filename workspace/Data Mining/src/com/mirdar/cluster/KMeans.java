package com.mirdar.cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.naming.RefAddr;
import javax.security.auth.kerberos.KerberosKey;

import org.apache.poi.ss.usermodel.PrintCellComments;

/*
 * ��k�����ĵ㶼���ڱ仯ʱ��ֹͣ����
 */

public class KMeans {

	public static void main(String[] args)
	{
		String filename = "C:/Users/zpp/Desktop/datamining/classAndcluster/clusterdata.txt";
		double[] center1 = {18,45};
		double[] center2 = {15,40};
		double[] center3 = {1000,30};
		double[] center_3 = new double[2];
		
		System.out.println("Kmeans���ࣺ");
		KMeans kmeans = new KMeans();
		ArrayList<Point> points = kmeans.readData(filename);
		kmeans.firstIterator(points, center1);
		System.out.println("����������ģ�");
		kmeans.kmeans(points,center1);
		System.out.println();
		kmeans.firstIterator(points, center2);
		System.out.println("����������ģ�");
		kmeans.kmeans(points,center2);
		System.out.println();
		kmeans.firstIterator(points, center3);
		System.out.println("����������ģ�");
		center_3 = kmeans.kmeans(points,center3);
		kmeans.firstIterator(points, center_3);
//		kmeans.kmeansByMinSSE(points,center1);
	}
	/*//���޸ģ��õ���С��
	public double[] kmeansByMinSSE(ArrayList<Point> points, double[] center)
	{
		double[] pointCenter = new double[2];
		
		int k1=0,k2=0;
		for(int i=0;i<points.size();i++)
		{
			if(dis(points.get(i).getX(),center[0]) < dis(points.get(i).getX(),center[1]))
			{
				pointCenter[0]+=points.get(i).getX();
				k1++;
			}
			else
			{
				pointCenter[1]+=points.get(i).getX();
				k2++;
			}
		}
		if(k1 == 0)
			System.out.println("cluster1 û�г�Ա");
		else 
		{
			System.out.println("cluster1's center "+pointCenter[0]/k1);
			pointCenter[0] = pointCenter[0]/k1;
		}
		if(k2 == 0)
			System.out.println("cluster2 û�г�Ա");
		else 
		{
			System.out.println("cluster2's center "+pointCenter[1]/k2);
			pointCenter[1] = pointCenter[1]/k2;
		}
		
		if((sse(points,pointCenter[0])+sse(points,pointCenter[1]))<=
					(sse(points,center[0])+sse(points,center[1])))
			kmeans(points,pointCenter);
		else 
			return pointCenter;
		
		return pointCenter;
	}*/
	
	public double[] kmeans(ArrayList<Point> points, double[] center)
	{
		double[] pointCenter = new double[2];
		int k1=0,k2=0;
		for(int i=0;i<points.size();i++)
		{
			if(dis(points.get(i).getX(),center[0]) < dis(points.get(i).getX(),center[1]))
			{
				pointCenter[0]+=points.get(i).getX();
				k1++;
			}
			else
			{
				pointCenter[1]+=points.get(i).getX();
				k2++;
			}
		}
		if(k1 == 0)
			System.out.println("cluster1 û�г�Ա");
		else 
		{
			System.out.println("cluster1's center "+pointCenter[0]/k1);
			pointCenter[0] = pointCenter[0]/k1;
		}
		if(k2 == 0)
			System.out.println("cluster2 û�г�Ա");
		else 
		{
			System.out.println("cluster2's center "+pointCenter[1]/k2);
			pointCenter[1] = pointCenter[1]/k2;
		}
		
//		firstIterator(points, pointCenter); //�鿴ÿһ�ε���
		if(pointCenter[0]-center[0]==0 && pointCenter[1]-center[1]==0)
			return pointCenter;
		else if(pointCenter[0]-center[0]!=0 || pointCenter[1]-center[1]!=0)
			pointCenter = kmeans(points,pointCenter);
		
		return pointCenter;
	}
	//��һ�ε�������һ��Ĵ�
	public void firstIterator(ArrayList<Point> points, double[] center)
	{
		ArrayList<Point> points1 = new ArrayList<Point>();
		ArrayList<Point> points2 = new ArrayList<Point>();
		double SSE1 = 0;
		double SSE2 = 0;
		for(int i=0;i<points.size();i++)
		{
			if(dis(points.get(i).getX(),center[0]) < dis(points.get(i).getX(),center[1]))
				points1.add(points.get(i));
			else
				points2.add(points.get(i));
		}
		SSE1 = sse(points1,center[0]);
		SSE2 = sse(points2,center[1]);
		
		System.out.println("����Ϊ��"+center[0]+"����SSEΪ�� "+SSE1);
		printClust(points1, center[0]);
		System.out.println();
		System.out.println("����Ϊ��"+center[1]+"����SSEΪ�� "+SSE2);
		printClust(points2, center[1]);
		System.out.println();
		System.out.println("�ܵ�SSEΪ��"+(SSE1+SSE2));
	}
	
	public void printClust(ArrayList<Point> points,double center)
	{
		System.out.print("����: "+center + "   cluster��Ա�� ");
		for(int i=0;i<points.size();i++)
		{
			System.out.print(points.get(i).getX() + "  ");
		}
	}
	
	public double sse(ArrayList<Point> points,double center)
	{
		double SSE = 0;
		
		for(int i=0;i<points.size();i++)
			SSE+=Math.pow((points.get(i).getX()-center), 2);
		return SSE;
	}
	
	public double dis(double a,double b)
	{
		return Math.abs(a-b);
	}
	
	public ArrayList<Point> readData(String filename)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		File file = new File(filename);
		BufferedReader reader = null;
		int i = 0;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Point point = new Point();
				point.setX(Integer.parseInt(strings[0]));
				point.pointID = i;
				i++;
				points.add(point);
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
		
		return points;
	}
}
