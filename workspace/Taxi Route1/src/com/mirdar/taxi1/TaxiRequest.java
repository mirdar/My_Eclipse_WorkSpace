package com.mirdar.taxi1;

/**
 * 
 * @author mirdar
 *
 */
public class TaxiRequest {

	int taxiID;
	//��ǰλ��
	Location s;
	//��ǰ����״̬���Ƿ����ˣ�0/1
	int status;
	//(����ʱ��) ֱ������Ϊ�Ӹ�λ�õ�custRequest��ʱ��
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
