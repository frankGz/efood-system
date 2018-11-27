package com.gz.efood.controller.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gz.efood.model.dao.CategoryDAO;
import com.gz.efood.model.dao.ItemDAO;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getPathInfo().substring(1));
		CategoryDAO dao = new CategoryDAO();
		byte[] pic = dao.getPicture(id);
		
		response.setContentType("image/png");
		
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(pic);
		outputStream.flush();
		outputStream.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
