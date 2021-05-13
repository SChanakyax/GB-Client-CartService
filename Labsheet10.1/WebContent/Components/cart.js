$(document).ready(function()
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 


// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 

if (status != true) 
 { 
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
data : $("#formItem").serialize(), 
dataType : "text", 
complete : function(response, status) 
{ 
onItemSaveComplete(response.responseText, status); 
} 
}); 
}); 
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
	$("#hidItemIDSave").val($(this).data("cartid")); 
 $("#productname").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#description").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#size").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#price").val($(this).closest("tr").find('td:eq(3)').text()); 
 $("#type").val($(this).closest("tr").find('td:eq(4)').text()); 
});


$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "CartAPI", 
		 type : "DELETE", 
		 data : "cartid=" + $(this).data("cartid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onItemDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});


//CLIENT-MODEL================================================================
function validateItemForm() 
{ 
// CODE
if ($("#productname").val().trim() == "") 
 { 
 return "Insert Item Code."; 
 } 
// NAME
if ($("#description").val().trim() == "") 
 { 
 return "Insert Item Name."; 
 } 
//PRICE-------------------------------
if ($("#price").val().trim() == "") 
 { 
 return "Insert Item Price."; 
 } 
// is numerical value
var tmpPrice = $("#price").val().trim(); 
if (!$.isNumeric(tmpPrice)) 
 { 
 return "Insert a numerical value for Item Price."; 
 } 
// convert to decimal price
 $("#price").val(parseFloat(tmpPrice).toFixed(2)); 
// DESCRIPTION------------------------
if ($("#type").val().trim() == "") 
 { 
 return "Insert Item Description."; 
 } 
return true; 
}

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
$("#formItem")[0].reset(); 
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
