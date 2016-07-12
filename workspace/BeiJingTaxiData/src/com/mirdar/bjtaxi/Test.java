package com.mirdar.bjtaxi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mirdar.ssa.*;

public class Test {

	public static void main(String[] args)
	{
		String filePath = "F:/route assignment/BeiJingRouteData/Data/Trajectory Data/segment_feature2.csv";
		Graph graph = new Graph();
		File file = new File(filePath);
		BufferedReader reader = null;
		
		Map<String,Integer> nodeMap = new HashMap<String,Integer>();
		ArrayList<String> nodeList = new ArrayList<String>();
		
		int j=0;
		try {
			reader = new BufferedReader(new FileReader(file));

			String content = null;
			while ((content = reader.readLine()) != null) {

					String[] strings = content.split(",");
					graph.addEdge(strings[1], strings[2], Double.parseDouble(strings[3]),Integer.parseInt(strings[4]));
					if(nodeMap.get(strings[1]) == null){
						nodeMap.put(strings[1], 1);
						nodeList.add(strings[1]);
					}
					if(nodeMap.get(strings[2]) == null){
						nodeMap.put(strings[2], 1);
						nodeList.add(strings[2]);
					}
						
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
//		for(int i=0;i<20;i++) //nodeList������ȷ
//			System.out.println("nodeList"+"["+i+"]"+nodeList.get(i));
//		System.out.println("the numble of vertex in Graph is:" + graph.vertexMap.size());
		System.out.println("the length of nodeList is:" + nodeList.size());
		System.out.println("the length of nodeList is:" + nodeMap.size());
		System.out.println(nodeList.get(6));
		System.out.println(nodeList.get(7)); 
		graph.dijkstra(nodeList.get(7));
		graph.printPath(nodeList.get(12)); 
		
		int flag=0; //��¼��ĳ��node�����ж��ٵ�����node���ɴ�
		//�������˲���ͨ�������õ���vectorMap�е�Vector�����໥��ͨ��
		for(int i=0;i<nodeList.size();i++)
		{
			//cleanVertex���ص��ǵ����node��·������
			if(graph.cleanVertex(nodeList.get(i)).dist >10000)
			{
				flag++;
				nodeMap.remove(nodeList.get(i));
			}
		}
		System.out.println("����ͨ��node������"+flag);
		System.out.println("the numble of vertex in Graph is:" + nodeMap.size());
	}
}
