package com.mirdar.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
	
	public static void main(String[] args) throws ParseException
	{
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = fmt.parse("2014-11-19 10");
		System.out.println(date1);
		Date date3 = fmt.parse("2014-11-19 22");
		System.out.println(date3);
		Date date2 = fmt.parse("2014-11-11 01");
		System.out.println((int)(date1.getTime()-date2.getTime())/(1000L*60*60*24));
		
	/*	ArrayList<Vector> vectors = new ArrayList<Vector>();
		System.out.println(vectors);
		 for(int i=0;i<5;i++)
		 {
			 vectors.set(i,new Vector());
		 }
		 
		 for(int i=0;i<5;i++)
			{
			 	Vector v = new Vector();
			 	v.setX(Math.random()+2);
			 	v.setY(Math.random()+1);
			 	vectors.add(v);
			}
		 for(int i=0;i<5;i++)
		 {
			 System.out.println(vectors.get(i).getX());
		 }
		 System.out.println("---------");
		 for(int i=0;i<5;i++)
		 {
			 System.out.println(vectors.get(i).getY());
		 }
		 KDtree tree = new KDtree(vectors,0);
		 tree.root.vprint();
		 System.out.println(tree.root.isLeaf);*/
//		 for(int i=0;i<5;i++)
//			{
//				if(i == 3) continue;
//				if(i >= 0)
//				System.out.println("i= "+ i);
//			}
		 //java中的值传递原因
//		 System.out.println(tree.root.leftVector.isLeaf);
//		 System.out.println(tree.root.rightVector.isLeaf);
//		 System.out.println(tree.root.leftVector.leftVector.isLeaf);
//		 System.out.println(tree.root.rightVector.leftVector.isLeaf);
		 
//		 print(tree.root);
		 
	}
//	
//	static void print(Vector v)
//	{
//		if(v.isLeaf)
//		{
//			v.vprint();
//			return;
//		}
//		v.vprint();
//		if(v.leftVector != null)
//			print(v.leftVector);
//		if(v.rightVector != null)
//			print(v.rightVector);
//	}
	
	
	
	
	
}
