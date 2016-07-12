package com.mirdar.frequent_pattern;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Apriori {

	int minSup;
	private static List<String> data;
	private static List<Set<String>> dataSet;
	public Map<String, Integer> ordermap = new TreeMap<String, Integer>();
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
//		String filename = "C:/Users/zpp/Desktop/datamining/AdultSample2.csv"; //data1
			String filename = "C:/Users/zpp/Desktop/datamining/titanic.csv"; //data2
//			String filename = "C:/Users/zpp/Desktop/datamining/Groceries.csv"; //data3
		long startTime = System.currentTimeMillis();
		Apriori apriori = new Apriori();

		// ������С֧�ֶ�
		apriori.setMinSup(1000);
		// �������ݼ�
		data = apriori.buildData(filename);

		// ����Ƶ��1�
		List<Set<String>> f1Set = apriori.findF1Items(data);
		apriori.printSet(f1Set, 1);
		List<Set<String>> result = f1Set;

		int i = 2;
		do
		{

			result = apriori.arioriGen(result);
//			apriori.printSet(result, i);
			i++;
		} while (result.size() != 0);
		long endTime = System.currentTimeMillis();
		System.out.println("����ʱ��" + (endTime - startTime) + "ms");
		
		
		System.out.println("Ƶ�����");
		File writename = new File(
				"C:/Users/zpp/Desktop/datamining/Apriori_output_1.txt"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
		writename.createNewFile(); // �������ļ�
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		out.write("��ʱ�� "+(endTime - startTime) + "ms"+"\r\n");
		out.write("������С֧�ֶ�Ϊ"+apriori.minSup+"Ƶ�������"+apriori.ordermap.size()+"\r\n");
		for (String name : apriori.ordermap.keySet())
		{
//			System.out.println(name + " " + apriori.ordermap.get(name));
			out.write(name + " " + apriori.ordermap.get(name) + "\r\n");
		}
		out.flush(); // �ѻ���������ѹ���ļ�
		out.close(); // ���ǵùر��ļ�
		
		HSSFWorkbook wb = new HSSFWorkbook();
		//�����µ�sheet����excel�ı���
		HSSFSheet sheet=wb.createSheet("Apriori_experiment");
		//��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
		HSSFRow row1=sheet.createRow(0);
		//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
//		HSSFCell cell=row1.createCell(0);
			row1.createCell(0).setCellValue("support");
			row1.createCell(1).setCellValue("runtime");
			row1.createCell(2).setCellValue("frequent_set_numble");
		int xls = 1;
		for (int j = 20; j <= 500; j=j+50)
		{
			long s = System.currentTimeMillis();
			Apriori apriori1 = new Apriori();
			// ������С֧�ֶ�
			apriori1.setMinSup(j);
			// �������ݼ�
			data = apriori1.buildData(filename);

			// ����Ƶ��1�
			List<Set<String>> f1Set1 = apriori1.findF1Items(data);
			apriori.printSet(f1Set1, 1);
			List<Set<String>> result1 = f1Set1;
			do
			{

				result1 = apriori1.arioriGen(result1);
//				apriori.printSet(result, i);
			} while (result1.size() != 0);
			long e = System.currentTimeMillis();
			System.out.println("����ʱ��" + (e - s) + "ms");
			HSSFRow row=sheet.createRow(xls);
			//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
//			HSSFCell cell=row1.createCell(0);
				row.createCell(0).setCellValue(apriori1.minSup);
				row.createCell(1).setCellValue((e - s));
				row.createCell(2).setCellValue(apriori1.ordermap.size());
			xls++;
		}
		OutputStream output=new FileOutputStream("C:/Users/zpp/Desktop/datamining/Apriori_experiment_Titanic.xls");
	    wb.write(output);
		output.close();
		wb.close();
		
	}

	public void setMinSup(int minSup)
	{
		this.minSup = minSup;
	}

	/**
	 * ����ԭʼ���ݼ�������Ϊ֮�ṩ������Ҳ���Բ��ṩ ������ṩ��������������Ĭ�Ϲ�������ݼ��� ����ṩ����Ϊ�ļ�������ʹ���ļ��е����ݼ�
	 * 
	 * @return
	 */
	List<String> buildData(String fileName)
	{
		List<String> data = new ArrayList<String>();
		File file = new File(fileName);
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				data.add(line);
			}

		} catch (FileNotFoundException e)
		{

			e.printStackTrace();
		} catch (IOException e)
		{

			e.printStackTrace();
		}
		dataSet = new ArrayList<Set<String>>();
		Set<String> dSet;
		for (String d : data)
		{
			dSet = new TreeSet<String>();
//			String[] dArr = d.split(" ");
			String[] dArr = d.split(",");
			for (String str : dArr)
			{
				dSet.add(str);
			}
			dataSet.add(dSet);
		}

		return data;
	}

	/**
	 * �ҳ���ѡ1�
	 * 
	 * @param data
	 * @return
	 */
	List<Set<String>> findF1Items(List<String> data)
	{

		List<Set<String>> result = new ArrayList<Set<String>>();
		Map<String, Integer> dc = new HashMap<String, Integer>();
		for (String d : data)
		{
//			String[] items = d.split(" ");
			String[] items = d.split(",");
			for (String item : items)
			{
				if (dc.containsKey(item))
				{
					dc.put(item, dc.get(item) + 1);
				} else
				{
					dc.put(item, 1);
				}
			}
		}
		Set<String> itemKeys = dc.keySet();
		// ����treeSet�洢�����Զ�����
		Set<String> tempKeys = new TreeSet<String>();
		for (String str : itemKeys)
		{
			tempKeys.add(str);

		}
		
		for (String item : tempKeys)
		{
			if (dc.get(item) >= minSup)
			{
				Set<String> f1Set = new TreeSet<String>();
				f1Set.add(item);
				result.add(f1Set);
				ordermap.put(item, dc.get(item));
			}
		}

		return result;
	}

	/**
	 * ����arioriGen������k-1�����k�
	 * 
	 * @param preSet
	 * @return
	 */
	List<Set<String>> arioriGen(List<Set<String>> preSet)
	{

		List<Set<String>> result = new ArrayList<Set<String>>();
		int preSetSize = preSet.size();
		for (int i = 0; i < preSetSize - 1; i++)
		{
			for (int j = i + 1; j < preSetSize; j++)
			{
				String[] strA1 = preSet.get(i).toArray(new String[0]);
				String[] strA2 = preSet.get(j).toArray(new String[0]);
				if (isCanLink(strA1, strA2))
				{ // �ж�����k-1��Ƿ�������ӳ�k�������
					Set<String> set = new TreeSet<String>();
					for (String str : strA1)
					{
						set.add(str);
					}
					set.add((String) strA2[strA2.length - 1]); // ���ӳ�k�
					// �ж�k��Ƿ���Ҫ���е����������Ҫ��cut��������뵽k��б���
					if (!isNeedCut(preSet, set))
					{

						result.add(set);
					}
				}

			}
		}
		return checkSupport(result); // ����֧�ֶȼ��
	}

	/**
	 * ��set�е�����������Ƚϲ����м��㣬���֧�ֶȴ���Ҫ����
	 * 
	 * @param set
	 * @return
	 */
	List<Set<String>> checkSupport(List<Set<String>> setList)
	{

		List<Set<String>> result = new ArrayList<Set<String>>();
		boolean flag = true;
		int[] counter = new int[setList.size()];
		for (int i = 0; i < setList.size(); i++)
		{
			for (Set<String> dSets : dataSet)
			{
				// �����ȸü��ϴ�СҪС������
				if (setList.get(i).size() > dSets.size())
				{
					flag = true;

				} else // ���ĳ����������ü��ϣ���counter[i]++
				{

					for (String str : setList.get(i))
					{
						if (!dSets.contains(str))
						{
							flag = false;
							break;
						}
					}
					if (flag)
					{
						counter[i] += 1;
					} else
					{
						flag = true;
					}
				}
			}
		}

		for (int i = 0; i < setList.size(); i++)
		{
			if (counter[i] >= minSup)
			{
				result.add(setList.get(i));
				String[] a = setList.get(i).toArray(new String[0]);
				String b = "";
				for (int k = 0; k < a.length; k++)
					b = b + a[k] + ",";
				ordermap.put(b, counter[i]);
			}
		}

		return result;
	}

	/**
	 * �ж���������ܷ�ִ�����Ӳ���
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	boolean isCanLink(String[] s1, String[] s2)
	{

		boolean flag = true;
		if (s1.length == s2.length)
		{
			for (int i = 0; i < s1.length - 1; i++)
			{
				if (!s1[i].equals(s2[i]))
				{
					flag = false;
					break;
				}
			}
			if (s1[s1.length - 1].equals(s2[s2.length - 1]))
			{
				flag = false;
			}
		} else
		{
			flag = false;
		}

		return flag;
	}

	/**
	 * �ж�set�Ƿ���Ҫ��cut
	 * 
	 * @param setList
	 * @param set
	 * @return
	 */
	boolean isNeedCut(List<Set<String>> setList, Set<String> set)
	{
		boolean flag = false;
		List<Set<String>> subSets = getSubset(set); // ���k�������k-1�
		for (Set<String> subSet : subSets)
		{
			// �жϵ�ǰ��k-1�set�Ƿ���Ƶ��k-1��г��֣�����֣�����Ҫcut
			// ��û�г��֣�����Ҫ��cut
			if (!isContained(setList, subSet))
			{
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * �ж�k���ĳk-1��Ƿ������Ƶ��k-1��б���
	 * 
	 * @param setList
	 * @param set
	 * @return
	 */
	boolean isContained(List<Set<String>> setList, Set<String> set)
	{

		boolean flag = false;
		int position = 0;
		for (Set<String> s : setList)
		{

			String[] sArr = s.toArray(new String[0]);
			String[] setArr = set.toArray(new String[0]);
			for (int i = 0; i < sArr.length; i++)
			{
				if (sArr[i].equals(setArr[i]))
				{ // �����Ӧλ�õ�Ԫ����ͬ����positionΪ��ǰλ�õ�ֵ
					position = i;
				} else
				{
					break;
				}
			}
			// ���position����������ĳ��ȣ�˵�����ҵ�ĳ��setList�еļ�����
			// set������ͬ�ˣ��˳�ѭ�������ذ���
			// ���򣬰�position��Ϊ0������һ���Ƚ�
			if (position == sArr.length - 1)
			{
				flag = true;
				break;
			} else
			{
				flag = false;
				position = 0;
			}

		}
		return flag;
	}

	/**
	 * ���k�������k-1�
	 * 
	 * @param set
	 * @return
	 */
	List<Set<String>> getSubset(Set<String> set)
	{

		List<Set<String>> result = new ArrayList<Set<String>>();
		String[] setArr = set.toArray(new String[0]);
		for (int i = 0; i < setArr.length; i++)
		{
			Set<String> subSet = new TreeSet<String>();
			for (int j = 0; j < setArr.length; j++)
			{
				if (i != j)
				{
					subSet.add((String) setArr[j]);
				}
			}
			result.add(subSet);
		}
		return result;
	}

	void printSet(List<Set<String>> setList, int i)
	{
		System.out.print("Ƶ��" + i + "��� ��" + setList.size() + "�{");
		for (Set<String> set : setList)
		{
			System.out.print("[ ");
			for (String str : set)
			{
				System.out.print(str + " ");
			}
			System.out.print("], ");
		}
		System.out.println("}");
	}

}
