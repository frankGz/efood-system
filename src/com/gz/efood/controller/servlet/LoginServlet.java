package com.gz.efood.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.gz.efood.model.beans.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		
		 if (request.getQueryString()==null) {	 
			 System.out.println(request.getSession().getAttribute("user"));
			  String strBackUrl = "http://" + request.getServerName()
			  + ":"                       
			  + request.getServerPort()                     
			  + request.getContextPath()                       
			  + request.getServletPath() ;    
			  response.sendRedirect("https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi?back=" + strBackUrl);
		 }else {
			
			User user = new User(request.getParameter("user") ,request.getParameter("name"));
			
			//request.getSession().setAttribute("user", request.getParameter("user"));
			//request.getSession().setAttribute("name", request.getParameter("name"));
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/Home").forward(request, response);
			
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
