package com.gz.efood.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import com.gz.efood.model.JAXBbeans.OrderType;
import com.gz.efood.model.PO.Order;
import com.gz.efood.model.PO.POManager;
import com.gz.efood.model.Utlis.XMLUtils;
import com.gz.efood.model.beans.User;
import com.gz.efood.model.cart.Cart;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("check") != null)
		{
			String filename = request.getParameter("check");
			try
			{
				OrderType orderType = XMLUtils.FileToJAXBbeans(filename);
				request.setAttribute("orderType", orderType);
				request.getRequestDispatcher("/WEB-INF/Review.jspx");
				
			} catch (JAXBException e)
			{
				request.setAttribute("message", "Your order is currently unavaiable. Please try again later.");
				e.printStackTrace();
			}
			
		} else
		{
			ServletContext context = request.getServletContext();
			if(request.getServletContext().getAttribute("POManager")==null) {
				POManager poManager = POManager.getPOManager();
				context.setAttribute("POManager", poManager);
			}
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			Cart cart = (Cart) session.getAttribute("cart");
			Order order = new Order(user,cart);
			POManager poManager = (POManager) context.getAttribute("POManager");
			poManager.addOrder(order);
			cart.clear();
			String[] url = order.getUrl().split("/");
			String filename = url[url.length - 1];
			String currentServlet = "http://" + request.getServerName()
			  + ":"                       
			  + request.getServerPort()                     
			  + request.getContextPath()                       
			  + request.getServletPath() + "?check="; 
			request.setAttribute("url", currentServlet + filename);
			request.getRequestDispatcher("/WEB-INF/Checkout.jspx").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
