package com.gz.efood.model.PO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.gz.efood.model.beans.User;
import com.gz.efood.model.cart.Cart;


/**
 * Aggreate everything that needs for generate P/O xml.
 *
 */


public class Order
{
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

    private Date    submitDate;
    private User customer;
    private Cart    cart;
    private String  url;
    private int orderId;
    
    /**
     * for JXBA
     */
    public  Order()
	{
		
	}

	/**
	 * 
	 * 
	 * 
	 * @param customer
	 * @param cart
	 * 
	 */
	public Order(User customer, Cart cart)
	{
		
		this.submitDate = Calendar.getInstance().getTime();
		this.customer = customer;
		this.cart = cart;
		
	}


    public String getSubmitDate() {
        return SDF.format(submitDate).toString().split(" ")[0];
    }


    public User getUser() {
        return customer;
    }


    public Cart getCart() {
        return cart;
    }
    
    public String getUrl() {
        return url;
    }

	public int getOrderId()
	{
		return orderId;
	}

	//can only be used by POManager

	void setUrl(String url)
	{
		this.url = url;
	}


	void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}
	
	





	
    
 

}
