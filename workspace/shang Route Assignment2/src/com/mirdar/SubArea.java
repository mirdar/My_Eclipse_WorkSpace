package com.mirdar;

import java.util.ArrayList;

import com.mirdar.refinement.Cust;

public class SubArea {

	// 获得 4000<= x <= 6000 , 2000<= y <= 4000 的子区域路段
	public ArrayList<RouteSection> getSubArea(
			ArrayList<RouteSection> routeSections, double x1, double x2,
			double y1, double y2)
	{
		ArrayList<RouteSection> subRouteSection = new ArrayList<RouteSection>();
		for (int i = 0; i < routeSections.size(); i++)
		{
			if (routeSections.get(i).I1.x >= x1
					&& routeSections.get(i).I1.x <= x2
					&& routeSections.get(i).I1.y >= y1
					&& routeSections.get(i).I1.y <= y2)

				if (routeSections.get(i).I2.x >= x1
						&& routeSections.get(i).I2.x <= x2
						&& routeSections.get(i).I2.y >= y1
						&& routeSections.get(i).I2.y <= y2)

					subRouteSection.add(routeSections.get(i));
		}
		return subRouteSection;
	}

	public ArrayList<RouteSection> getSubArea2(ArrayList<RouteSection> routeSections, double r,
			Cust cust)
	{
		ArrayList<RouteSection> subRouteSection = new ArrayList<RouteSection>();
		for (int i = 0; i < routeSections.size(); i++)
		{
			if ((routeSections.get(i).I1.x - cust.vector.x)*(routeSections.get(i).I1.x - cust.vector.x)
				+(routeSections.get(i).I1.y - cust.vector.y)*(routeSections.get(i).I1.y - cust.vector.y) <=r
				&& (routeSections.get(i).I2.x - cust.vector.x)*(routeSections.get(i).I2.x - cust.vector.x)
				+(routeSections.get(i).I2.y - cust.vector.y)*(routeSections.get(i).I2.y - cust.vector.y) <=r)
					subRouteSection.add(routeSections.get(i));
		}
		return subRouteSection;
	}

	public ArrayList<Vector> getSubAreaV(ArrayList<Vector> vectors, double x1,
			double x2, double y1, double y2)
	{
		ArrayList<Vector> vectorList = new ArrayList<Vector>();
		for (int i = 0; i < vectors.size(); i++)
		{
			if (vectors.get(i).x > x1 && vectors.get(i).x < x2
					&& vectors.get(i).y > y1 && vectors.get(i).y < y2)
				vectorList.add(vectors.get(i));
		}

		return vectorList;

	}
}
