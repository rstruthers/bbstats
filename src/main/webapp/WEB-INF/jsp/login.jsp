<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="header.jsp" %>

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

	


<%@ include file="footer.jsp" %>
