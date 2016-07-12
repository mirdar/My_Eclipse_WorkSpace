package com.mirdar.taxi1;

public class CustRequest {

	//顾客ID
	int custID;
	//请求时间
	double ts; 
	// s , e 分别表示起始点与终点
	Location s;
	Location e;
	//int numble; 人数
	double profit;

	public void initial(RouteSection Rs,RouteSection Re)
	{
		s = new Location(Rs);
		e = new Location(Re);
		profit = Distance.distance(s, e);
	}
	
	void getProfit()
	{
		//得到顾客请求的利润
		//需要构造s到e的最短路径
		//然后用单价*距离
		
		
	}
}
