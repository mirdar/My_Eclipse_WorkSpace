package com.mirdar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadData {

	public ArrayList<RouteSection> readSection(String filepath,Map<String,Vector> vectors) 
																		throws IOException
	{
		ArrayList<RouteSection> routeSections = new ArrayList<RouteSection>();
		File file = new File(filepath);

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			String content = null;
			while ((content = reader.readLine()) != null) {

					String[] strings = content.split(",");
					RouteSection rs = new RouteSection();
					rs.routeSectionID = Integer.parseInt(strings[0]);
					rs.I1 = vectors.get(strings[1]);
					rs.I2 = vectors.get(strings[2]);
					//length与time都加进去，length作为范围夸张的指标，而time用来得到最短时间路径
					rs.length =  Double.parseDouble(strings[3]); 
					
					routeSections.add(rs);

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

		return routeSections;
	}
	
	public Map<String,Vector>  readVector(String filepath) throws IOException
	{
//		ArrayList<Vector> vectors = new ArrayList<Vector>();
		Map<String,Vector> vectors = new HashMap<String,Vector>();
		File file = new File(filepath);

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			String content = null;
			boolean flag = true;
			while ((content = reader.readLine()) != null) {

				
				// 引用asyty的代码
					String[] strings = content.split(",");
					
					Vector v = new Vector();
					v.vectorID =strings[0];
					v.x = Double.parseDouble(strings[1]);
					v.y = Double.parseDouble(strings[2]);
					
					if(flag)
					{
						System.out.println(content);
						for(int i=0;i<strings.length;i++)
							System.out.println(strings[i]);
						System.out.println(v.vectorID + ", "+v.x+", "+v.y);
						flag = false;
					}
					
					vectors.put(v.vectorID,v);



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

		return vectors;
	}
}
