<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://thymleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Welcome</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
    
    
     <link th:href="@{/css/home.css}" rel="stylesheet" />
</head>

 <section id="top">
        <div class="container1">

            <div class="heading">

                <center>
                    <h2>Furniture Store</h2>
                </center>

            </div>
        </div>
    </section>


<body>
	<div class="container">
		
		
		
		<div class="welcome"><center><h2  >Welcome  [[${user.username}]]</center></h2></div>
		
		
		<hr class="featurette-divider">
		
		<a class="btn btn-link btn-lg" href="/furniture/products" role="button">Product Inventory</a></br>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;Here you can view and search our product inventory!</p>
		
		<a class="btn btn-link btn-lg" href="/furniture/cart" role="button">Shopping Cart</a></br>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;Here you can view your current Shopping Cart.</p>
		
		<a class="btn btn-link btn-lg" href="/furniture/orders" role="button">Your Orders</a></br>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;Here you can view your current and past orders.</p>
		
		<hr class="featurette-divider">
	</div>
	
	
	<div class="row">
		<div class="col-5">
		</div>
		<div class="col">
			<a class="btn btn-primary btn-lg" href="/logout" role="button">Logout</a></br>
		</div>
		<hr class="featurette-divider">
		
	</div>
	
	<footer class="footer">
      <div class="container">
        <span class="text-muted"> &nbsp;&nbsp;&copy;2021 Furniture Store by Divya </span>
      </div>
    </footer>
</body>
</html>