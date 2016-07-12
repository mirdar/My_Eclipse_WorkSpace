package com.mirdar.dataStrctures.Linklist;
/*
 * ”––Ú¡¥±Ì£¨»√AnyTypeºÃ≥–¡ÀComparable
 * 
 */
public class SortedLinkedList<AnyType extends Comparable<? super AnyType>> extends LinkedList<AnyType> {

	public void insert(AnyType x, LinkedListIterator<AnyType> p)
	{
		insert(x);
	}
	
	public void insert(AnyType x)
	{
		LinkedListIterator<AnyType> prev = zeroth();
		LinkedListIterator<AnyType> curr = first();
		
		while(curr.isValid() && x.compareTo(curr.retrieve()) > 0)
		{
			prev.advance();
			curr.advance();
		}
		super.insert(x, prev);
	}
}
