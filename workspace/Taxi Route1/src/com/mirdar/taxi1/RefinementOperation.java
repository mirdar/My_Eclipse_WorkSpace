package com.mirdar.taxi1;

//精化操作
public class RefinementOperation {

	public double refinement(double t0,double dis,double speed,double constant,double profitDiff)
	{
		double r = 0;
		//第一次精化操作，缩短距离，还有第二次摇在r的范围内找到profit最小的taxi所在的地方的r'
		r = speed*(t0 + Math.log(1+profitDiff/constant));
		
		return r;
	}
}
