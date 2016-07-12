package my.udf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args)
	{
		List<String> digitList = new ArrayList<String>();
		Pattern p = Pattern.compile("[^0-9]");
		Matcher m = p.matcher("1 2 345 4");
		String result = m.replaceAll("");
		for (int i = 0; i < result.length(); i++) 
			digitList.add(result.substring(i, i+1));
		for(int i=0;i<digitList.size();i++)
			System.out.println(" "+digitList.get(i));
	}
}
