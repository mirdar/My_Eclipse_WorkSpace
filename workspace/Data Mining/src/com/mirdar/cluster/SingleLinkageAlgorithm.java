package com.mirdar.cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.naming.RefAddr;

/*
 * 最小距离作为簇间距离
 */
public class SingleLinkageAlgorithm {

	public static void main(String[] args)
	{
		String filename = "C:/Users/zpp/Desktop/datamining/classAndcluster/clusterdata.txt";
		System.out.println("单链产生的簇： ");
		SingleLinkageAlgorithm sl = new SingleLinkageAlgorithm();
		ArrayList<ArrayList<Point>> clusters = sl.readData(filename);
		sl.singleLinkage(clusters);
	}
	//单连接
	public void singleLinkage(ArrayList<ArrayList<Point>> clusters)
	{
		double dis = Double.MAX_VALUE;
		int[] flag = new int[2];
		for(int i=0;i<clusters.size();i++)
		{
			for(int j=i+1;j<clusters.size();j++)
			{
				if(clusterDis(clusters.get(i), clusters.get(j)) < dis)
				{
					flag[0] = i;
					flag[1] = j;
					dis = clusterDis(clusters.get(i), clusters.get(j));
				}
			}
		}
		for(int i=0;i<clusters.get(flag[1]).size();i++)
		{
			clusters.get(flag[0]).add(clusters.get(flag[1]).get(i));
		}
		clusters.remove(flag[1]);
		if(clusters.size() == 2)
		{
			System.out.println("cluster.size() = "+clusters.size());
			for(int i=0;i<clusters.size();i++)
			{
				System.out.println("cluster "+(i+1)+"为：");
				for(int j=0;j<clusters.get(i).size();j++)
				{
					System.out.print(clusters.get(i).get(j).getX()+"  ");
				}
				System.out.println();
			}
		}
		else
			singleLinkage(clusters);
	}
	//两个cluster之间的距离
	public double clusterDis(ArrayList<Point> cluster1,ArrayList<Point> cluster2)
	{
		double distance = Double.MAX_VALUE;
		for(int i=0;i<cluster1.size();i++)
		{
			for(int j=0;j<cluster2.size();j++)
			{
				double d = dis(cluster1.get(i).getX(),cluster2.get(j).getX());
				if(d<distance)
					distance = d;
			}
		}
		return distance;
	}
	
	public double dis(double a,double b)
	{
		return Math.abs(a-b);
	}
	
	public ArrayList<ArrayList<Point>> readData(String filename) //读取数据
	{
		ArrayList<ArrayList<Point>> clusters = new ArrayList<ArrayList<Point>>();
		
		File file = new File(filename);
		BufferedReader reader = null;
		int i = 0;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				ArrayList<Point> points = new ArrayList<Point>();
				String[] strings = content.split(",");
				Point point = new Point();
				point.setX(Integer.parseInt(strings[0]));
				point.pointID = i;
				i++;
				points.add(point);
				clusters.add(points);
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
		
		return clusters;
	}
}
