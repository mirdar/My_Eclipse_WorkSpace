package com.mirdar.datastruct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadData {

	public ArrayList<Record> readRecord(String filename, int xBound, int yBound)
	{
		ArrayList<Record> records = new ArrayList<Record>();
		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String content = null;
			while ((content = reader.readLine()) != null)
			{
				String[] strings = content.split(",");
				Record record = new Record();
				record.record_id = Integer.parseInt(strings[0]);
				record.time = Integer.parseInt(strings[1]);
				record.x = Double.parseDouble(strings[2]);
				record.y = Double.parseDouble(strings[3]);
				record.flag = Integer.parseInteger(strings[4]);
				record.destRecordId = Integer.parseInteger(strings[5]);
				record.grid_id = (int) (Math.floor(record.x / 10) // 这是通过坐标计算格子的id
						+ Math.floor(record.y / 10) * Math.ceil(xBound)/ 10);
				records.add(record);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return records;
	}
}
