package my.group.store;

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
		Double[] temp = new Double[444];
		
//		HashMap<Long,Double> map = new HashMap<Long,Double>();
		result.set(0, key.get(0));
		result.set(1,key.get(1));
		while (values.hasNext())
		{
			Record val = values.next();
			Long date = val.getBigint(0);
			Double sale = val.getDouble(1); 
			
			if (date >= 402 && date <= 443) // 取42天的试试
			{
				temp[Integer.valueOf(String.valueOf(date))] = sale;
				 
			}
		}
		
		int j = 2;
		int flag = 0;
		int neg = 0;
		double perday = 0;
		for (int i = 402; i <= 443; i++)
		{
			if(flag == 1 && temp[i] == null){ //为了平滑中间缺失值
				temp[i] = temp[i-1];
			}
			if(temp[i] != null){
				flag = 1;
				perday = perday + temp[i];
			}
			if(temp[i] == null){ //前面未出现的日期填充-1
				temp[i] = -1.0;
				neg++;
			}
			result.set(j, temp[i]);
			System.out.println(temp[i]);
			j++;
		}
		
		result.set(j,neg);
		result.set(j+1,perday/(42-neg));
		result.set(j+2,perday);
		/*Double[] week = new Double[4]; //按周取平均值
		Double[] two_week = new Double[2]; //按两周取平均值
		int k = 0;
		if(temp[416] >= 0){
			for (int i = 416; i <= 443; i=i+7)
			{
				week[k] = (temp[i]+temp[i+1]+temp[i+2]+temp[i+3]+temp[i+4]+temp[i+5]+temp[i+6]);
				k++;
			}
			two_week[0] = (week[0]+week[1]);
			two_week[1] = (week[2]+week[3]);
		}
		else{
			while(k < 4)
				week[k] = 0.0; //当出现日期在416日后时，一周与两周的总和都设置为0
			two_week[0] = 0.0;
			two_week[1] = 0.0;
		}
		result.set(16,week[0]);
		result.set(17,week[1]);
		result.set(18,week[2]);
		result.set(19,week[3]);
		result.set(20,two_week[0]);
		result.set(21,two_week[1]);*/
		
		context.write(result);
	}

    public void cleanup(TaskContext arg0) throws IOException {

    }
}
