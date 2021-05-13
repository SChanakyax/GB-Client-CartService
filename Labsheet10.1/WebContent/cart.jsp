<%@page import="com.CartController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/cart.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>CartManagement</h1><!--form to jsp pages --->
				<form id="formItem" name="formItem">
			<!--		Item code: <input id="cartid" name="cartid" type="text" class="form-control form-control-sm">  this is not used-->
					Item name: <input id="productname" name="productname" type="text" class="form-control form-control-sm"> <br> Item
					description: <input id="description" name="description" type="text"
						class="form-control form-control-sm"> Item size: <input
						id="size" name="size" type="text"
						class="form-control form-control-sm"> <br> Item
					price: <input id="price" name="price" type="text"
						class="form-control form-control-sm"> <br> Item type:
					<input id="type" name="type" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				<!-- succes msg -->
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
		CartController itemObj = new CartController(); 
	 		out.print(itemObj.readCart());
	%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>