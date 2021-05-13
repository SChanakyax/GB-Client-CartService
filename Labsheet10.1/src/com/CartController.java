package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class CartController {
	
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
 
			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shoppingcart?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root",""); 
		} 
		catch (Exception e) 
		{e.printStackTrace();} 
		return con; 
	} 
	//insert to the cart
	
	public String insertCart(String productname, String description, String size, String price, String type) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement to insert data to table
			 String query = " insert into cart (`cartid`,`productname`,`description`,`size`,`price`,`type`)"
					+ " values (?, ?, ?, ?, ?,?)"; 

			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, productname); 
			 preparedStmt.setString(3, description); 
			 preparedStmt.setString(4, size); 
			 preparedStmt.setDouble(5, Double.parseDouble(price)); 
			 preparedStmt.setString(6, type); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readCart(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 }  
	//Read the cart		
	public String readCart()
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for reading."; 
	 } 
	 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>product_name</th>" 
	 + "<th>Description</th><th>size</th><th>Price</th>"
	 + "<th>Type</th>" 
	 + "<th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from cart"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // loop the statements
	 while (rs.next()) 
	 { 
	 String cartid = Integer.toString(rs.getInt("cartid")); 
	 String productname = rs.getString("productname"); 
	 String description = rs.getString("description"); 
	 String size = rs.getString("size"); 
	 String price = Double.toString(rs.getDouble("price")); 
	 String type = rs.getString("type");
	 
	// Add into the html table retreive
	 output += "<tr><td>" + productname + "</td>"; 
	 output += "<td>" + description + "</td>"; 
	 output += "<td>" + size + "</td>"; 
	 output += "<td>" + price + "</td>"; 
	 output += "<td>" + type + "</td>"; 
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' "
	+ "class='btnUpdate btn btn-secondary' data-cartid='" + cartid + "'></td>"
	+ "<td><input name='btnRemove' type='button' value='Remove' "
	+ "class='btnRemove btn btn-danger' data-cartid='" + cartid + "'></td></tr>"; 
	 } 
	 con.close(); 
	 // retreive the html table
	 output += "</table>"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while reading the items."; 
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}
	
	//Update cart
	public String updateCart(String cartid, String productname, String description, String size, 
			 String price, String type) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for updating."; 
			 } 
			 // create prepared statement for update cart
			 String query = "UPDATE cart SET productname=?,description=?,size=?price=?,type=? WHERE cartid=?"; 
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values to 
			 preparedStmt.setString(1, productname); 
			 preparedStmt.setString(2, description); 
			 preparedStmt.setString(3, size); 
			 preparedStmt.setDouble(4, Double.parseDouble(price)); 
			 preparedStmt.setString(5, type); 
			 preparedStmt.setInt(6, Integer.parseInt(cartid)); 
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readCart(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 }
	//delete cart items		 
	public String deleteCart(String cartid) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 //delete cart items by cart id  prepared statement 
	 String query = "delete from cart where cartid=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(cartid)); 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newItems = readCart(); 
	 output = "{\"status\":\"success\", \"data\": \"" + 
	 newItems + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
}
