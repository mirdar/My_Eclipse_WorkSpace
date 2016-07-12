package my.mr;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Reducer;
import com.aliyun.odps.mapred.Reducer.TaskContext;

import java.io.IOException;
import java.util.Iterator;

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

    public void cleanup(TaskContext arg0) throws IOException {

    }
}
