	package my.mr;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Mapper;
import com.aliyun.odps.mapred.Mapper.TaskContext;

import java.io.IOException;

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
		String item_id = record.getString(2);
		Long date = record.getBigint(0);
		Double sale = record.getDouble(30);
		
		key.setString(0,item_id);
		value.setBigint(0, date);
		value.setDouble(1, sale);
		context.write(key, value);
	}
	@Override
	public void cleanup(TaskContext arg0) throws IOException
	{
		// TODO Auto-generated method stub
		
	}
}