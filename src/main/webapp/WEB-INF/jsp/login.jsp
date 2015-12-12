<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Bbstats Login</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<style>
body {
	padding-top: 70px;
	/* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
}

.center form {
	margin: 0 auto;
}

.top-buffer { margin-top:20px; }
</style>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Start Bbstats</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="#">About</a></li>
					<li><a href="#">Services</a></li>
					<li><a href="#">Contact</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>

	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="col-lg-12 text-center">
				<h1>Bbstats home</h1>
				<p class="lead">Put your baseball stats online!</p>
				<ul class="list-unstyled">
					<li>Bootstrap v3.3.6</li>
					<li>jQuery v1.11.1</li>
				</ul>
				<h2>Please login</h2>

			</div>
		</div>
		<div class="row">


			<form action="/login" method="post" role="form"
				class="form-horizontal">


				<div class="form-group">
					<label for="username" class="control-label col-md-4">Username:</label>
					<div class="col-sm-4">
						<input name="username" type="text" id="username"
							class="form-control" placeholder="User Name" required autofocus />
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword" class="control-label col-md-4">Password:</label>
					<div class="col-sm-4">
						<input name="password" type="password" id="inputPassword"
							class="form-control" placeholder="Password" />
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="col-md-4 col-md-offset-4">
					<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
						in</button>
				</div>
			</form>
			

		</div>
		<!-- /.row -->
		<div class="row top-buffer">
				<div class="col-lg-12 text-center">
					<c:if test="${param.error ne null}">
						<p>Invalid username and password.</p>
					</c:if>
					<c:if test="${param.logout ne null}">
						<p>You have been logged out.</p>
					</c:if>
				</div>
			</div>

	</div>
	<!-- /.container -->

	<!-- jQuery Version 1.11.1 -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>
