package tests;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.gz.efood.model.PO.Order;
import com.gz.efood.model.PO.POManager;
import com.gz.efood.model.beans.User;
import com.gz.efood.model.cart.Cart;
import com.gz.efood.model.cart.Prices;

public class POtest
{

	public static void main(String[] args) throws JAXBException, IOException, InterruptedException
	{
		POManager p = POManager.getPOManager();
		//String filename = "/sdad/dsds" + String.format("/pox_%02d.xml", 0 + 1);
		//System.out.println(filename);
		
		Cart cart = new Cart();
		
		cart.addItem("2910h705"); //3.92
		cart.addItem("2910h734"); //1.42
		cart.addItem("2910h734"); 
		cart.addItem("2910h785"); //3.98
		cart.addItem("asdasd"); 
		cart.update("2910h785", 200);
		Prices d= cart.calculate();
		
		System.out.println(d.toString());
		//System.out.println(cart.size());
		
		User user = new User("id123", "name");
		Order order = new Order(user, cart);
		System.out.println("add anther one");
		p.addOrder(order);
		
		Thread.sleep(5000);
		System.out.println("add anther one");
		p.addOrder(order);
		
	}

}
