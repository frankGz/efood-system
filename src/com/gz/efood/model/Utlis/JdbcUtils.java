package com.gz.efood.model.Utlis;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Frank.Gz
 * Utility tools for JDBC
 */
public class JdbcUtils {

	/**
	 * 释放 Connection
	 */
	public static void releaseConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DataSource dataSource = null;
	
	static{
		//数据源只能被创建一次。
		dataSource = new ComboPooledDataSource("efood");
	}
	
	
	/**
	 * 返回数据源的一个Connection对象
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		Connection con = dataSource.getConnection();
		con.createStatement().executeUpdate("set schema roumani");
		return con;
	}
}
