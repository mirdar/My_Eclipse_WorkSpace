package com.mirdar;

/**
 * 
 * @author mirdar
 *
 */
public class TaxiRequest {

	public int taxiID;
	public Vector s;
	//当前位置
//	public Location s;
	//当前车辆状态，是否有人，0/1
	int status;
	//(请求时间) 直接设置为从该位置到custRequest的时间
	double t;
	public double profit;
	public double profitDirect;
	public double profitViolent;
	
	public void setLocation(Vector Rs)
	{
		s = Rs;
	}
	public void update(CustRequest custRequest)
	{
		profit = profit + custRequest.profit;
	}
	
	public void clear()
	{
		profit = 0;
		profitDirect = 0;
		profitViolent = 0;
	}
}
