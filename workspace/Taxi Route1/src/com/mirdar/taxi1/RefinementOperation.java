package com.mirdar.taxi1;

//��������
public class RefinementOperation {

	public double refinement(double t0,double dis,double speed,double constant,double profitDiff)
	{
		double r = 0;
		//��һ�ξ������������̾��룬���еڶ���ҡ��r�ķ�Χ���ҵ�profit��С��taxi���ڵĵط���r'
		r = speed*(t0 + Math.log(1+profitDiff/constant));
		
		return r;
	}
}
