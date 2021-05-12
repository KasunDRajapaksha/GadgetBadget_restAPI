package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class cart {

	private Connection connect(){

	 Connection con = null;
	 try{ 
	 
		 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", ""); 
	 }
	 catch (Exception e){e.printStackTrace();
	 }
	 
	 return con;
	 } 
	
	public String insertCart(String cart_id, String product_id, String added_date, String qty) { 
	 
		String output = ""; 
	 
		try{ 
	 
			Connection con = connect(); 
	 
			if (con == null) {
		 
				return "Error while connecting to the database for inserting."; 
	 
			} 
	 
			// create a prepared statement
	
			String query = " insert into cart (`cart_id`,`product_id`,`added_date`,`qty`)"+ " values (?, ?, ?, ?)"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setInt(2, Integer.parseInt(product_id)); 
			preparedStmt.setString(3, added_date); 
			preparedStmt.setInt(4, Integer.parseInt(qty));
	
			
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 

	 String newItems = readCart();
	 output = "{\"status\":\"success\", \"data\": \"" +
	 newItems + "\"}";

	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\":\"Error while inserting.\"}"; 
		 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
		
	
	
	public String readCart(){


		 String output = ""; 
		 
		 try{
			 
			 Connection con = connect();
			 if (con == null) {
	
				 return "Error while connecting to the database for reading."; }
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Product ID</th><th>Added date</th>" +
					 "<th>qty</th><th>Update</th><th>Remove</th></tr>";
		
			 String query = "select * from cart";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 
			 // iterate through the rows in the result set
			 while (rs.next()){
				 
				 String cart_id = Integer.toString(rs.getInt("cart_id"));
				 String product_id = Integer.toString(rs.getInt("product_id"));
				 String added_date = rs.getString("added_date");
				 String qty = Integer.toString(rs.getInt("qty"));
				 
				 // Add into the html table
				 output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate'type='hidden' value='" + cart_id + "'>"
						 + product_id + "</td>";
				 output += "<td>" + added_date + "</td>";
				 output += "<td>" + qty + "</td>";
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
						 + "class='btnUpdate btn btn-secondary' data-itemid='" + cart_id + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' "
						 + "class='btnRemove btn btn-danger' data-itemid='" + cart_id + "'></td></tr>";
			 }
			 con.close();
			 // Complete the html table
			 output += "</table>";
		 }
		 catch (Exception e){
			 output = "Error while reading.";
			 System.err.println(e.getMessage());
		 }
		 return output;
	 } 
	
	public String updateCart(String cart_id, String product_id, String added_date, String qty){
	 
		String output = "";
	 
		 try {
			 
			 Connection con = connect();
			 
			 if (con == null){
				 return "Error while connecting to the database for updating."; 
			 }
			 
			 // create a prepared statement
			 String query = "UPDATE cart SET product_id=?,added_date=?,qty=? WHERE cart_id=?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 
			 preparedStmt.setInt(1, Integer.parseInt(product_id));
			 preparedStmt.setString(2, added_date);
			 preparedStmt.setInt(3, Integer.parseInt(qty));
			 preparedStmt.setInt(4,Integer.parseInt(cart_id));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newItems = readCart();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}";
		 }
		 catch (Exception e){
			 output = "{\"status\":\"error\", \"data\":\"Error while updating.\"}";
			 System.err.println(e.getMessage());
		 }
		 return output;
	} 
	
	public String deleteCart(String cart_id){
	 
		 String output = "";
		 
		 try {
				 
			 Connection con = connect();
			 
			 if (con == null){
				 return "Error while connecting to the database for deleting."; 
			 }
			 
			 // create a prepared statement
			 String query = "delete from cart where cart_id=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(cart_id));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newItems = readCart();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}";
		 }
		 catch (Exception e){
			 output = "{\"status\":\"error\", \"data\":\"Error while deleting.\"}";
			 System.err.println(e.getMessage());
		 }
		 return output;
		 }
	
	
}
