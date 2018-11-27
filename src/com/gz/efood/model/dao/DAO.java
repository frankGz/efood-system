package com.gz.efood.model.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.gz.efood.model.Utlis.JdbcUtils;
/**
 *封装了CRUD 的方法，以供子类使用
 *当前 DAO 直接在方法中获取数据库连接 
 * @param <T>：当前 DAO 处理实体类的类型是什么
 */
public class DAO<T> {
	
	private QueryRunner queryRunner = new QueryRunner();

	private Class<T> clazz;
	
	public DAO() {
		Type superClass = getClass().getGenericSuperclass();//返回该类继承的带有泛型的父类
		//System.out.println( superClass);
		//由于Type 只是一个接口，无法获取其泛型类型，但是Type接口继承ParameterizedType 可使用此类中的方法获取
		if(superClass instanceof ParameterizedType) {//检查此类是否是带参数的类
			ParameterizedType parameterizedType = (ParameterizedType)superClass;//如果是将其强转成该类型
			Type[] typeArgs = parameterizedType.getActualTypeArguments();//获得此类参数的类型，返回一个Type数组
			
			if(typeArgs != null && typeArgs.length > 0) {//如果返回值不是空，并且长度大于0 证明获取了参数类型
				
				if(typeArgs[0] instanceof Class) {//检查此类型是否是一个Class
					clazz = (Class<T>) typeArgs[0];//如果是将其强转 
				}
			}
		} 
	}
	
	/**
	 * 返回某一个字段的值：例如返回某一条记录customer NAME,或返回数据报表中有多少条记录
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql,Object ... args) {
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			return (E)queryRunner.query(connection, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	/**
	 * 返回 T 所对应的List
	 * @param <T>
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getForList(String sql,Object ... args) {
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	/**
	 * 返回对应T的一个实体类的对象。
	 * @param <T>
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql,Object ... args) {
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	/**
	 * 该方法封装了INSERT\DELETE\UPDATE 操作
	 * @param sql：SQL 语句
	 * @param args： 填充SQL语句的占位符
	 */
	public void update(String sql,Object ... args) {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = JdbcUtils.getConnection();
			queryRunner.update(connection, sql, args);//SQLSERVER 2008 使用此方法执行insert into会报错。。
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConnection(connection);
		}
		
	}
	
}