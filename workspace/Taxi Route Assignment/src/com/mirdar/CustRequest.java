package com.mirdar;

public class CustRequest {

	//�˿�ID
	public int custID;
	//����ʱ��
	double ts; 
	// s , e �ֱ��ʾ��ʼ�����յ�
//	public Location s;
//	public Location e;
	public Vector s;
	public Vector e;
	//int numble; ����
	public double profit;

	public void initial(Vector Rs,Vector Re)
	{
		s = Rs;
		e = Re;
		profit = Distance.distance2(s, e);
	}
	
	void getProfit()
	{
		//�õ��˿����������
		//��Ҫ����s��e�����·��
		//Ȼ���õ���*����
		
		
	}
}
