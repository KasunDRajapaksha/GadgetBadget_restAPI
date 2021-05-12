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
		url : "CartAPI",
		type : type,
		data : $("#formCart").serialize(),
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
		$("#formCart")[0].reset();
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
	 $("#product_id").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#added_date").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#qty").val($(this).closest("tr").find('td:eq(2)').text());
	});
	
	
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
	url : "CartAPI",
	type : "DELETE",
	data : "cart_id=" + $(this).data("itemid"),
	dataType : "text",
	complete : function(response, status)
	{
	onItemDeleteComplete(response.responseText, status);
	}
	});
})	
	
	// CLIENT-MODEL================================================================
function validateItemForm()
	{
		// product ID
		if ($("#product_id").val().trim() == "")
		 {
		 return "Insert product ID.";
		 }
		
		// added date
		if ($("#added_date").val().trim() == "")
		 {
		 return "Insert added date.";
		 } 
		
		// quantity-------------------------------
		if ($("#qty").val().trim() == "")
		 {
		 return "Insert quantity.";
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