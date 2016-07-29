package com.mirdar.datastruct;

public class Record {

	// 这是每个pick_up/get_off的记录

	public int record_id; // 用来唯一标识record
	public int grid_id; // 格子id
	public double x; // 每条记录的横坐标，纵坐标
	public double y;
	public int time; // 记录的时间
             public int flag = 0;  //pick up/get off 
             public int destRecordId = -1; //只有pickup记录，这变量才有意义
}
