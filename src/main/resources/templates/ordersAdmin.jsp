<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://thymleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Orders</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	 <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap5.min.css"  crossorigin="anonymous">
	<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"  crossorigin="anonymous"> </script>
	<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<h2 class="display-3"><font color="blue"> Orders</font></h2>
	<hr class="featurette-divider">
	
	<br>
	<table id="example" class="table table-striped table-bordered table-hover table-sm">
	<thead class="thead-dark">
		<tr>
			<th>ID</th>
			<th>Purchased On</th>
			<th>Card Number</th>
			<th>Billing Address</th>
			<th>Is Delivered?</th>
			<th>Change Status</th>
			<th>Cart Items</th>
		</tr>
	</thead>
	<tbody>
		<tr th:each="r,state : ${res}">
           <td th:text="${state.count}" />
           <td th:text="${r.purchasedOn}" />
           <td th:text="${r.paymentInfo.cardNumber}" />
           <td th:text="${r.paymentInfo.address}" />
           <td th:text="${r.delivered}" />
           <td>
           	<a th:href="@{/admin/changeStatus(transactionId=${r.tid})}" 
           	onclick="if (!(confirm('Are you sure you want to change the delivery status of this product?'))) return false;" class="btn btn-outline-success btn-sm">Change Status</a>	
           </td>
           <td>
           	<a th:href="@{/admin/viewCartItems(transactionId=${r.tid})}" class="btn btn-outline-info btn-sm">Cart Items</a>		
           </td>
	     </tr>
	</tbody>
	</table>
		<hr class="featurette-divider">
	
</div>
<br>
	<div class="container">
		<div class="row">
			<div class="col-10">
				<a class="btn btn-link btn-lg"  th:href="@{/home}">Back to Home Page</a>
			</div>
			<div class="col">
				<a class="btn btn-primary btn-lg" href="/logout" role="button">Logout</a></br>
			</div>
		</div>
		
	</div>
	
	<footer class="footer">
      <div class="container">
      	<hr class="featurette-divider">
        <span class="text-muted">&nbsp;&nbsp;&copy; 2021 Furniture Store by Divya </span>
      </div>
    </footer>
</body>
</html>