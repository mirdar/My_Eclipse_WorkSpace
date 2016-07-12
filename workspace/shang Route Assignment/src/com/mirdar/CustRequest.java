package com.mirdar;

public class CustRequest {

	//顾客ID
	public int custID;
	//请求时间
	double ts; 
	// s , e 分别表示起始点与终点
//	public Location s;
//	public Location e;
	public Vector s;
	public Vector e;
	//int numble; 人数
	public double profit;

	public void initial(Vector Rs,Vector Re)
	{
		s = Rs;
		e = Re;
		profit = Distance.distance2(s, e);
	}
	
	void getProfit()
	{
		//得到顾客请求的利润
		//需要构造s到e的最短路径
		//然后用单价*距离
		
		
	}
}
