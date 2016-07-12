package com.mirdar.classifer;

public class Record {

	public String department;
	public String status;
	public String age;
	public String salary;
	public int count;
	
	
	public int age_cate; // Ó³Éä±äÁ¿
	public int salary_cate;
	
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getAge()
	{
		return age;
	}
	public void setAge(String age)
	{
		this.age = age;
	}
	public String getSalary()
	{
		return salary;
	}
	public void setSalary(String salary)
	{
		this.salary = salary;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	public int getAge_cate()
	{
		return age_cate;
	}
	public void setAge_cate(int age_cate)
	{
		this.age_cate = age_cate;
	}
	public int getSalary_cate()
	{
		return salary_cate;
	}
	public void setSalary_cate(int salary_cate)
	{
		this.salary_cate = salary_cate;
	}
	
	public String getFeatureAtt(String featureName)
	{
		if(featureName.equals("department"))
		{
			return getDepartment();
		}
		if(featureName.equals("age"))
		{
			return getAge();
		}
		return getSalary();
	}
	
	
}
