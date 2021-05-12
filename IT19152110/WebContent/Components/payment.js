$(document).ready(function(){
	
	if ($("#alertSuccess").text().trim() == ""){
	 	$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event){
		
	// Clear alerts---------------------
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true){
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
	}
	
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
	{
		url : "PaymentAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onItemSaveComplete(response.responseText, status);
		}
	});
});
	
	
function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		} else if (status == "error")
		{
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else
		{
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
		}
		$("#hidItemIDSave").val("");
		$("#formPayment")[0].reset();
}
	
function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
	var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		} else if (status == "error")
		{
			$("#alertError").text("Error while deleting.");
			$("#alertError").show();
		} else
		{
			$("#alertError").text("Unknown error while deleting..");
			$("#alertError").show();
		}
}
	// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
	{
	 $("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	 $("#cardNo").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#nameOnCard").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#expireDate").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#cvc").val($(this).closest("tr").find('td:eq(3)').text());
	 $("#totalAmount").val($(this).closest("tr").find('td:eq(4)').text());
});
	
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
	url : "PaymentAPI",
	type : "DELETE",
	data : "paymentID=" + $(this).data("itemid"),
	dataType : "text",
	complete : function(response, status)
	{
	onItemDeleteComplete(response.responseText, status);
	}
	});
});	
	
	// CLIENT-MODEL================================================================
function validateItemForm()
	{
		// Card no
		if ($("#cardNo").val().trim() == "")
		 {
		 return "Insert Card no.";
		 }
		
		// Name on card
		if ($("#nameOnCard").val().trim() == "")
		 {
		 return "Insert Name on card.";
		 } 
		
		// quantity-------------------------------
		if ($("#expireDate").val().trim() == "")
		 {
		 return "Insert expire  date.";
		 }
		// CVC-------------------------------
		if ($("#cvc").val().trim() == "")
		 {
		 return "Insert CVC.";
		 }
		// Total amount-------------------------------
		if ($("#totalAmount").val().trim() == "")
		 {
		 return "Insert Total amount.";
		 }
		/*
		// is numerical value
		
		var tmpPrice = $("#itemPrice").val().trim();
		
		if (!$.isNumeric(tmpPrice))
		 {
		 return "Insert a numerical value for Item Price.";
		 }
		
		// convert to decimal price
		 $("#itemPrice").val(parseFloat(tmpPrice).toFixed(2));
		 
		// DESCRIPTION------------------------
		if ($("#itemDesc").val().trim() == "")
		 {
		 return "Insert Item Description.";
		 }*/
	return true;
}