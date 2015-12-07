<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Bare - Start Bootstrap Template</title>

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
</style>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

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
				<a class="navbar-brand" href="#">Start Bootstrap</a>
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
				<h1>A Bootstrap Starter Template</h1>
				<p class="lead">Complete with pre-defined file paths that you
					won't have to change!</p>
				<ul class="list-unstyled">
					<li>Bootstrap v3.3.6</li>
					<li>jQuery v1.11.1</li>
				</ul>
			</div>
		</div>
		<div class="row">
				<div ng-app="myApp" ng-controller="myCtrl">
					<form role="form" class="form-horizontal">
						<div class="form-group">
							<label for="firstName" class="control-label col-md-4">First Name:</label>
							<div class="col-sm-4">
							<input type="text" class="form-control" id="firstName" ng-model="firstName">
							</div>
						</div>
						<div class="form-group">
							<label for="lastName" class="control-label col-md-4">Last Name:</label>
							<div class="col-sm-4">
							<input type="text" class="form-control" id="lastName" ng-model="lastName">
							</div>
						</div>
					</form>
					<div class="col-md-12 text-center">
						Full Name: {{firstName + " " + lastName}}
					</div>
				</div>

				<script>
					var app = angular.module('myApp', []);
					app.controller('myCtrl', function($scope) {
						$scope.firstName = "John";
						$scope.lastName = "Doe";
					});
				</script>
	</div>
	<!-- /.row -->

	</div>
	<!-- /.container -->

	<!-- jQuery Version 1.11.1 -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>

</html>