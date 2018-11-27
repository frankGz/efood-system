package com.gz.efood.controller.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gz.efood.model.cart.Cart;
import com.gz.efood.model.cart.Prices;


@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		// create a cart if session doesn't have one
		if(session.getAttribute("cart") == null) 
		{
			session.setAttribute("cart", new Cart());
		}
		
		Cart cart = (Cart) session.getAttribute("cart");
		
		// 
		if(request.getParameter("add")!= null && request.getParameter("add")!= "") // add an item
		{
			response.setContentType("text/html");
	        Writer out = response.getWriter();
	        String number = request.getParameter("add");
	        cart.addItem(number);
	        int items = cart.getSize();
	        out.write("Item " + number + " is successfully added to cart. You now have " + items + " items in cart." );
	        
		}else if(request.getParameter("update")!= null && request.getParameter("qty")!=null) // update
		{
			String message = "";
			try
			{
				String number = request.getParameter("update");
				int qty = Integer.parseInt(request.getParameter("qty"));
				if(qty < 0) {
					message = "Cannot enter negative quantity!";
				}else {
					cart.update(number, qty);
					message = "Update success!";
				}
				
			} catch (NumberFormatException e)
			{
				message = "You have to entry a positive integer as quantity!";
			}
			request.setAttribute("cartSize", cart.getSize());
			request.setAttribute("itemTypes", cart.getRecord());
			request.setAttribute("prices", cart.calculate());
			request.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/Cart.jspx").forward(request, response);
			
		}else {
			request.setAttribute("cartSize", cart.getSize());
			request.setAttribute("itemTypes", cart.getRecord());
			request.setAttribute("prices", cart.calculate());
			request.getRequestDispatcher("/WEB-INF/Cart.jspx").forward(request, response);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
