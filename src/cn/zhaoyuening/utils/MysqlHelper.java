
package cn.zhaoyuening.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * mysql帮助器
 * 帮助简单连接mysql数据库
 * @author buynow
 *
 */
public class MysqlHelper {
	private String username;
	private String password;
	private final String MYSQL_INIT = "com.mysql.jdbc.Driver";
	private String  connUrl;
	private int port;
	private Connection conn;
	
	public MysqlHelper(String username, String password, String host,
			int port,String databaseName) {
		this.username = username;
		this.password = password;
		this.connUrl = "jdbc:mysql://"+host+":"+port+"/"+databaseName;
		this.port = port;
		try {
			Class.forName(MYSQL_INIT);
			conn=getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public MysqlHelper(String username, String password,String databaseName) {
		this(username,password,"127.0.0.1",3306,databaseName);
	}
	
	public Connection getConnection(){
		try {
			if(this.conn==null||this.conn.isClosed()){
				this.conn = DriverManager.getConnection(connUrl, username, password);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return this.conn;			
		}
	}
	
	public static void main(String[] args) {
		try {
			MysqlHelper helper = new MysqlHelper("root", "20080808", "buynow");
			Connection conn = helper.getConnection();
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery("select *   from commodity where id=1");
			while(set.next()){
				String name = set.getString("name");
				System.out.println(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}