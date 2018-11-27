package com.gz.efood.model.dao;

import java.util.List;

import com.gz.efood.model.beans.Item;

/**
 * @author frank.gz
 *
 */
public class ItemDAO extends DAO<Item>{
	
	/**
	 * @return all item in the db
	 */
	public List<Item> getAllItem()
	{
		String sql = "SELECT NUMBER, NAME, PRICE, QTY, ONORDER, ONORDER, REORDER, CATID, SUPID, COSTPRICE, UNIT FROM Item";
		return getForList(sql);
	}
	
	/**
	 * @param catId
	 * @return all items with given catid
	 */
	public List<Item> getByCatId(int catId)
	{
		String sql = "SELECT NUMBER, NAME, PRICE, QTY, ONORDER, ONORDER, REORDER, CATID, SUPID, COSTPRICE, UNIT FROM Item WHERE CATID = ?";
		return getForList(sql, catId);
	}
	
	/**
	 * @param number
	 * @return an item with given number(string, unique)
	 */
	public Item getByItemNumber(String number)
	{
		String sql = "SELECT NUMBER, NAME, PRICE, QTY, ONORDER, ONORDER, REORDER, CATID, SUPID, COSTPRICE, UNIT FROM Item WHERE NUMBER = ?";
		return get(sql, number);
	}
	
	
	/**
	 * Handles word search, input should have a pattern like str
	 * @param search
	 * @return
	 */
	public List<Item> getItemLike(String search)
	{
		String sql = "SELECT NUMBER, NAME, PRICE, QTY, ONORDER, ONORDER, REORDER, CATID, SUPID, COSTPRICE, UNIT FROM Item WHERE NAME LIKE ? UNION SELECT NUMBER, NAME, PRICE, QTY, ONORDER, ONORDER, REORDER, CATID, SUPID, COSTPRICE, UNIT FROM Item WHERE NAME LIKE ?";
		sql += "  UNION SELECT NUMBER, NAME, PRICE, QTY, ONORDER, ONORDER, REORDER, CATID, SUPID, COSTPRICE, UNIT FROM Item WHERE NAME LIKE ?";
		sql += "  UNION SELECT NUMBER, NAME, PRICE, QTY, ONORDER, ONORDER, REORDER, CATID, SUPID, COSTPRICE, UNIT FROM Item WHERE NUMBER = ?";
		return getForList(sql, "%" + search + "%", "%" + search.toLowerCase() + "%", "%" + search.substring(0, 1).toUpperCase() +  search.substring(1).toLowerCase() + "%", search);
	}
	
	
	

}
