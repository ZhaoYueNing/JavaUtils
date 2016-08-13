package cn.zhaoyuening.utils.sql;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * <b>jdbc����������</b><br>
 * �����������ݿ�
 * @author Zhao
 * <hr>
 * Ӧ���ڴ���srcĿ¼��д�����ļ����������ݿ����á�<br>
 * �ļ�����jdbc.properties<br>
 * �ļ���ʽ<br>
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
		//��ȡjdbc����
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
	 * ���������ݿ������
	 * ʹconnection�������߳��໥����
	 * ÿһ���̣߳�����һ��connection����
	 * @return �����ݿ�����Ӷ��� 
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
	 * ����һ���µ����Ӷ���
	 * ���߳��޹�
	 * @return ���Ӷ���
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
	 * �ر������ݿ������
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
	 * �õ����ݿ����ӳ�(c3p0)
	 * @return ���ݿ����ӳ�
	 */
	public static DataSource getDataSource(){
		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			return null;
		}
		ds.setUser(username);
		ds.setPassword(password);
		ds.setJdbcUrl(url);
		return ds;
	}
}
