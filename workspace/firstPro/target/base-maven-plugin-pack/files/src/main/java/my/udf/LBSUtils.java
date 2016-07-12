package my.udf;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LBSUtils 
{
	public static int DateSubtract(String d1, String d2) throws ParseException
	{
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = fmt.parse(d1);
		Date date2 = fmt.parse(d2);
		return (int) ((date1.getTime()-date2.getTime())/(1000L*60*60*24)); //返回d2与d1相隔的天数
	}
	
	public static String Join(int[] arr, String sep)
	{
		if ( arr.length==0)
		{
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(arr[0]);
		for(int i=1;i<arr.length;i++)
		{
			sb.append(sep);
			sb.append(arr[i]);
		}
		return sb.toString();
	}
	
	public static void main(String args[]) throws ParseException
	{
		System.out.println(DateSubtract("2014-02-19 20", "2014-12-18"));
	}
}
