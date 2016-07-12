package com.mirdar.dataStructures.StackQueue;

/*
 * ¡¥±Ì µœ÷Stack
 * 
 */
public class ListStack<AnyType> {

	private ListNode<AnyType> topOfStack = null;
	
	public boolean isEmpty()
	{
		return topOfStack == null;
	}
	
	public void makeEmpty()
	{
		topOfStack = null;
	}
	
	public void push(AnyType x)
	{
		topOfStack = new ListNode<AnyType>(x,topOfStack);
	}
	
	public void pop() throws Exception
	{
		if(isEmpty())
			throw new Exception();
		topOfStack = topOfStack.next;
	}
	
	public AnyType top() throws Exception
	{
		if(isEmpty())
			throw new Exception();
		return topOfStack.element;
	}
	
	public AnyType topAndPop() throws Exception
	{
		if(isEmpty())
			throw new Exception();
		
		AnyType topItem = topOfStack.element;
		topOfStack = topOfStack.next;
		return topItem;
	}
}
