package com.mirdar.dataStructures.CollectionAndIterator;

public class vStack {

	Vnode head;
	Vnode current;
	int lenght;
	
	public void push(Vector v)
	{
		if(head == null)
		{
			head = new Vnode(v);
			current = head;
			lenght++;
		}
		else
		{
			Vnode node = new Vnode(v);
			node.pre = current;
			current = node;
			lenght++;
		}
	}
	
	public Vector pop() throws Exception
	{
		if(current == null)
			throw new Exception("╤сапн╙©у");
		Vnode node = current;
		current = current.pre;
		lenght--;
		return node.v;
		
	}
	
	public int lenght()
	{
		return lenght;
	}
}
