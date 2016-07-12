package com.mirdar.O2O;

public class Order {

	String shop_id;
	String spot_id;
	String order_id;
	int pickup_time;
	int delivery_time;
	int num;
	int time;
	int stay_time;
	int last_time;
	int flag = 1; //用来记录是否被配送过，1表示还未配送，而0表示已经被配送
}
