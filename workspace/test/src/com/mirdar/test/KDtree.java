package com.mirdar.test;

import java.util.ArrayList;

public class KDtree {

	Vector root;
	
	public KDtree(ArrayList<Vector> vectors,int flag)
	{
//		root = new Vector();
		System.out.println("Constructor: " + vectors.size());
		root = getKDtree(vectors,flag);
		
	}
	
	public Vector getKDtree(ArrayList<Vector> vectors,int flag)
	{
		Vector v;
		if(vectors.size() == 0)
		{ 
	          return null;
	    }
		System.out.println("getKDtrees: " + vectors.size());
		if(vectors.size() == 1)
		{ 
	          v = vectors.get(0);
	          v.isLeaf = true;
	          System.out.println();
	          return v;
	    }
		
		ArrayList<Vector> leftSet = new ArrayList<Vector>();
		ArrayList<Vector> rightSet = new ArrayList<Vector>();
		
		if(flag == 0)
		{
			System.out.println("     i am x");
			v = getMedian(vectors,flag);
			v.isLeaf = false;
			System.out.println(v.getX());
			vectors.remove(v);

			for(int i=0;i< vectors.size();i++)
			{
				if(vectors.get(i).getX() <= v.getX())
				{
					leftSet.add(vectors.get(i));
				}
				else
				{
					rightSet.add(vectors.get(i));
				}
			}
			
			
			
			v.setLeftVector(getKDtree(leftSet,1));
			v.setRightVector(getKDtree(rightSet,1));
			System.out.println("X leftVector.size(): " + leftSet.size());
			System.out.println("X rightVector.size(): " + rightSet.size());
			return v;
			
			/*
		 	Vector leftVector = new Vector();
			Vector rightVector = new Vector();
			v.setLeftVector(leftVector);
			v.setRightVector(rightVector);
			System.out.println("X leftVector.size(): " + leftSet.size());
			System.out.println("X rightVector.size(): " + rightSet.size());
			getKDtree(leftVector, leftSet,1);
			getKDtree(rightVector, rightSet,1);*/
			
		}
		 
		else
		{
			System.out.println("     i am y");
			v = getMedian(vectors,flag);
			System.out.println(v.getY());
			v.isLeaf = false;
			vectors.remove(v);
			for(int i=0;i< vectors.size();i++)
			{
				if(vectors.get(i).getY() <= v.getY())
				{
					leftSet.add(vectors.get(i));
				}
				else
				{
					rightSet.add(vectors.get(i));
				}
			}
			v.setLeftVector(getKDtree(leftSet,1));
			v.setRightVector(getKDtree(rightSet,1));
			System.out.println("Y leftVector.size(): " + leftSet.size());
			System.out.println("Y rightVector.size(): " + rightSet.size());
			return v;
			
			/*Vector leftVector = new Vector();
			Vector rightVector = new Vector();
			v.setLeftVector(leftVector);
			v.setRightVector(rightVector);
			System.out.println("Y leftVector.size(): " + leftSet.size());
			System.out.println("Y rightVector.size(): " + rightSet.size());
			getKDtree(leftVector, leftSet,0);
			getKDtree(rightVector, rightSet,0);*/
		}
		
		
	}
	
	public Vector getMedian(ArrayList<Vector> vectors,int flag)
	{
		System.out.println("getMedian: " + vectors.size());
		Vector v;
		int n = vectors.size();
		//不管n是奇数还是偶数，得到第n/2 + 1大的数
		if(flag == 0)
		{
			//利用快速次序选择算法得到x轴的中位数
			v = orderSelection(vectors, 0, n-1, 1+n/2,flag);
			
		}
		else
		{
			//得到y轴的中位数
			v = orderSelection(vectors, 0, n-1, 1+n/2,flag); 
		
		}
		
		
		return v;
	}
	
	//快速次序选择算法
	public Vector orderSelection(ArrayList<Vector> vectors,int p,int q,int i,int flag)
	{
		System.out.println("orderSelection: " + vectors.size());
		int r;
		if(p == q)
			return vectors.get(p);
		r = partition(vectors, p, q,flag);
		System.out.println("orderSelection p: " + p +" q: " + q);
		System.out.println("orderSelection r: " + r);
		int k = r-p+1;
		System.out.println("orderSelection k: " + k);
		System.out.println("orderSelection i: " + i);
		if(i == k)
		{
			System.out.println("orderSelection is over");
			return vectors.get(r);
		}
		if(i < k)
			return orderSelection(vectors, p, r-1, i,flag);
		else
			return orderSelection(vectors, r+1, q, i-k,flag);
	}
	
	//快速排序的partition过程
	public int partition(ArrayList<Vector> vectors,int m,int n,int flag)
	{
		System.out.println("partition: " + vectors.size());
		double xy;
		int i = m;
		
		if(flag == 0)
		{
			xy = vectors.get(i).getX();
			System.out.println("partition xy: " + xy);
			for(int j=m+1;j<=n;j++)
			{
				if(vectors.get(j).getX() <= xy)
				{
					i = i+1;
					Vector temp = vectors.get(j);
					vectors.set(j,vectors.get(i));
					vectors.set(i,temp);
				}
			}	
		}
		
		else
		{
			xy = vectors.get(i).getY();
			for(int j=m+1;j<=n;j++)
			{
				if(vectors.get(j).getY() <= xy)
				{
					i = i+1;
					Vector temp = vectors.get(j);
					vectors.set(j,vectors.get(i));
					vectors.set(i,temp);
				}
			}	
		
		}
		Vector temp = vectors.get(i);
		vectors.set(i,vectors.get(m));
		vectors.set(m,temp);
		
		return i;
	}
}




