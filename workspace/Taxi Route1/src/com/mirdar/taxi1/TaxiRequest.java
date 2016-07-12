package com.mirdar.taxi1;

/**
 * 
 * @author mirdar
 *
 */
public class TaxiRequest {

	int taxiID;
	//当前位置
	Location s;
	//当前车辆状态，是否有人，0/1
	int status;
	//(请求时间) 直接设置为从该位置到custRequest的时间
	double t;
	double profit;
	
	public void setLocation(RouteSection Rs)
	{
		s = new Location(Rs);
	}
	public void update(CustRequest custRequest)
	{
		profit = profit + custRequest.profit;
	}
}
