package my.udf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;

@Resolve({"string,string,string,bigint,string,double,double->"
		+ "string,string,bigint,double,double,double,double,double,double,double,double,double,double,"
		+ "double,double,double,double,double,double,double,double"})
//TODO define input and output types, e.g., "string,string->string,bigint".
public class Feature extends UDTF {

	String uid = "";
	String iid = "";
	String feature = "";
	public String ptime = "";
	public double buyRate = 0;
	public int numble = 0;
	int label = 0;
	List<Double> digitList = new ArrayList<Double>();
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
	String item_id = (String) args[2];
	Long date = (Long) args[0];
	Double[] v = new Double[25];
	int i = 7;
	int j = 0;
	while(i < 32){
		v[j] =(Double) args[i];
		j++;
	}
	j = 0;
	if(date == 443){ //前一天
		while(i < 32){
			digitList.add(v[j]);
			j++;
		}
	}
	if(date == 441){ //前一天
		while(i < 32){
			digitList.add(v[j]);
			j++;
		}
	}
	String uid = (String) args[0];
	String iid = (String) args[1];
	String ptime = (String) args[2];
	int label = ((Long) args[3]).intValue();
	String feature = (String) args[4];
	double buyRate = ((Double) args[5]).doubleValue();
	int numble = ((Long) args[6]).intValue();
	
	this.uid = uid;
	this.iid = iid;
	this.ptime = ptime;
	this.label = label;
	this.feature = feature;
	this.buyRate = buyRate;
	this.numble = numble;
	
	
	Pattern p = Pattern.compile("[^0-9]");
	Matcher m = p.matcher(feature);
	String result = m.replaceAll("");
	for (int i = 0; i < result.length(); i++) 
		digitList.add(result.substring(i, i+1));
	this.output();
	this.clearStatus();
 }

 public void clearStatus()
 {
	 digitList = new ArrayList<String>();
 }

 public void output() throws UDFException
 { 
     if (this.uid.length()>0 && this.iid.length()>0)
     {
         this.forward(this.uid,this.iid,
        		 this.label
                 ,digitList.get(0),digitList.get(1),digitList.get(2),digitList.get(3)
                 ,digitList.get(4),digitList.get(5),digitList.get(6),digitList.get(7)
                 ,digitList.get(8),digitList.get(9),digitList.get(10),digitList.get(11)
                 ,digitList.get(12),digitList.get(13),digitList.get(14),digitList.get(15),
                 this.buyRate,this.numble);
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
