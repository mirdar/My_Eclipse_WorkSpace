/*
*初始化grid
 */
import java.util.*;

public class GridInit {
   
   public Map<Integer,Grid> gridInit(double xBound,double yBound)
   {
        Map<Integer,Grid> gridMap = HashMap<Integer,Grid>();
        for(int i=0;i<Math.ceil(yBound/10);i++)
        {
            for(int j=0;j<Math.ceil(xBound/10);j++)
            {
                Grid gird = new Grid( );
                grid.grid_id = i*Math.ceil(xBound/10)+j;
                grid.x = (j*10+(j+1)*10)/2.0;
                grid.y = (i*10+(i+1)*10)/2.0;
                gridMap.put(grid.grid_id,grid);
            }
        }

        return gridMap;
   }
   //将records数据映射到grid中
   public Map<Integer,Grid> gridInit(double xBound,double yBound,ArrayList<Record> records)
   {
        Map<Integer,Grid> gridMap = gridInit(xBound,yBound );
        for(int i=0;i<records.size();i++)
        {
            if(records.get(i).flag == 1)
                gridMap.get(records.get(i).grid_id).pickup_records.add(records.get(i));
            else
                gridMap.get(records.get(i).grid_id).getoff_records.add(records.get(i));
        }

        return gridMap;
   }

   //将Grid进行标记是否是hotGrid
   public Map<Integer,Grid> setHotGrid(Map<Integer,Grid> gridMap,int threshold)
   {
        Map<Integer,Grid> gridMap2 = new Map<Integer,Grid>();
        for(String key : gridMap.keySet())
        {
            if(gridMap.get(key).setHotGrid(threshold) == 1)
            {
                gridMap2.put(key,gridMap.get(key));
            }
        }

        return griidMap2;
   }
   //设置grid的CD数量
   public void setCon(Map<Integer,Grid> gridMap)
   {
        //对于每个hot grid的CD在0-8之间，所以当grid在边界的最大为5，边界grid可以根据其x,y的坐标判断
        for(Integer grid_id : gridMap.keySet())
        {
            if(gridMap.get(grid_id).y*2 == 10 )
            {
                if(gridMap.get(grid_id).x*2 == 10)
                {
                    if(gridMap.get(grid_id+1) != null )
                        gridMap.get(grid_id).connectdegree++;
                    if(gridMap.get(grid_id+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                }
                else if(gridMap.get(grid_id).x> Math.floor(xBound/10.0)*10 )
                {
                     if(gridMap.get(grid_id-1) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                       if(gridMap.get(grid_id-1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                }
                else
                {
                     if(gridMap.get(grid_id-1) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+1) != null )
                        gridMap.get(grid_id).connectdegree++;
                    if(gridMap.get(grid_id+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                    if(gridMap.get(grid_id-1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                }
            }
            else if(gridMap.get(grid_id).x*2 == 10)
            {
                if(gridMap.get(grid_id).y > Math.floor(yBound/10.0)*10)
                {
                     if(gridMap.get(grid_id+1) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                }
                else
                {
                     if(gridMap.get(grid_id+1) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                }
            }
           else if(gridMap.get(grid_id).x> Math.floor(xBound/10.0)*10 )
            {
                 if(gridMap.get(grid_id).y > Math.floor(yBound/10.0)*10)
                 {
                    if(gridMap.get(grid_id-1) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id-1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                 }
                 else
                 {
                    if(gridMap.get(grid_id-1) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id-1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                     if(gridMap.get(grid_id-1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                 }
            }
            else if(gridMap.get(grid_id).y > Math.floor(yBound/10.0)*10)
            {
                if(gridMap.get(grid_id-1) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id+1) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id-1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id+1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
            }
            else
            {
                if(gridMap.get(grid_id-1) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id+1) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id-1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id+1-Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id-1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
                if(gridMap.get(grid_id+1+Math.ceil(xBound/10.0)) != null )
                        gridMap.get(grid_id).connectdegree++;
            }
        }
   }

}