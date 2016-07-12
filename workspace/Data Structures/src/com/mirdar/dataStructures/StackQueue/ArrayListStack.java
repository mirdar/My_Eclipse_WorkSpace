package com.mirdar.dataStructures.StackQueue;

import java.util.ArrayList;

/*
 * 利用java.util.ArrayList实现的Stack
 * 
 */
@SuppressWarnings("serial")
public class ArrayListStack<AnyType> implements java.io.Serializable {

	private ArrayList<AnyType> items;
	
	public ArrayListStack()
	{
		items = new ArrayList<AnyType>();
	}
	
	public AnyType push(AnyType x)
	{
		items.add(x);
		return x;
	}
	
	public AnyType pop()
	{
		return items.remove(items.size()-1);
	}
	
	public AnyType peek()
	{
		return items.get(items.size()-1);
	}
	
	public boolean isEmpty()
	{
		return items.size() == 0;
	}
	
	public int size()
	{
		return items.size();
	}
	
	public void clear()
	{
		items.clear();
	}
	
	
}
