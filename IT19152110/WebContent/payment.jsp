<%@page import="com.payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
	  //Save---------------------------------
	    if (request.getParameter("paymentID") != null){
		    
	    	payment paymentObj = new payment();
		    String stsMsg = "";
		    
		    //Insert--------------------------
		    if (request.getParameter("hidItemIDSave") == ""){
		    	
			    stsMsg = paymentObj.insertPayment(request.getParameter("paymentID"),
			    request.getParameter("cardNo"),
			    request.getParameter("nameOnCard"),
			    request.getParameter("expireDate"),
			    request.getParameter("cvc"),
			    request.getParameter("totalAmount"));
		    }
		    else{//Update----------------------

			    stsMsg = paymentObj.updatePayment(request.getParameter("hidItemIDSave"),
			    	request.getParameter("cardNo"),
					request.getParameter("nameOnCard"),
					request.getParameter("expireDate"),
					request.getParameter("cvc"),
					request.getParameter("totalAmount"));
		    }
		    
		    session.setAttribute("statusMsg", stsMsg);
		}
    
	    //Delete-----------------------------
	    if (request.getParameter("hidItemIDDelete") != null){
		   
	    	payment paymentObj = new payment();
		    String stsMsg =
		    paymentObj.deletePayment(request.getParameter("hidItemIDDelete"));
		    session.setAttribute("statusMsg", stsMsg);
	    }
	    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Service</title>
<link rel="stylesheet" href="View/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
	<form id="formPayment" name="formPayment" method="post" action="payment.jsp">
	Card No:
	<input id="cardNo" name="cardNo" type="text" class="form-control form-control-sm">
	<br> Name on card:
	<input id="nameOnCard" name="nameOnCard" type="text" class="form-control form-control-sm">
	<br> Expire date:
	<input id="expireDate" name="expireDate" type="text" class="form-control form-control-sm">
	<br> CVC:
	<input id="cvc" name="cvc" type="text" class="form-control form-control-sm">
	<br> Total amount:
	<input id="totalAmount" name="totalAmount" type="text" class="form-control form-control-sm">
	<br>
	<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
	<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
	</form>
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	<br>
	<div id="divItemsGrid">
	<%
	payment paymentObj = new payment();
	out.print(paymentObj.readPayment());
	%>
	</div>
</body>
</html>