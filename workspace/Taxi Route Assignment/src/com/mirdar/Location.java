package com.mirdar;

public class Location {
	
	//Rʱ��·�����е�һ��·�Σ�����R1=(I1,I2),��·����G=(I,R)
	RouteSection R;
	//R·���������������һ��
	public Vector I;
	//rateʱR·����I��ľ�����R���ȵı���
	double rate;
	//��γ�ȣ�����Location����õ�
	double x;
	double y;
	
	public Location(RouteSection R)
	{
		this.R = R;
		initial();
	}
	
	void initial()
	{
		double x = Math.random();
		if(x <= 0.5)
		{
			this.I = R.I1;
		}
		else
		{
			this.I = R.I2;
		}
		rate = Math.random();
		
		x = I.x + (R.I2.x - I.x)*rate;
		y = I.y + (R.I2.y - I.y)*rate;
	}
}
