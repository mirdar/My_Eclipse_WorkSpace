package com.mirdar.classifer;

import java.util.HashMap;
import java.util.Map;

public class Node {
	String value; //为了打印显示方便
	String featureName;
	//该节点的取值，与子节点
	Map<String,Node> childNode = new HashMap<String,Node>();
}
