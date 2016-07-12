package com.mirdar.data.Strctures.disjointSets;

/*
 * 灵巧求并算法
 */
public class DisjSets_2 {

	private int[] s;
	public DisjSets_2(int numElements) //初始化
	{
		s = new int[numElements];
		for(int i=0;i<s.length;i++)
		{
			s[i] = -1;
		}
	}
	//按高度合并，将浅的合并到深的中，树最高logN，其中s[i]表示对应树的高度的负值-1
	public void union(int root1,int root2) //将root2归属到root1类别中，指定树合并
	{
		if(s[root2]<s[root1])
		{
			s[root1] = root2;
		}
		else
		{
			if(s[root2] == s[root1])
			{
				s[root1]--; //只有两个合并的树同样高的时候才会使得高度增加
			}
			s[root2] = root1;
		}
	}
	//按大小合并，将小的合并到大的中去，树最高logN，s[i]表示对应树的大小-2
	public void union_by_size(int root1,int root2) //将root2归属到root1类别中，指定树合并
	{
		if(s[root2]<=s[root1])
		{
			s[root1]+=s[root2]+2; //先更新树的大小
			s[root2] = root1;
		}
		else
		{
			s[root2]+=s[root1]+2;
			s[root1] = root2;
		}
	}
	
	public int find(int x) //返回x所属的类别
	{
		if(s[x] < 0)
			return x;
		else
			return find(s[x]);
	}
}
