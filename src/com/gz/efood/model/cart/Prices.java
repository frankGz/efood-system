package com.gz.efood.model.cart;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gz.efood.model.Utlis.Tools;

/**
 * Store the calculated prices to make JAXB works, read-only after created.
 *
 */
@XmlRootElement(name="order")
public class Prices
{
	
	private double total;
	private double shipping;
	private double HST;
	private double grandTotal;
	
	public Prices() 
	{
		//for jaxb
	}

	/**
	 * Can only be created by cart to store values
	 * @param total
	 * @param shipping
	 * @param tax
	 * @param grandTotal
	 */
	Prices(double total, double shipping, double tax, double grandTotal)
	{
		super();
		this.total = total;
		this.shipping = shipping;
		this.HST = tax;
		this.grandTotal = grandTotal;
	}
	@XmlElement
	public double getTotal()
	{
		return Tools.roundOff(total);
	}
	@XmlElement
	public double getShipping()
	{
		return Tools.roundOff(shipping);
	}
	@XmlElement
	public double getHST()
	{
		return Tools.roundOff(HST);
	}
	@XmlElement
	public double getGrandTotal()
	{
		return Tools.roundOff(grandTotal);
	}

	@Override
	public String toString()
	{
		return "Prices [total=" + total + ", shipping=" + shipping + ", HST=" + HST + ", grandTotal=" + grandTotal
				+ "]";
	} 
	
	
	
	
	 
	 
	 
}
