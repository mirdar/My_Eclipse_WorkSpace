package com.mirdar.dataDeal;

public class Merge {

    //这个是第二次连接
    public ArrayList<Cluster> getCluster(ArrayList<Cluster> clusters,double mergeThreshold)
    {
        ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
        for(int i=0;i<clusters.size();i++)
        {
            if(clusters.get(i).flag == 0)
            {
                for(int j=i+1;j<clusters.size();j++)
                {
                 if(clusters.get(j).flag == 0 && disCluster2(clusters.get(i),clusters.get(j)) <= mergeThreshold)
                    {
                        mergeCluster(clusters.get(i),clusters.get(j));
                        clusters.get(j).flag = 1;
                    }
                 }
                clusters.get(i).flag = 2;
                clusters.get(i).setXY();
                clusterList.add(clusters.get(i));
            }
        }

        return clusterList;
    }


    //这个是第一次连接
    public ArrayList<Cluster> getInitCluster(Map<Integer,Grid> gridMap,double mergeThreshold)
    {
        ArrayList<Cluster> clusters = new ArrayList<Cluster>();

        for(String key : gridMap.keySet())
        {
            if(gridMap.grids(key).flag  == 1)
            {
                Cluster cluster = new Cluster();
                cluster.grids.add(cluster);
                clusters.add(cluster);
            }
        }

        ArrayList<Cluster> clusterList = new ArrayList<Cluster>();
        for(int i=0;i<clusters.size();i++)
        {
            if(clusters.get(i).flag == 0)
            {
                for(int j=i+1;j<clusters.size();j++)
                {
                 if(clusters.get(j).flag == 0 && disCluster(clusters.get(i),clusters.get(j)) <= mergeThreshold)
                    {
                        mergeCluster(clusters.get(i),clusters.get(j));
                        clusters.get(j).flag = 1;
                    }
                 }
                clusters.get(i).flag = 2;
                clusters.get(i).setXY();
                clusterList.add(clusters.get(i));
            }
        }
         
        for(int i=0;i<clusterList.size();i++)
        {
            clusterList.get(i).flag =  0;
        }

        return clusterList;

    }

    public double disCluster(Cluster clusterA,Cluster clusterB)
    {
            double  minDis = Double.MaxValue;
            for(int i=0;i<clusterA.grids.size();i++)
            {
                for(int j=0;j<clusterB.grids.size();j++)
                {
                    if(disGrid(clusterA.get(i),clusterB.get(j)) < minDis)
                    {
                        minDis = disGrid(clusterA.get(i),clusterB.get(j));
                    }
                }
            }

            return minDis;
    }

    public double disCluster2(Cluster clusterA,Cluster clusterB)
    {
        return Math.sqrt(Math.pow(clusterA.x - clusterB.x)+Math.pow(clusterA.y - clusterB.y));
    }

    public double disGrid(Grid grid1,Grid grid2)
    {
        return Math.sqrt(Math.pow(grid1.x-grid2.x)+Math.pow(grid1.y-grid2.y));
    }

    public void mergeCluster(Cluster clusterA,Cluster clusterB)
    {
        for(int i=0;i<clusterB.grids.size();i++)
        {
            clusterA.grids.add(clusterB.grids.get(i));
        }
    }
}
