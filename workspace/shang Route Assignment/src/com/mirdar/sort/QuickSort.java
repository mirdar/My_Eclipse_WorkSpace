package com.mirdar.sort;
import java.util.ArrayList;

import com.mirdar.CustRequest;

public class QuickSort {
	

	public void quickSort(ArrayList<CustRequest> custs,int p,int r)
	{
		if(p < r)
		{
			int q = partition(custs, p, r);
			quickSort(custs, p, q-1);
			quickSort(custs, q+1, r);
		}
	}
	public int partition(ArrayList<CustRequest> custs,int m,int n)
	{
		CustRequest cust = custs.get(m);
		int i = m;
		for(int j=m+1;j<=n;j++)
		{
			if(custs.get(j).profit >= cust.profit)
			{
				i = i+1;
				CustRequest temp = custs.get(i);
				custs.set(i, custs.get(j));
				custs.set(j, temp);
			}
		}
		CustRequest temp = custs.get(m);
		custs.set(m, custs.get(i));
		custs.set(i, temp);
		
		return i;
	}
}
