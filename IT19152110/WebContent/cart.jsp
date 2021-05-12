<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@page import = "com.cart" %>
    
    <%
	  //Save---------------------------------
	    if (request.getParameter("cart_id") != null){
		    
	    	cart cartObj = new cart();
		    String stsMsg = "";
		    
		    //Insert--------------------------
		    if (request.getParameter("hidItemIDSave") == ""){
		    	
			    stsMsg = cartObj.insertCart(request.getParameter("cart_id"),
			    request.getParameter("product_id"),
			    request.getParameter("added_date"),
			    request.getParameter("qty"));
		    }
		    else{//Update----------------------

			    stsMsg = cartObj.updateCart(request.getParameter("hidItemIDSave"),
			    request.getParameter("product_id"),
			    request.getParameter("added_date"),
			    request.getParameter("qty"));
		    }
		    
		    session.setAttribute("statusMsg", stsMsg);
		}
    
	    //Delete-----------------------------
	    if (request.getParameter("hidItemIDDelete") != null){
		   
	    	cart cartObj = new cart();
		    String stsMsg =
		    cartObj.deleteCart(request.getParameter("hidItemIDDelete"));
		    session.setAttribute("statusMsg", stsMsg);
	    }
	    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart Service</title>
<link rel="stylesheet" href="View/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/cart.js"></script>
</head>
<body>
	<form id="formCart" name="formCart" method="post" action="cart.jsp">
	Product ID:
	<input id="product_id" name="product_id" type="text" class="form-control form-control-sm">
	<br> Added date:
	<input id="added_date" name="added_date" type="text" class="form-control form-control-sm">
	<br> Quantity:
	<input id="qty" name="qty" type="text" class="form-control form-control-sm">
	<br>
	<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
	<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
	</form>
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	<br>
	<div id="divItemsGrid">
	<%
	cart cartObj = new cart();
	out.print(cartObj.readCart());
	%>
	</div>
</body>
</html>