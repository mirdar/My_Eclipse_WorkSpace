package com.mirdar.dataStrctures.Linklist;
/*
 * 该链表需要由链表本身类，一个单独的迭代器，储存基本表节点的单独的类
 * java.util.LinkedList也是按上面模式实现，不过使用的是头尾双节点（都为null,初始化为end.pre=start,start.next=end）
 * 实现了双链表，
 */
public class LinkedList<AnyType> {
	
	private ListNode<AnyType> header;

	public LinkedList()
	{
		header = new ListNode<AnyType>(null);
	}
	//为链表设置空的头节点，为了防止删除第一个节点后无法进行节点移动，而且也不能插入在链表的首项，
	public boolean isEmpty()
	{
		return header.next == null;
	}
	
	public void makeEmpty()
	{
		header.next = null;
	}
	//返回一个带null头节点的迭代器，使用单独的迭代器好处是允许同时访问不同位置
	public LinkedListIterator<AnyType> zeroth()
	{
		return new LinkedListIterator<AnyType>(header);
	}
	//返回一个无null头节点的迭代器
	public LinkedListIterator<AnyType> first()
	{
		return new LinkedListIterator<AnyType>(header.next);
	}
	//返回对应x元素的位置
	public LinkedListIterator<AnyType> find(AnyType x)
	{
		ListNode<AnyType> itr = header.next;
		//当前位置比较
		while(itr != null && itr.element.equals(x))
			itr = itr.next;
		return new LinkedListIterator<AnyType>(itr);
	}
	
	public void remove(AnyType x)
	{
		LinkedListIterator<AnyType> p = findPrevious(x);
		
		if(p.current.next != null)
			p.current.next = p.current.next.next;
	}
	//返回对应x元素的前一个位置
	public LinkedListIterator<AnyType> findPrevious(AnyType x)
	{
		ListNode<AnyType> itr = header;
		//next位置比较
		while(itr.next != null && itr.next.element.equals(x))
			itr = itr.next;
		
		return new LinkedListIterator<AnyType>(itr);
	}
	
	public void insert(AnyType x, LinkedListIterator<AnyType> p)
	{
		if(p != null && p.current != null)
			p.current.next = new ListNode<AnyType>(x, p.current.next);
	}
}
