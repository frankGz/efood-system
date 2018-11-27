package com.gz.efood.model.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.gz.efood.model.JAXBbeans.ItemType;
import com.gz.efood.model.Utlis.Tools;
import com.gz.efood.model.beans.Item;
import com.gz.efood.model.dao.ItemDAO;

import sun.misc.Cleaner;

/**
 * @author user
 * A cart contains a list of item numbers with the number ordered and can calculate the 
 */
public class Cart
{
	
	final private double FREE_SHIPPING = 100.00;
	final private double HST = 0.13;
	
	private Map<String, Integer> cart;
	
	public Cart() {
		cart = new HashMap<>();
	}
	
	
	/**
	 * add an item to cart, if it appears in the cart, increment 1, else put 1 into the cart
	 * @param number
	 */
	public synchronized void addItem(String number)
	{
		int count = cart.containsKey(number) ? cart.get(number) : 0;
		cart.put(number, count + 1);		
	}
	
	/**
	 * change the quantity of an item number, remove it if qty is 0
	 * @param number
	 * @param qty
	 */
	public synchronized void update(String number, int qty)
	{
		cart.put(number, qty);
		if(qty == 0) {
			cart.remove(number);
		}
	}
	
	/**
	 * calcuate the price in the cart, where total is all item in the cart add up,
	 * a shipping cost ($5 that is waived for orders of $100 or more before taxes)
	 * HST (13% of total, including shipping if any)
	 * and a grand total.
	 * @return an arry of double that have total, shipping, tax, grand_total
	 */
	public synchronized Prices calculate()
	{
		double total = 0.0, shipping = 5.0, tax = 0.0, grand_total = 0.0;
		ItemDAO dao = new ItemDAO();
		
		if(!cart.isEmpty()) {
			for(Map.Entry<String, Integer> entry: cart.entrySet())
			{
				try
				{
					double price = Tools.roundOff(dao.getByItemNumber(entry.getKey()).getPrice());
					total += price * (double) entry.getValue();
				} catch (NullPointerException e)
				{
					System.out.println("No such an item number: " + entry.getKey());
				}
			}
		}
		
		if (total >= FREE_SHIPPING)
		{
			shipping = 0.0;
		}
		
		tax = Tools.roundOff((total + shipping) * HST);
		
		grand_total = total + shipping + tax;
		
		
		return new Prices(total,shipping,tax,grand_total);
		
	}
	
	/**
	 * Create a list of JAXVveans.ItemType
	 * @return list of cart rocord
	 */
	public synchronized List<ItemType> getRecord()
	{
		List<ItemType> l = new ArrayList<>();
		
		ItemDAO dao = new ItemDAO();
		for(Map.Entry<String, Integer> entry: cart.entrySet()) 
		{
			try
			{
				ItemType itemType = new ItemType();
				Item item = dao.getByItemNumber(entry.getKey());
				double price = Tools.roundOff(item.getPrice());
				double extended = price * (double) entry.getValue();
				
				itemType.setExtended(extended);
				itemType.setName(item.getName());
				itemType.setNumber(item.getNumber());
				itemType.setPrice(price);
				itemType.setQuantity(entry.getValue());
				
				l.add(itemType);
				
			}  catch (NullPointerException e)
			{
				System.out.println("No such an item number: " + entry.getKey());
			}
		}
		
		return l;
	}
	

	/**
	 * @return if the cart is empty
	 */
	public boolean isEmpty()
	{
		return cart.isEmpty();
	}
	
	/**
	 * @return size of the cart
	 */
	public int getSize()
	{
		int size = 0;
		for(int i : cart.values())
		{
			size += i;
		}
		return size;
	}
	
	/**
	 * Clear the cart
	 */
	public void clear() {
		this.cart.clear();
	}

}
