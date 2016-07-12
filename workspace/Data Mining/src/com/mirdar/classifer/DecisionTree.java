package com.mirdar.classifer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * 决策树要用递归思想
 */

public class DecisionTree {

	public static void main(String[] args)
	{
		String fliename = "C:/Users/zpp/Desktop/datamining/classAndcluster/classdata.txt";
		DecisionTree dt = new DecisionTree();
		ArrayList<Record> records = dt.readData(fliename);
		System.out.println("department  " + "status  " + "age  " + "salary  "
				+ "count  " + "age_cate  " + "salary_cate  ");
		ArrayList<String> featureName = new ArrayList<String>(); //存储属性名字集合
		featureName.add("department");
		featureName.add("age");
		featureName.add("salary");
		
		for (int k = 0; k < records.size(); k++) // 数据预处理成功
		{
			System.out.println(records.get(k).getDepartment() + "  "
					+ records.get(k).getStatus() + "  "
					+ records.get(k).getAge() + "  "
					+ records.get(k).getSalary() + "  "
					+ records.get(k).getCount() + "  "
					+ records.get(k).getAge_cate() + "  "
					+ records.get(k).getSalary_cate());
		}
		
		DecisionTree dT = new DecisionTree();
//		double gain = dT.getGain(records, "department"); //信息增益计算正常
		Node root = dT.bulitDT(records,featureName);
		System.out.println("root feature name: "+root.featureName);
//		for(String key : root.childNode.keySet())
//		{
//			System.out.println(key+": "+root.childNode.get(key).featureName);
//		}
		dt.traverse(root,0);
	}
	//树的遍历
		public void traverse(Node node,int k)
		{
			if(node == null)
				System.out.println();
			print(node,k);
			k++;
			for( String key : node.childNode.keySet())
			{
				traverse(node.childNode.get(key),k);
			}
		}
		public void print(Node node,int i)
		{
			for(int j=0;j<i;j++)
			{
				System.out.print("	");
			}
			if(node.value == null)
				System.out.println("root: "+node.featureName);
			else
				System.out.print(node.value+": "+node.featureName);
			System.out.println();
		}
		
	public Node bulitDT(ArrayList<Record> records,ArrayList<String> featureName)
	{
		if(featureName == null)
		{
			return null;
		}
		System.out.println("feature.size: "+featureName.size());
		Node node = new Node();
		double gain = 0; 
		String splitFeature = null;
		ArrayList<String> newFeatureName = new ArrayList<String>(); 
		for(int i=0;i<featureName.size();i++) //还要根据feature的value存到map中
		{
			System.out.println("featureName: "+featureName.get(i));
			double gains = getGain(records, featureName.get(i));
			System.out.println("=============================");
			if(gains > gain) //选出信息增益最大的feature
				splitFeature = featureName.get(i);
		}
		node.featureName = splitFeature;
		System.out.println("----splitFeature----- : "+splitFeature);
		//根据信息增益最大的feature进行分裂
		Map<String,ArrayList<Record>> map = getFeatureContent(records, splitFeature);
		
		for(int i=0;i<featureName.size();i++) //去除已经进行的feature
		{
			if(!splitFeature.equals(featureName.get(i))) 
				newFeatureName.add(featureName.get(i));
		}
		
		for(String key : map.keySet()) //根据feature的value进行分裂
		{
			for(int i=0;i<map.get(key).size();i++)
			{
				if(validSplit(map.get(key))) //需要分支
				{
					Node child = bulitDT(map.get(key),newFeatureName);
					node.childNode.put(key, child);
					node.childNode.get(key).value = key;
				}
				else
				{
					Node child = new Node(); //不需要分支
					child.featureName = map.get(key).get(0).getStatus();
					node.childNode.put(key, child);
					node.childNode.get(key).value = key;
				}
					
			}
		}
		
		return node;
	}
	//feature不同取值进行分裂
	public Map<String,ArrayList<Record>> getFeatureContent(ArrayList<Record> records,String featureName) //当属性多时，利用该方法得到属性名字集合
	{
		Map<String,ArrayList<Record>> map = new HashMap<String,ArrayList<Record>>();
		for(int i=0;i<records.size();i++)
		{
			if(map.get(records.get(i).getFeatureAtt(featureName)) == null)
			{
				ArrayList<Record> list = new ArrayList<Record>();
				list.add(records.get(i));
				map.put(records.get(i).getFeatureAtt(featureName), list);
			}
			else
			{
				ArrayList<Record> list = map.get(records.get(i).getFeatureAtt(featureName));
				list.add(records.get(i));
				map.put(records.get(i).getFeatureAtt(featureName), list);
			}
		}
		
		return map;
	}
	
	//检查该feature的value下是否还需要分裂
	public boolean validSplit(ArrayList<Record> records)
	{
		if(records.size() == 0) //分支无元素
		{
			System.out.println("records is null");
			return false;
		}
		int junior = 0, senior = 0;
		for(int i=0;i<records.size();i++)
		{
			if(records.get(i).getStatus().equals("senior"))
				senior++;
			if(records.get(i).getStatus().equals("junior"))
				junior++;
		}
		if(senior == 0 || junior == 0) //是否存在分支
			return false;
		else
			return true;
	}
	
	public double getInfo(ArrayList<Record> records) //计算熵
	{
		double info = 0;
		double seniorCount = 0;
		double juniorCount = 0;
		double total = 0;
		for(int i=0;i<records.size();i++)
		{
			if(records.get(i).getStatus().equals("senior"))
				seniorCount+= records.get(i).getCount();
			else
				juniorCount+= records.get(i).getCount();
		}

		total = seniorCount+juniorCount;
		
//		System.out.println("total: "+total);
//		System.out.println("seniorCount: "+seniorCount);
//		System.out.println("juniorCount: "+juniorCount);
		if(total == 0 || total == juniorCount || total == seniorCount)
		{
//			System.out.println("info: "+info);
//			System.out.println("------------------");
			return 0;
		}
		
		info = -(seniorCount/total*log(seniorCount/total,2)+juniorCount/total*log(juniorCount/total,2));
//		System.out.println("info: "+info);
//		System.out.println("------------------");
		return info;
	}
	
	public double getGain(ArrayList<Record> records,String type) // 计算信息增益
	{
		double gain = 0;
		Map<String,ArrayList<Record>> map = new HashMap<String,ArrayList<Record>>();
		double total = 0;
		gain = getInfo(records);
		if(type.equals("age")) //在分裂属性比较少时，可以直接穷举列名，当很多时，利用type匹配属性名字
		{
			for(int i=0;i<records.size();i++) //将不同的类别用map存储
			{
				if(map.get(records.get(i).getAge()) == null)
				{
					ArrayList<Record> list = new ArrayList<Record>();
					list.add(records.get(i));
					map.put(records.get(i).getAge(), list);
					total+= records.get(i).getCount();
				}
				else
				{
					ArrayList<Record> list = map.get(records.get(i).getAge());
					list.add(records.get(i));
					map.put(records.get(i).getAge(), list);
					total+= records.get(i).getCount();
				}
			}
		}
		else if(type.equals("department"))
		{
			for(int i=0;i<records.size();i++) //将不同的类别用map存储
			{
				if(map.get(records.get(i).getDepartment()) == null)
				{
					ArrayList<Record> list = new ArrayList<Record>();
					list.add(records.get(i));
					map.put(records.get(i).getDepartment(), list);
					total+= records.get(i).getCount();
				}
				else
				{
					ArrayList<Record> list = map.get(records.get(i).getDepartment());
					list.add(records.get(i));
					map.put(records.get(i).getDepartment(), list);
					total+= records.get(i).getCount();
				}
			}
		}
		else
		{
			for(int i=0;i<records.size();i++) //将不同的类别用map存储
			{
				if(map.get(records.get(i).getSalary()) == null)
				{
					ArrayList<Record> list = new ArrayList<Record>();
					list.add(records.get(i));
					map.put(records.get(i).getSalary(), list);
					total+= records.get(i).getCount();
				}
				else
				{
					ArrayList<Record> list = map.get(records.get(i).getSalary());
					list.add(records.get(i));
					map.put(records.get(i).getSalary(), list);
					total+= records.get(i).getCount();
				}
			}
			
		}
		
		for(String key : map.keySet()) //计算信息增益
		{
			double rCount = 0;
			for(int i=0;i<map.get(key).size();i++)
				rCount+=map.get(key).get(i).getCount();
//			System.out.println("rCount: "+rCount);
			gain = gain-rCount/total*getInfo(map.get(key));
		}
		System.out.println("gain: "+gain);
		return gain;
	}

	public ArrayList<Record> readData(String filename) //读取数据
	{
		ArrayList<Record> records = new ArrayList<Record>();

		Map<String, Integer> ageMap = new HashMap<String, Integer>(); // 保存不同age
		Map<String, Integer> salaryMap = new HashMap<String, Integer>();
		int i = 0, j = 0;

		File file = new File(filename);

		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String content = null;
			while ((content = reader.readLine()) != null)
			{

				String[] strings = content.split(" ");
				Record record = new Record();
				// record.department = strings[0];
				// record.status = strings[1];
				// record.age = strings[2];
				// record.salary = strings[3];
				// record.count = Integer.parseInt(strings[4]);

				record.setDepartment(strings[0]);
				record.setStatus(strings[1]);
				record.setAge(strings[2]);
				record.setSalary(strings[3]);
				record.setCount(Integer.parseInt(strings[4]));

				if (ageMap.containsKey(record.age))
					;
				else
				{
					ageMap.put(record.age, i);
					i++;
				}
				if (salaryMap.containsKey(record.salary))
					;
				else
				{
					salaryMap.put(record.salary, j);
					j++;
				}

				records.add(record);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		System.out.println("age的映射如下： ");
		for (String age : ageMap.keySet())
		{
			System.out.println("age: " + age + " ----> " + ageMap.get(age));
			for (int k = 0; k < records.size(); k++) // 将age --> age_cate
			{
				if (age.equals(records.get(k).getAge()))
					records.get(k).setAge_cate(ageMap.get(age));
			}
		}

		System.out.println("salary的映射如下： ");
		for (String salary : salaryMap.keySet())
		{
			System.out.println(
					"salary: " + salary + " ----> " + salaryMap.get(salary));
			for (int k = 0; k < records.size(); k++)
			{
				if (salary.equals(records.get(k).getSalary()))
					records.get(k).setSalary_cate(salaryMap.get(salary));
			}
		}

		return records;
	}

	
	
	static public double log(double value, double base)
	{
	   return Math.log(value) / Math.log(base);
	}
}
