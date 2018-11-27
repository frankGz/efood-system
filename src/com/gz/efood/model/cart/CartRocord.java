package com.gz.efood.model.cart;

public class CartRocord
{
	private String number;
	private String name;
	private double price;
	private Integer qty;
	private double extended;
	
	public CartRocord()
	{
		//for jaxb
	}

	/**
	 * for cart use only, read-only after created.
	 * @param number
	 * @param name
	 * @param qty
	 * @param extended
	 */
	CartRocord(String number, String name,double price, Integer qty, double extended)
	{
		super();
		this.number = number;
		this.name = name;
		this.price = price;
		this.qty = qty;
		this.extended = extended;
	}

	public String getNumber()
	{
		return number;
	}

	public String getName()
	{
		return name;
	}

	public Integer getQty()
	{
		return qty;
	}

	public double getExtended()
	{
		return extended;
	}
	
	public double getPriced()
	{
		return price;
	}
	
	
	
	
	
}
