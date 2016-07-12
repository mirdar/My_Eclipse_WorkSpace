package com.mirdar.test;

import com.mirdar.O2O.ReadData;

public class Valid {

	public static void main(String[] args)
	{
		String fileShop = "F:\\ML\\last mile delivery/shop.csv";
		String fileSpot = "F:\\ML\\last mile delivery/spot.csv";
		String fileSite = "F:\\ML\\last mile delivery/site.csv";
		String fileResult = "F:\\ML\\last mile delivery/baseResult.csv";
		ReadData readData = new ReadData();
		
		Test test = new Test();
		test.placeMap = readData.readPlace(fileSite,test.placeMap);
		test.placeMap = readData.readPlace(fileSpot,test.placeMap);
		test.placeMap = readData.readPlace(fileShop,test.placeMap);
		test.records = readData.readRRecord(fileResult);
		
		System.out.println("placeSize: "+test.placeMap.size());
		System.out.println("records.size : "+test.records.size());
//		if(!test.evaluation(test.records))
//			System.out.println("验证失败，数据中存在问题");;
	}
	
}
