package com.gz.efood.controller.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gz.efood.model.beans.Category;
import com.gz.efood.model.beans.Item;
import com.gz.efood.model.dao.CategoryDAO;
import com.gz.efood.model.dao.ItemDAO;

/**
 * Servlet implementation class CatalogServlet
 */
@WebServlet("/Catalog")
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemDAO dao = new ItemDAO();
		CategoryDAO dao2 = new CategoryDAO();
		String msg = "";
		List<Item> list;
		
		List<Category> list2 = dao2.getAll();
		
		if(request.getParameter("search") != null) {
			
			//come from a search
			list = dao.getItemLike(request.getParameter("search").trim());
			msg = "Seach '" + request.getParameter("search").trim() + "'";
			
		}else if (request.getParameter("categoryId") != null) {
			
			//comes from a category browse
			int id = Integer.parseInt(request.getParameter("categoryId"));
			list = dao.getByCatId(id);
			msg = "List all food in " + request.getParameter("categoryName");
			
		}else {
			
			// come from findall or any abnormal access
			list = dao.getAllItem();
			msg = "List all food in inventory";
			
		}
		
		request.setAttribute("message", msg);
		request.setAttribute("items", list);
		request.setAttribute("categorys", list2);
		
		request.getRequestDispatcher("/WEB-INF/Catalog.jspx").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
