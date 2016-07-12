package com.mirdar.dataStructures.CollectionAndIterator;

public interface Collection<AnyType> extends Iterable<AnyType>,java.io.Serializable{

	int size();
	
	boolean isEmpty();
	
	boolean contains(Object x);
	
	boolean add(AnyType x);
	
	boolean remove(Object x);
	
	void clear();
	
	Iterator<AnyType> iterator();
	
	Object[] toArray();
	
	<OtherType> OtherType[] toArray(OtherType[] arr);
	
}
