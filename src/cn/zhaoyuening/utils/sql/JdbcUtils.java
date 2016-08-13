package cn.zhaoyuening.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
/**
 * <b>jdbc开发工具类</b><br>
 * 快速链接数据库
 * @author Zhao
 * <hr>
 * 应该在代码src目录下写配置文件，保存数据库配置。<br>
 * 文件名：jdbc.properties<br>
 * 文件格式<br>
 * driver=com.mysql.jdbc.Driver<br>
 * url=jdbc:mysql:///testdb<br>
 * username=xxxx<br>
 * password=xxxx<br>
 */
public class JdbcUtils {
	private static String url;
	private static String username;
	private static String password;
	private static String driver;
	private static final ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	static{
		//获取jdbc配置
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		url = bundle.getString("url");
		username=bundle.getString("username");
		password=bundle.getString("password");
		driver=bundle.getString("driver");
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 返回与数据库的链接
	 * 使connection对象与线程相互关联
	 * 每一个线程，保存一个connection对象
	 * @return 与数据库的链接对象 
	 */
	public static Connection getConnectionInThread(){
		Connection conn = tl.get();
		try{
			if(conn==null||conn.isClosed()){
				conn = DriverManager.getConnection(url, username, password);
			}
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 创建一个新的连接对象
	 * 与线程无关
	 * @return 连接对象
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭与数据库的连接
	 */
	public static void closeConnection(){
		Connection conn = tl.get();
		try {
			if(conn==null){
				return ;
			}
			conn.close();
			conn=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到数据库连接池
	 * @return 数据库连接池
	 */
	public static DataSource getDataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setUrl(url);
		return ds;
	}
}
