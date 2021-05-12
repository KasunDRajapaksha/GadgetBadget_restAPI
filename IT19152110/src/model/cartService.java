package model;



import javax.ws.rs.core.MediaType;
//For REST Service
import javax.ws.rs.*;

import com.cart;
//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/carts") 
public class cartService {
	
		cart cartObj = new cart();
		
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readCart(){
			
		 return cartObj.readCart();
		}	
		
		@POST
		@Path("/") 
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String insertCart(@FormParam("cart_id") String cart_id, 
		@FormParam("product_id") String product_id, 
		@FormParam("added_date") String added_date, 
		@FormParam("qty") String qty)
		{ 
			
			String output = cartObj.insertCart(cart_id, product_id, added_date, qty); 
			return output; 
			
		}
		
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateCart(String itemData){
			
		//Convert the input string to a JSON object
		 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		 
		//Read the values from the JSON object
		 String cart_id = itemObject.get("cart_id").getAsString();
		 String product_id = itemObject.get("product_id").getAsString();
		 String added_date = itemObject.get("added_date").getAsString();
		 String qty = itemObject.get("qty").getAsString();
		 
		 String output = cartObj.updateCart(cart_id, product_id, added_date, qty );
		
		 return output;
		}
		
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteCart(String itemData)
		{
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		//Read the value from the element <itemID>
		 String cart_id = doc.select("cart_id").text();
		 String output = cartObj.deleteCart(cart_id);
		
		 return output;
		}


}


