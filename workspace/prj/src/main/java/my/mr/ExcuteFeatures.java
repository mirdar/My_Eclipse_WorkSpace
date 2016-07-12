package my.mr;

import java.io.IOException;
import java.util.Iterator;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.TableInfo;
import com.aliyun.odps.mapred.JobClient;
import com.aliyun.odps.mapred.MapperBase;
import com.aliyun.odps.mapred.ReducerBase;
import com.aliyun.odps.mapred.conf.JobConf;
import com.aliyun.odps.mapred.utils.InputUtils;
import com.aliyun.odps.mapred.utils.OutputUtils;

public class ExcuteFeatures {

	public static class TokenizerMapper extends MapperBase {
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
			key.set(new Object[]{record.get(3).toString()});
			value.set(0, new Object[]{record.get(1).toString()});
			value.set(1, new Object[]{record.get(31).toString()});
			context.write(key, value);
		}
	}

	public static class SumReducer extends ReducerBase {
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
			int[] temp = new int[444];
			result.set(0, key.get(0));
			while (values.hasNext())
			{
				Record val = values.next();
				int x = (Integer) val.get(0);
				if (x >= 416 && x <= 443) // 取28天的试试
				{
					temp[x] = (Integer) val.get(1);
				}
			}
			int j = 1;
			for (int i = 416; i <= 443; i++)
			{
				result.set(j, temp[416]);
				j++;
			}

			context.write(result);
		}
	}

	public static void main(String[] args) throws Exception
	{
		if (args.length != 2)
		{
			System.err.println("Usage: should be 32 rows ");
			System.exit(2);
		}
		JobConf job = new JobConf();
		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(SumReducer.class);
//		job.setMapOutputKeySchema(SchemaUtils.fromString("word:string"));
//		job.setMapOutputValueSchema(SchemaUtils.fromString("count:bigint"));
		InputUtils.addTable(TableInfo.builder().tableName(args[0]).build(),
				job);
		OutputUtils.addTable(TableInfo.builder().tableName(args[1]).build(),
				job);
		JobClient.runJob(job);
	}

}
