package com.mirdar.dataStructures.CollectionAndIterator;

public interface Iterator<AnyType> extends java.util.Iterator<AnyType>{
	
	boolean hasNext();
	
	AnyType next();
	
	void remove();

}
