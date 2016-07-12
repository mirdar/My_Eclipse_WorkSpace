package com.mirdar.dataStructures.IteratorAndFactoryPattern;


public class MyContainer {

	Object[] items;
	int size;
	
	public Iterator iterator()
	{
		return new MyContainerIterator(this);
	}
	
	public void add(Object object)
	{
		items[size] = object;
		size++;
	}
}
