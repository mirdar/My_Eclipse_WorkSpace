	package my.group.mymr;

import java.io.IOException;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Mapper;

/**
 * Mapper模板。请用真实逻辑替换模板内容
 */
public class MyMapper implements Mapper {
	private Record key;
	private Record value;
	@Override
	public void setup(TaskContext context) throws IOException
	{
		key = context.createMapOutputKeyRecord();
		value = context.createMapOutputValueRecord();
		System.out.println("TaskID:" + context.getTaskID().toString());
	}
	@Override
	public void map(long recordNum, Record record, TaskContext context)
			throws IOException
	{
		String item_id = String.valueOf(record.getBigint(2));
		Long date = record.getBigint(0);
		Double sale = Double.valueOf(record.getBigint(30));
		
		key.set(0,item_id);
		value.set(0,date);
		value.set(1,sale);
		context.write(key, value);
	}
	@Override
	public void cleanup(TaskContext arg0) throws IOException
	{
		// TODO Auto-generated method stub
		
	}
}