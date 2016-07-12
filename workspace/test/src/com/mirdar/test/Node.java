package com.mirdar.test;

public class Node {

	Node leftNode;
	Node rightNode;
	double x;
	double y;
	//是否是叶子节点
	boolean isLeaf;
	
	
	public Node getLeftNode()
	{
		return leftNode;
	}
	public void setLeftNode(Node leftNode)
	{
		this.leftNode = leftNode;
	}
	public Node getRightNode()
	{
		return rightNode;
	}
	public void setRightNode(Node rightNode)
	{
		this.rightNode = rightNode;
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
	
	
}
