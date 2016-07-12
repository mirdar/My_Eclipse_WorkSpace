package com.mirdar.data.Strctures.interestingDemo;

public class Demo {

	public static void main(String[] args)
	{
//		printOut(76234); // Demo print Interger
		Double[] x= new Double[2];
//		x[1] = 0.;
		f(x);
		System.out.println(x[0]);
//		Long x = 1L;
//		System.out.println(Integer.valueOf(String.valueOf(x)));
	}
	
	//Demo print Interger 依次打印出一个整数
	public static void printOut(int n){
		if(n >= 10)
			printOut(n/10);
		printDigit(n%10);
	}
	static void printDigit(int n){
		System.out.println(n);
	}
	
	public static void f(Double[] x){
		x[0] = 1.0;
		x[1] = 1.0;
	}
	
}
