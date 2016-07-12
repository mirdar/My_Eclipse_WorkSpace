package com.mirdar.GA;

public class Point {

	public String pointName;//配送点名字，与订单id对应
	public double lon;
	public double lan;
	public int pointId; //配送点编码
	public int goods_num; //商品件数
	public String order_id;
	public String getPointName()
	{
		return pointName;
	}
	public void setPointName(String pointName)
	{
		this.pointName = pointName;
	}
	public double getLon()
	{
		return lon;
	}
	public void setLon(double lon)
	{
		this.lon = lon;
	}
	public double getLan()
	{
		return lan;
	}
	public void setLan(double lan)
	{
		this.lan = lan;
	}
	public int getPointId()
	{
		return pointId;
	}
	public void setPointId(int pointId)
	{
		this.pointId = pointId;
	}
	public int getGoods_num()
	{
		return goods_num;
	}
	public void setGoods_num(int goods_num)
	{
		this.goods_num = goods_num;
	}
	
}
