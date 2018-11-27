package tests;

import com.gz.efood.model.beans.Item;
import com.gz.efood.model.cart.Cart;
import com.gz.efood.model.cart.Prices;
import com.gz.efood.model.dao.ItemDAO;

public class CartTest
{

	public static void main(String[] args)
	{
		
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

	}

}
