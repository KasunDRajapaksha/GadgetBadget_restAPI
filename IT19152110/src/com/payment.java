package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class payment {

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
		
		public String insertPayment(String paymentID,String cardNo, String nameOnCard, String expireDate, String cvc ,String totalAmount) { 
		 
			String output = ""; 
		 
			try{ 
		 
				Connection con = connect(); 
		 
				if (con == null) {
			 
					return "Error while connecting to the database for inserting."; 
		 
				} 
		 
				// create a prepared statement
		
				String query = " insert into payment (`paymentID`,`cardNo`,`nameOnCard`,`expireDate`,`cvc`,`totalAmount`)"+ " values (?, ?, ?, ?, ?, ?)"; 
		 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
				// binding values
				preparedStmt.setInt(1, 0); 
				preparedStmt.setInt(2, Integer.parseInt(cardNo)); 
				preparedStmt.setString(3, nameOnCard); 
				preparedStmt.setString(4, expireDate); 
				preparedStmt.setInt(5, Integer.parseInt(cvc)); 
				preparedStmt.setDouble(6, Double.parseDouble(totalAmount)); 
				
		
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 String newItems = readPayment();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newItems + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\":\"Error while insertind.\"}";
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
			
		
		
		public String readPayment(){


			 String output = ""; 
			 
			 try{
				 Connection con = connect();
				 if (con == null) {
		
					 return "Error while connecting to the database for reading."; }
				 
				 // Prepare the html table to be displayed
				 output = "<table border='1'><tr><th>Card No</th>" +
						 "<th>Name On Card</th>" +
						 "<th>Expire Date</th>" +
						 "<th>CVC</th>" +
						 "<th>Toatal Amount</th>" +
						 "<th>Update</th><th>Remove</th></tr>";
			
				 String query = "select * from payment";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 
				 // iterate through the rows in the result set
				 while (rs.next()){
					 
					 String paymentID = Integer.toString(rs.getInt("paymentID"));
					 String cardNo = Integer.toString(rs.getInt("cardNo"));
					 String nameOnCard = rs.getString("nameOnCard");
					 String expireDate = rs.getString("expireDate");
					 String cvc = Integer.toString(rs.getInt("cvc"));
					 String totalAmount = Double.toString(rs.getDouble("totalAmount"));
					
					 
					 // Add into the html table
					 output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate'type='hidden' value='" + paymentID + "'>"
							 + cardNo + "</td>";
					 output += "<td>" + nameOnCard + "</td>";
					 output += "<td>" + expireDate + "</td>";
					 output += "<td>" + cvc + "</td>";
					 output += "<td>" + totalAmount + "</td>";
					 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
							 + "class='btnUpdate btn btn-secondary' data-itemid='" + paymentID + "'></td>"
							 + "<td><input name='btnRemove' type='button' value='Remove' "
							 + "class='btnRemove btn btn-danger' data-itemid='" + paymentID + "'></td></tr>";
				 }
				 con.close();
				 // Complete the html table
				 output += "</table>";
			 }
			 catch (Exception e){
				 output = "Error while reading the items.";
				 System.err.println(e.getMessage());
			 }
			 return output;
		 } 
		
		public String updatePayment(String paymentID,String cardNo, String nameOnCard, String expireDate, String cvc ,String totalAmount){
			 
			String output = "";
		 
			 try {
				 
				 Connection con = connect();
				 
				 if (con == null){
					 return "Error while connecting to the database for updating."; 
				 }
				 
				 // create a prepared statement
				 String query = "UPDATE payment SET cardNo=?,nameOnCard=?,expireDate=?,cvc=?,totalAmount=? WHERE paymentID=?";
				 
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 
				
				 preparedStmt.setInt(1, Integer.parseInt(cardNo));
				 preparedStmt.setString(2, nameOnCard);
				 preparedStmt.setString(3, expireDate);
				 preparedStmt.setInt(4,Integer.parseInt(cvc));
				 preparedStmt.setDouble(5,Double.parseDouble(totalAmount));
				 preparedStmt.setInt(6,Integer.parseInt(paymentID));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 String newItems = readPayment();
				 output = "{\"status\":\"success\", \"data\": \"" +
				 newItems + "\"}";
			 }
			 catch (Exception e){
				 output = "{\"status\":\"error\", \"data\":\"Error while updating.\"}";
				 System.err.println(e.getMessage());
			 }
			 return output;
		} 
		
		public String deletePayment(String paymentID){
		 
			 String output = "";
			 
			 try {
					 
				 Connection con = connect();
				 
				 if (con == null){
					 return "Error while connecting to the database for deleting."; 
				 }
				 
				 // create a prepared statement
				 String query = "delete from payment where paymentID=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(paymentID));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 String newItems = readPayment();
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
