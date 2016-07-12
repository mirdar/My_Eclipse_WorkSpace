package com.mirdar.dataStructures.StackQueue;

/*
 * 链表实现队列
 * 
 */
public class ListQueue<AnyType> {

	private ListNode<AnyType> front;
	private ListNode<AnyType> back;
	
	public ListQueue()
	{
		front = back = null;
	}
	
	public void enqueue(AnyType x)
	{
		if(isEmpty())
			back = front = new ListNode<AnyType>(x);
		else
			back = back.next = new ListNode<AnyType>(x);
	}
	
	public AnyType dequeue()
	{
		
		AnyType returnValue = front.element;
		front = front.next;
		return returnValue;
	}
	
	public AnyType getFront()
	{
		return front.element;
	}
	
	public void makeEmpty()
	{
		front = null;
		back = null;
	}
	
	public boolean isEmpty()
	{
		return front == null;
	}
}
