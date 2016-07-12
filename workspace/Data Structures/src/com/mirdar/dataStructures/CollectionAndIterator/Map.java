package com.mirdar.dataStructures.CollectionAndIterator;

import java.util.Set;

/*
 * HashMap与TreeMap实现了Map接口，其中TreeMap中的关键字是有序的（实现了一个SortedMap接口）
 * 
 */
public interface Map<KeyType, ValueType> extends java.io.Serializable{

	int size();
	
	boolean isEmpty();
	
	boolean containsKey(KeyType key);
	
	ValueType get(KeyType key);
	
	ValueType put(KeyType key, ValueType value);
	
	ValueType remove(KeyType key);
	
	Set<KeyType> keySet();
	
	Collection<ValueType> values();
	
	/*键/值对的序偶集合
	 * 例如
	 * Set<Map.Entry<KeyType,ValueType>> entryies = map.entrySet();
	 * for(Map.Entry<KeyType,ValueType> thisPair : entries)
	 * {
	 * 		sysout(thisPair.getKey()+thisPair.getValue())
	 * }
	 * 
	 */
	Set< Enrty<KeyType,ValueType>> entrySet();
	
	public interface Enrty<KeyType,ValueType> extends java.io.Serializable
	{
		KeyType getKey();
		
		ValueType getValue();
		
		ValueType setValue(ValueType newValue);
	}
}
