package com.mirdar.dataStrctures.Linklist;

public class LinkedListIterator<AnyType> {

	ListNode<AnyType> current;
	
	LinkedListIterator(ListNode<AnyType> theNode)
	{
		current = theNode;
	}
	//是否有元素
	public boolean isValid()
	{
		return current != null;
	}
	//返回当前元素
	public AnyType retrieve()
	{
		return isValid() ? current.element : null;
	}
	//当前位置往下
	public void advance()
	{
		if(isValid())
			current = current.next;
	}
}
