package com.mirdar.dataStrctures.Linklist;

public class ListNode<AnyType> {
	
	public AnyType element;
	public ListNode<AnyType> next;
	
	public ListNode(AnyType theElement)
	{
		this(theElement,null);
	}
	
	public ListNode(AnyType theElement, ListNode<AnyType> n)
	{
		element = theElement;
		next = n;
	}
}
