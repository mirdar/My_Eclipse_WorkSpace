package my.udf;

import java.text.ParseException;
import java.util.Arrays;
import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;
import com.aliyun.odps.udf.UDFException;

@Resolve({"string,string,bigint,string,string,bigint->string,string,string,string"})
// TODO define input and output types, e.g., "string,string->string,bigint".
public class ExtractPairCount extends UDTF {

	public String lastUid = "";
	public String lastIid = "";
	public int[] intervals = {1,3,7,100};
	public int currentIndex = 0;
	public int[] counts = new int[4*4];
	public String ptime = "";
//	public double buyRate = 0;
//	public double numble = 0;
	@Override
	public void setup(ExecutionContext ctx) throws UDFException
	{
		
	}
    /**
     * UDTF Process接口
     *
     * 每条记录都会调用此接口�??
     */
    public void process(Object[] args) throws UDFException 
    {
	String uid = (String) args[0];
	String iid = (String) args[1];
	int behavior = ((Long) args[2]).intValue();
	String atime = (String) args[3];
	String ptime = (String) args[4];
	int cnt = ((Long) args[5]).intValue();
//	double buyRate = ((Double) args[6]).doubleValue();
//	double numble = ((Double) args[7]).doubleValue();
//	
//	this.buyRate = buyRate;
//	this.numble = numble;
	this.ptime = ptime;
	
		//同一（user_id,item_id）对的记录不清空couts数据
        if (uid.compareTo(this.lastUid)==0 && iid.compareTo(this.lastIid)==0)
        {
		
        }
        else
        {
            //new pair
            //output old pair
            if (this.lastUid.length()>0 && this.lastIid.length()>0)
            {
                this.output();
            }

            //add new pair
            this.lastUid = uid;
            this.lastIid = iid;
            this.clearStatus();
        }

        int dateDiff;
		try {
			dateDiff = LBSUtils.DateSubtract(ptime,atime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		//intervals是一个[1,3,7,100]的数组，数值代表在前几天范围内，currentIndex取值（0,1,2,3）
        while (dateDiff > this.intervals[this.currentIndex])
        {
            this.currentIndex+=1;
        }
      //所以针对不同4个不同的日期范围对4种行为进行了统计，刚好构成了4*4的大小
        this.counts[this.currentIndex*4+behavior-1]+=cnt;   	
    }

    public void clearStatus()
    {
        Arrays.fill(this.counts, 0);
        this.currentIndex = 0;
    }

    public void output() throws UDFException
    {
        if (this.lastUid.length()>0 && this.lastIid.length()>0)
        {
            for(int i=0;i<this.intervals.length-1;i++)
            {
            	for(int k=0;k<4;k++)
            	{
            		//数据累积
                    this.counts[(i+1)*4 + k] += this.counts[i*4+k];
            	}
            }

            String feature = LBSUtils.Join(this.counts, " ");
            this.forward(this.lastUid,this.lastIid,
                this.ptime
                ,feature);
        }
    }
		
    /**
     * UDTF Close接口
     *
     * 任务�?后调用此接口，规格化�?有数据并输出。forward方法用于输出结果
     */
    public void close() throws UDFException 
    {
	this.output();
    }

}
