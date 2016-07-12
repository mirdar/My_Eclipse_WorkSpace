package com.mirdar.dataStructures.StackQueue;

/*
 * 数组实现Stack
 * 
 */
public class ArrayStack<AnyType>{

	private AnyType[] theArray;
	private int topofStack;
	
	private static final int DEFAULT_CAPACITY = 10;
	
	@SuppressWarnings("unchecked")
	public ArrayStack()
	{
		theArray = (AnyType[]) new Object[DEFAULT_CAPACITY];
		topofStack = -1;
	}
	public void push(AnyType x)
	{
		if(topofStack+1 == theArray.length)
			doubleArray();
		theArray[++topofStack] = x;
	}

	public AnyType top() throws Exception
	{
		if(isEmpty())
			throw new Exception("ArrayStack top");
		return theArray[topofStack];
	}
	public void pop() throws Exception
	{
		if(isEmpty())
			throw new Exception("ArrayStack top");
		topofStack--;
	}

	public AnyType topAndPop() throws Exception
	{
		if(isEmpty())
			throw new Exception("ArrayStack top");
		return theArray[topofStack--];
	}
	
	@SuppressWarnings("unchecked")
	private void doubleArray()
	{
		AnyType[] newArray;
		newArray = (AnyType[]) new Object[theArray.length*2];
		
		for(int i=0;i<topofStack;i++)
			newArray[i] = theArray[i];
		theArray = newArray;
	}
	
	public void makeEmpty()
	{
		topofStack = -1;
	}

	public boolean isEmpty()
	{
		return topofStack == -1;
	}

	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
