package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CartAPI
 */
@WebServlet("/CartAPI")
public class CartAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartAPI() {
        super();

    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CartController itemObj = new CartController();
		String output = itemObj.insertCart(request.getParameter("productname"), 
				 request.getParameter("description"), 
				request.getParameter("size"), 
				request.getParameter("price"), 
				request.getParameter("type")); 
		
				response.getWriter().write(output);
	}
	
	
	
	// Convert request parameters to a Map the reqeat and response
	private static Map getParasMap(HttpServletRequest request) 
	{ 
	 Map<String, String> map = new HashMap<String, String>(); 
	try
	 { 
	 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
	 String queryString = scanner.hasNext() ? 
	 scanner.useDelimiter("\\A").next() : ""; 
	 scanner.close(); 
	 String[] params = queryString.split("&"); 
	 for (String param : params) 
	 {
		 String[] p = param.split("=");
		 map.put(p[0], p[1]); 
		 } 
		 } 
		catch (Exception e) 
		 { 
		 } 
		return map; 
		}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CartController itemObj = new CartController();
		Map paras = getParasMap(request); 
		 String output = itemObj.updateCart(paras.get("hidItemIDSave").toString(), 
		 paras.get("productname").toString(), 
		 paras.get("description").toString(), 
		paras.get("size").toString(), 
		paras.get("price").toString(), 
		paras.get("type").toString()); 
		response.getWriter().write(output);
		
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		CartController itemObj = new CartController();
		Map paras = getParasMap(request); 
		 String output = itemObj.deleteCart(paras.get("cartid").toString()); 
		response.getWriter().write(output);
		System.out.println(paras.get("cartid").toString());
	}

}
