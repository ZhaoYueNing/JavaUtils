package cn.zhaoyuening.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import cn.zhaoyuening.utils.sql.JdbcUtils;

public class JdbcUtilsDemo {
	public static void main(String[] args) throws SQLException {
		//��ȡ���ݿ����ӳ�
		DataSource ds = JdbcUtils.getDataSource();
		//��ȡ���Ӷ���
		Connection conn= ds.getConnection();
		ResultSet set = conn.createStatement().executeQuery("select * from persion");
		while(set.next()){
			String name = set.getString("name");
			String age = set.getString("age");
			int gender= set.getInt("gender");
			System.out.println("name :"+name+"  age:"+age+" gender:"+(gender==1?"��":"Ů"));
		}
		conn.close();
	}
}
