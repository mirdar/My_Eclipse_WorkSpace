package com.mirdar.datastruct;

public class Record {

	// ����ÿ��pick_up/get_off�ļ�¼

	public int record_id; // ����Ψһ��ʶrecord
	public int grid_id; // ����id
	public double x; // ÿ����¼�ĺ����꣬������
	public double y;
	public int time; // ��¼��ʱ��
             public int flag = 0;  //pick up/get off 
             public int destRecordId = -1; //ֻ��pickup��¼���������������
}
