package com.mirdar.taxi1;

public class CustRequest {

	//�˿�ID
	int custID;
	//����ʱ��
	double ts; 
	// s , e �ֱ��ʾ��ʼ�����յ�
	Location s;
	Location e;
	//int numble; ����
	double profit;

	public void initial(RouteSection Rs,RouteSection Re)
	{
		s = new Location(Rs);
		e = new Location(Re);
		profit = Distance.distance(s, e);
	}
	
	void getProfit()
	{
		//�õ��˿����������
		//��Ҫ����s��e�����·��
		//Ȼ���õ���*����
		
		
	}
}
