package com.mirdar.data.Strctures.disjointSets;

/*
 * 路径压缩在于find/union过程中，union操作很难减少复杂度，所以从find中出发
 * 每次操作find(x)都使其父节点称为该树的根
 */
public class path_compress {
	public int find(int x)
	{
		if(s[x] < 0)
			return x;
		else
			return s[x] = find(s[x]); //查询后就更新了
	}
	private int[] s;
}
