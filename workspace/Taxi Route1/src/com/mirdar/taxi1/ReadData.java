package com.mirdar.taxi1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadData {

	public ArrayList<RouteSection> readSection(String filepath,ArrayList<Vector> vectors) 
																		throws IOException
	{
		ArrayList<RouteSection> routeSections = new ArrayList<RouteSection>();
		File file = new File(filepath);

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			String content = null;
			while ((content = reader.readLine()) != null) {

					String[] strings = content.split(" ");
					RouteSection rs = new RouteSection();
					rs.routeSectionID = Integer.parseInt(strings[0]);
					rs.I1 = vectors.get(Integer.parseInt(strings[1]));
					rs.I2 = vectors.get(Integer.parseInt(strings[2]));
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
	
	public ArrayList<Vector> readVector(String filepath) throws IOException
	{
		ArrayList<Vector> vectors = new ArrayList<Vector>();
		File file = new File(filepath);

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			String content = null;
			boolean flag = true;
			while ((content = reader.readLine()) != null) {

				
				// 引用asyty的代码
					String[] strings = content.split(" ");
					
					Vector v = new Vector();
					v.vectorID = Integer.parseInt(strings[0]);
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
					
					vectors.add(v);

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
