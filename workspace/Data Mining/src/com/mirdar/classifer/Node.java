package com.mirdar.classifer;

import java.util.HashMap;
import java.util.Map;

public class Node {
	String value; //Ϊ�˴�ӡ��ʾ����
	String featureName;
	//�ýڵ��ȡֵ�����ӽڵ�
	Map<String,Node> childNode = new HashMap<String,Node>();
}
