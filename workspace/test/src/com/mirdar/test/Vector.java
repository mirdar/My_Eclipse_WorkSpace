package com.mirdar.test;

public class Vector {

	double x;
	double y;
	Vector leftVector;
	Vector rightVector;
	//是否是叶子节点
	boolean isLeaf;
	public Vector getLeftVector()
	{
		return leftVector;
	}
	public void setLeftVector(Vector leftVector)
	{
		this.leftVector = leftVector;
	}
	public Vector getRightVector()
	{
		return rightVector;
	}
	public void setRightVector(Vector rightVector)
	{
		this.rightVector = rightVector;
	}
	public double getX()
	{
		return x;
	}
	public void setX(double x)
	{
		this.x = x;
	}
	public double getY()
	{
		return y;
	}
	public void setY(double y)
	{
		this.y = y;
	}
	public void vprint()
	{
		System.out.println("( "+x + "," + y + " )");
	}
}
