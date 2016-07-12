package com.mirdar.data.Strctures.disjointSets;

/*
 * 不相交集的基本数据结构，用s[i]=i，初始情况s[i]=-1，其中i表示根，初始都属于不同的类i中
 * 应用：用作迷宫生成
 */
public class DisjSets {

	private int[] s;
	public DisjSets(int numElements) //初始化
	{
		s = new int[numElements];
		for(int i=0;i<s.length;i++)
		{
			s[i] = -1;
		}
	}
	
	public void union(int root1,int root2) //将root2归属到root1类别中，指定树合并
	{
		s[root2] = root1;
	}
	
	public int find(int x) //返回x所属的类别
	{
		if(s[x] < 0)
			return x;
		else
			return find(s[x]);
	}
}
