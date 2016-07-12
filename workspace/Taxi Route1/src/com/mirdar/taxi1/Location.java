package com.mirdar.taxi1;

public class Location {
	
	//Rʱ��·�����е�һ��·�Σ�����R1=(I1,I2),��·����G=(I,R)
	RouteSection R;
	//R·���������������һ��
	Vector I;
	//rateʱR·����I��ľ�����R���ȵı���
	double rate;
	//��γ�ȣ�����Location����õ�
	double x;
	double y;
	
	public Location(RouteSection R)
	{
		this.R = R;
		this.initial();
	}
	
	void initial()
	{
		double a = Math.random();
		rate = Math.random();
		if(a <= 0.5)
		{
			this.I = R.I1;
			this.x = I.x + (R.I2.x - I.x)*rate;
			this.y = I.y + (R.I2.y - I.y)*rate;
		}
		else
		{
			this.I = R.I2;
			this.x = I.x + (R.I1.x - I.x)*rate;
			this.y = I.y + (R.I1.y - I.y)*rate;
		}
		
	}
}
