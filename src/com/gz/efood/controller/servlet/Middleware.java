package com.gz.efood.controller.servlet;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gz.efood.model.PO.POManager;

import jdk.nashorn.internal.runtime.URIUtils;


/**
 * Servlet implementation class Middleware
 */
@WebServlet("/Middleware")
public class Middleware extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		POManager poManager = (POManager) request.getServletContext().getAttribute("POManager");
		
		if(request.getParameter("action").equals("dump")) // let the Middleware work
		{
			String urls;
			if (poManager!=null)
			{
				poManager.setSpaceInUse(true);
				urls = poManager.getUrls();
				poManager.clearUrls();
				System.out.println(urls);
			}else {
				// no orders have been checked
				urls = "";
			}
			response.setContentType("text/plain");
	        Writer out = response.getWriter();
	        
	        out.write(urls);
		}
		
		if(request.getParameter("action").equals("continue")) // Middleware is done, continue checkout
		{
			if (poManager!=null)
			{
				poManager.setSpaceInUse(false);
			}
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
