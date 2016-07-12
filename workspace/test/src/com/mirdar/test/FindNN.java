package com.mirdar.test;

import java.util.ArrayList;

//查询最近点
public class FindNN {

	public Vector getNN(ArrayList<Vector> vectors,Vector v) throws Exception
	{
		//判断是x还是y是当前分割点
		int flag = 0;
		//记录最短距离
		double d;
		//保存搜索路径的队列
		vStack stack = new vStack();
		Vector currentVector = null;
		//与查询点v最近的Vector
		Vector nnVector = null;
		//生成kd树
		KDtree tree = new KDtree(vectors,0);
		Distance distance = new Distance();
		currentVector = tree.root;
		stack.push( currentVector);
		//记录搜索路线
		while(currentVector.isLeaf)
		{
			if(flag == 0)
			{
				if(v.getX() < currentVector.getX())
					 currentVector =  currentVector.leftVector;
				else
					 currentVector =  currentVector.rightVector;
				stack.push( currentVector);
				flag = 1;
			}
			else
			{
				if(v.getY() <  currentVector.getY())
					 currentVector =  currentVector.leftVector;
				else
					 currentVector =  currentVector.rightVector;
				stack.push( currentVector);
				flag = 0;
			}
		}
		Vector vector = stack.pop();
		d = distance.distance(v, vector);
		nnVector = vector;
		
		if(stack.lenght % 2 ==0)
		{
			//该叶节点的上一级节点由x分割
			do
			{
				vector = stack.pop();
				
			}while(stack.lenght > 0);
		}
		else
		{
			//该叶节点的上一级节点由y分割
		}
		
		
		return nnVector;
	}
	
	//寻找查询点的最近点
	public Vector findNN(ArrayList<Vector> vectors,Vector v,vStack stack,double minDis)
	{
//		double d;
		Distance distance = new Distance();
		Vector vector = null;
//		vector = 
		return vector;
	}
}
