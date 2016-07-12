package com.mirdar.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		
		File file = new File("F:/ML/fresh_comp_offline/tianchi_fresh_comp_train_item.csv");	
		BufferedReader reader = null;
		
		Class.forName("com.mysql.jdbc.Driver");
		 System.out.println("成功加载MySQL驱动！");
		String url = "jdbc:mysql://localhost:3306/tianchi_mobile_recommend";
		String user = "root";
		String pwd = "mirdar";
		Connection conn = DriverManager.getConnection(url, user, pwd);
		 System.out.println("成功连接到数据库！");
		Statement stmt = conn.createStatement();
		String sql = "select * from t_all_user where item_id='100002303'";
//		String rm = "delete from t_all_user";
		
		try {
			reader = new BufferedReader(new FileReader(file));

			String content = null;
			content = reader.readLine();
			while ((content = reader.readLine()) != null) 
			{

				String[] strings = content.split(",");
				String add = "insert into t_all_item values ("+strings[0]+","+strings[2]+")";
				stmt.executeUpdate(add);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("成功查询 ");
		while(rs.next())
		{
			System.out.print(rs.getString(1)+ " ");
			System.out.print(rs.getString(2)+ " ");
			System.out.print(rs.getInt(3)+ " ");
			System.out.print(rs.getString(4)+ " ");
			System.out.print(rs.getDate(5)+ " ");
		}
		
	}
}
