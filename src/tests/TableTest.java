package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;



public class TableTest
{


	
	public static void main(String[] args) throws Exception
	{
		
		String DB_URL = "jdbc:derby://localhost:64413/EECS;user=student;password=secret";
		String query1 = "SELECT * FROM Item";
		String query2 = "SELECT * FROM Category";
		String result;
		
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		Connection con = DriverManager.getConnection(DB_URL);
		Statement s = con.createStatement();
		s.executeUpdate("set schema roumani");
		
		ResultSet r1 = s.executeQuery(query1);
		ResultSetMetaData rsmd1 = r1.getMetaData();	
		int r1c = rsmd1.getColumnCount();
		
		System.out.println("Item label:");
		for(int i = 1; i <= r1c; i++) {
			System.out.println(rsmd1.getColumnLabel(i) + " " + rsmd1.getColumnTypeName(i));
		}
		while (r1.next()) {
			result = "";
			for(int i = 1; i <= r1c; i++) {
				result += r1.getString(i) + "\t";
			}
			System.out.println(result);
		}
		System.out.println();
		/*
		NUMBER CHAR
		NAME VARCHAR
		PRICE DOUBLE
		QTY INTEGER
		ONORDER INTEGER
		REORDER INTEGER
		CATID INTEGER
		SUPID INTEGER
		COSTPRICE DOUBLE
		UNIT VARCHAR
		 */
		
		
		ResultSet r2 = s.executeQuery(query2);
		ResultSetMetaData rsmd2 = r2.getMetaData();
		int r2c = rsmd2.getColumnCount();
		
		System.out.println("catagory label:");
		for(int i = 1; i <= r2c; i++) {
			System.out.println(rsmd2.getColumnLabel(i)+ " " + rsmd2.getColumnTypeName(i));
		}
		
		r2.next();
			result = "";
			for(int i = 1; i <= r2c; i++) {
				result += r2.getString(i) + ",";
			}
			System.out.println(result);
		
		System.out.println();
		/*
		ID INTEGER
		NAME VARCHAR
		DESCRIPTION VARCHAR
		PICTURE BLOB
		 */
		
		r1.close();
		r2.close();
		s.close();
		con.close();
		
		
		
		
	}

}
