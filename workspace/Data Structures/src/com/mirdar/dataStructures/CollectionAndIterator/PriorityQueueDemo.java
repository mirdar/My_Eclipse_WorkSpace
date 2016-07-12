package com.mirdar.dataStructures.CollectionAndIterator;

import java.util.PriorityQueue;

public class PriorityQueueDemo {

	//优先队列需要传入一个比较器而<AnyType extends Comparable<? super AnyType>（将AnyType继承了Comparable）
	//使得AnyType实现了比较器的功能
	public  static <AnyType extends Comparable<? super AnyType>>
			void dumpPQ(String msg,PriorityQueue<AnyType> pq)
			{
				System.out.println(msg + ":");
				while(!pq.isEmpty())
					System.out.println(pq.remove());
			}
	
	//PriorityQueue一般有add,element,remove方法,根据给定的比较元素进行优先输出
	public static void main(String[] args)
	{
		PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>();
		
		minPQ.add(3);
		minPQ.add(5);
		minPQ.add(6);
		
		dumpPQ("minPQ", minPQ);
	}
}
