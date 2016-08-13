package cn.zhaoyuening.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import cn.zhaoyuening.utils.sql.JdbcUtils;

public class JdbcUtilsDemo {
	public static void main(String[] args) throws SQLException {
		//获取数据库连接池
		DataSource ds = JdbcUtils.getDataSource();
		//获取连接对象
		Connection conn= ds.getConnection();
		ResultSet set = conn.createStatement().executeQuery("select * from persion");
		while(set.next()){
			String name = set.getString("name");
			String age = set.getString("age");
			int gender= set.getInt("gender");
			System.out.println("name :"+name+"  age:"+age+" gender:"+(gender==1?"男":"女"));
		}
		conn.close();
	}
}
