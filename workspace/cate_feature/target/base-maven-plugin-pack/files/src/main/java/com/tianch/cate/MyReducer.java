package com.tianch.cate;

import java.io.IOException;
import java.util.Iterator;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Reducer;

/**
 * Reducer模板。请用真实逻辑替换模板内容
 */
public class MyReducer implements Reducer {
	private Record result = null;
	@Override
	public void setup(TaskContext context) throws IOException
	{
		result = context.createOutputRecord();
	}
	@Override
	public void reduce(Record key, Iterator<Record> values,
			TaskContext context) throws IOException
	{
		Long[] temp = new Long[1227];
		for(int i=0;i<1227;i++)
		{
			temp[i] = (long) 0;
		}
//		Set<Long> cate_set = new TreeSet<Long>();
//		HashMap<Long,Double> map = new HashMap<Long,Double>();
		String id = null;
		result.set(0,key.get(0));
		if(values.hasNext())
		{
			Record val = values.next();
			id = String.valueOf(val.getBigint(0));
		}
		temp[Integer.valueOf(id)] = (long) 1;
		for(int i=0;i<1227;i++)
		{
			result.set(i+1,temp[i]);
		}
		
		context.write(result);
	}

    public void cleanup(TaskContext arg0) throws IOException {

    }
}
