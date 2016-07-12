package com.mirdar.dataStructures.CollectionAndIterator;

/*
 * ArrayList与LinkList实现了List接口，其中ArrayList主要用来查找，并且可以自动数值空间加倍
 * LinkList主要用来插入语删除操作
 * 
 */

public interface List<AnyType> extends Collection<AnyType>{

	AnyType get(int idx);
	
	AnyType set(int idx,AnyType newVal);
	
	//ListIterator是个双向迭代器
//	ListIterator<AnyType> listIterator(int pos);
}
