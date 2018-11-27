package com.gz.efood.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.gz.efood.model.Utlis.JdbcUtils;
import com.gz.efood.model.Utlis.Tools;
import com.gz.efood.model.beans.Category;

/**
 * @author frank.gz
 * dao for catalog
 *
 */
public class CategoryDAO extends DAO<Category>
{
	public List<Category> getAll()
	{
		String sql = "SELECT ID, NAME, DESCRIPTION FROM Category";
		return getForList(sql);
	}
	

	public Category getById(int id)
	{
		String sql = "SELECT ID, NAME, DESCRIPTION FROM Category WHERE ID = ?";
		return get(sql,id);
	}
	
	//c3p0 cannot handle type blob, have to do it manually
	
	public byte[] getPicture(int id)
	{
		Statement statement = null;
		Connection connection = null;
		ResultSet rs = null;
		byte[] bytes = null;
		
		String sql = "SELECT PICTURE FROM Category WHERE ID =" + id;
		
		try {
			connection = JdbcUtils.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate("set schema roumani");
			rs = statement.executeQuery(sql);
			if(rs.next()) {
				String hex = rs.getString(1);
				bytes = Tools.hexToBytes(hex);
			}else {
				System.out.println("cannot find picture with id = " + id);
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtils.releaseConnection(connection);
		}
		
		return bytes;
	}

}
