package com.mirdar;

/**
 * 
 * @author mirdar
 *
 */
public class TaxiRequest {

	public int taxiID;
	public Vector s;
	//��ǰλ��
//	public Location s;
	//��ǰ����״̬���Ƿ����ˣ�0/1
	int status;
	//(����ʱ��) ֱ������Ϊ�Ӹ�λ�õ�custRequest��ʱ��
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
