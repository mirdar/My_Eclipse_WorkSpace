package com.mirdar.dataStructures.StackQueue;

/*
 * 数组实现队列，用了首尾指针，一个currentSize，也可实现加倍扩展与实现Stack不一样
 * 
 */
public class ArrayQueue<AnyType> {

	private AnyType[] theArray;
	private int currentSize;
	private int front;
	private int back;
	
	private static final int DEFAULT_CAPACITY = 10;
	
	public int increment(int x)
	{
		if(++x == theArray.length) //这里就实现了循环队列
			x = 0;
		return x;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayQueue()
	{
		theArray = (AnyType []) new Object[DEFAULT_CAPACITY];
		makeEmpty();
	}
	
	public void enqueue(AnyType x)
	{
		if(currentSize == theArray.length)
			doubleQueue();
		back=increment(back);
		theArray[back] = x;
		currentSize++;
	}
	
	@SuppressWarnings("unchecked")
	private void doubleQueue()
	{
		AnyType[] newArray;
		newArray = (AnyType[]) new Object[theArray.length*2];
		
		//因为dequeue操作会使得front往后退,使得队列首与队列大小改变
		for(int i=0;i<currentSize;i++,front = increment(front))
			newArray[i] = theArray[front];
		theArray = newArray;
		front = 0;
		back = currentSize-1;
	}
	
	public AnyType dequeue() throws Exception
	{
		if(isEmpty())
			throw new Exception("ArrayStack top");
		currentSize--;
		
		AnyType returnValue = theArray[front];
		front = increment(front);
		return returnValue;
	}
	
	public AnyType getFront() throws Exception
	{
		if(isEmpty())
			throw new Exception("ArrayStack top");
		return theArray[front];
	}
	
	public boolean isEmpty()
	{
		return currentSize == 0;
	}
	
	public void makeEmpty()
	{
		currentSize = 0;
		front = 0;
		back = -1;
	}
}
