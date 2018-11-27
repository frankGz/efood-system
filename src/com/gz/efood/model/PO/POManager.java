package com.gz.efood.model.PO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.gz.efood.model.JAXBbeans.CustomerType;
import com.gz.efood.model.JAXBbeans.ItemType;
import com.gz.efood.model.JAXBbeans.ItemsType;
import com.gz.efood.model.JAXBbeans.ObjectFactory;
import com.gz.efood.model.JAXBbeans.OrderType;
import com.gz.efood.model.beans.User;
import com.gz.efood.model.cart.Cart;
import com.gz.efood.model.cart.Prices;


/**
 * Singleton class to handle an order, onece check out,
 * create an order instance and pass it to POManager.
 * POManager create a directory.
 */
public class POManager
{
	private final String PATH = "/home/user/POxmls";
	
	private static POManager manager = null;
	private Map<String, Integer> userMap; //user id and its number of ordered
	private ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<Order>();// To store incoming order
	private Set<String> urls = new HashSet<>();
	private int orderId;
	private boolean spaceInUse = false;
	
	/**
	 * singleton
	 * @return
	 */
	public static POManager getPOManager() {
		if(manager == null) {
			return new POManager();
		}else {
			return manager;
		}
	}
	
	/**
	 * Initiallize.
	 */
	private POManager()
	{
		this.userMap = new HashMap<>();
		this.orderId = 1;
		
		File f = new File(PATH);
		if(!f.exists()){
			f.mkdir();
		}
		StartCheckout();
	}
	
	
	public synchronized int addOrder(Order order)
	{
		User user = order.getUser();
		System.out.println(user.toString());
		int userNumberOrdered = userMap.containsKey(user.getId()) ? userMap.get(user.getId()) : 0;
		String filename = PATH + String.format("/po%s_%02d.xml",user.getId(), userNumberOrdered + 1);
		
		order.setUrl(filename);
		order.setOrderId(orderId);
		
		//enqueue
		queue.add(order);
		
		//update map and id count
		userMap.put(user.getId(), userNumberOrdered + 1);
		return orderId++;
		
	}
	
	public void StartCheckout()
	{
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(true) {
					
					
					if(!spaceInUse && !queue.isEmpty()) // is directory is not in use by B2B and have order in queue
					{
						System.out.println("Order proceed:" + orderId);
						try
						{
							Order order = queue.poll();
							User user = order.getUser();
							Cart cart = order.getCart();
							Prices prices = cart.calculate();
							String date = order.getSubmitDate();
							
							String filename = order.getUrl();
								
							//Construct order for JAXB
							CustomerType customerType = new CustomerType();
							customerType.setAccount(user.getId());
							customerType.setName(user.getName());
							
							ItemsType itemsType = new ItemsType();
							List<ItemType> ls = cart.getRecord();
							for(ItemType i : ls) {
								itemsType.getItem().add(i);
							}
							
							OrderType orderType = new OrderType();
							orderType.setCustomer(customerType);
							orderType.setItems(itemsType);
							orderType.setGrandTotal(prices.getGrandTotal());
							orderType.setHST(prices.getHST());
							orderType.setShipping(prices.getShipping());
							orderType.setId(order.getOrderId());
							orderType.setTotal(prices.getTotal());
							orderType.setSubmitted(date);
							
							//Render xml
							ObjectFactory objectFactory = new ObjectFactory();
							JAXBElement<OrderType> jaxbElement = objectFactory.createOrder(orderType);
							JAXBContext jc = JAXBContext.newInstance(orderType.getClass());
							
							Marshaller m = jc.createMarshaller();
							BufferedWriter bw=new BufferedWriter(new FileWriter(filename));
							m.marshal(jaxbElement, bw);
							urls.add(filename);
							bw.flush();
							bw.close();
						} catch (JAXBException e)
						{
							
							e.printStackTrace();
						} catch (IOException e)
						{
							
							e.printStackTrace();
						}
					}
					try
					{
						Thread.sleep(500);
					} catch (InterruptedException e)
					{
						
					}
				}
				
			}
		});
		
		thread.start();
	}

	/**
	 * @return the spaceInUse
	 */
	public boolean isSpaceInUse()
	{
		return spaceInUse;
	}

	/**
	 * @param spaceInUse the spaceInUse to set
	 */
	public void setSpaceInUse(boolean spaceInUse)
	{
		this.spaceInUse = spaceInUse;
	}
	
	public synchronized String getUrls()
	{
		String string = "";
		for(String s : urls)
		{
			string += s + ",";
		}
		return string;
	}
	
	public synchronized void clearUrls() {
		this.urls.clear();
	}
	
	
	/*
	public synchronized int checkout(Order order) throws JAXBException, IOException {
		User user = order.getUser();
		Cart cart = order.getCart();
		Prices prices = cart.calculate();
		String date = order.getSubmitDate();
		
		int userNumberOrdered = userMap.containsKey(user.getId()) ? userMap.get(user.getId()) : 0;
		String filename = PATH + String.format("/pox_%02d.xml", userNumberOrdered + 1);
			
		//Construct order for JAXB
		CustomerType customerType = new CustomerType();
		customerType.setAccount(user.getId());
		customerType.setName(user.getName());
		
		ItemsType itemsType = new ItemsType();
		List<ItemType> ls = cart.getRecord();
		for(ItemType i : ls) {
			itemsType.getItem().add(i);
		}
		
		OrderType orderType = new OrderType();
		orderType.setCustomer(customerType);
		orderType.setItems(itemsType);
		orderType.setGrandTotal(prices.getGrandTotal());
		orderType.setHST(prices.getHST());
		orderType.setShipping(prices.getShipping());
		orderType.setId(orderId);
		orderType.setTotal(prices.getTotal());
		orderType.setSubmitted(date);
		
		//Render xml
		ObjectFactory objectFactory = new ObjectFactory();
		JAXBElement<OrderType> jaxbElement = objectFactory.createOrder(orderType);
		JAXBContext jc = JAXBContext.newInstance(orderType.getClass());
		
		Marshaller m = jc.createMarshaller();
		BufferedWriter bw=new BufferedWriter(new FileWriter(filename));
		m.marshal(jaxbElement, bw);
		bw.flush();
		bw.close();
		
		//update map and id count
		userMap.put(user.getId(), userNumberOrdered + 1);
		return orderId++;
		
		
	}
	*/

}
