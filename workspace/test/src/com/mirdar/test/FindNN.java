package com.mirdar.test;

import java.util.ArrayList;

//��ѯ�����
public class FindNN {

	public Vector getNN(ArrayList<Vector> vectors,Vector v) throws Exception
	{
		//�ж���x����y�ǵ�ǰ�ָ��
		int flag = 0;
		//��¼��̾���
		double d;
		//��������·���Ķ���
		vStack stack = new vStack();
		Vector currentVector = null;
		//���ѯ��v�����Vector
		Vector nnVector = null;
		//����kd��
		KDtree tree = new KDtree(vectors,0);
		Distance distance = new Distance();
		currentVector = tree.root;
		stack.push( currentVector);
		//��¼����·��
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
			//��Ҷ�ڵ����һ���ڵ���x�ָ�
			do
			{
				vector = stack.pop();
				
			}while(stack.lenght > 0);
		}
		else
		{
			//��Ҷ�ڵ����һ���ڵ���y�ָ�
		}
		
		
		return nnVector;
	}
	
	//Ѱ�Ҳ�ѯ��������
	public Vector findNN(ArrayList<Vector> vectors,Vector v,vStack stack,double minDis)
	{
//		double d;
		Distance distance = new Distance();
		Vector vector = null;
//		vector = 
		return vector;
	}
}
