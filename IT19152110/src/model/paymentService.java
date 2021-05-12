package model;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.payment;

@Path("/payments") 
public class paymentService {

	payment paymentObj = new payment();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment(){
		
	 return paymentObj.readPayment();
	}	
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertPayment(@FormParam("paymentID ") String paymentID, 
	@FormParam("cardNo") String cardNo, 
	@FormParam("nameOnCard") String nameOnCard, 
	@FormParam("expireDate") String expireDate, 
	@FormParam("CVC") String CVC,
	@FormParam("totalAmount") String totalAmount)
	{ 
		
		String output = paymentObj.insertPayment(paymentID, cardNo, nameOnCard, expireDate, CVC, totalAmount); 
		return output; 
		
	}
}
