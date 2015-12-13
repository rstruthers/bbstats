<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="pgTitle" value="Create User" scope="request" />
<%@ include file="header.jsp" %>
<div class="container">
    <h1>Create User</h1>
    
	
     
     <spring:url value="/user/create" var="userActionUrl" />
     <form:form method="post" modelAttribute="form" action="${userActionUrl}" class="form-horizontal">

		<spring:bind path="username">
		  <div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">User Name</label>
			<div class="col-sm-10">
				<form:input path="username" type="text" class="form-control" 
                                id="username" placeholder="User Name" />
				<form:errors path="username" class="control-label" />
			</div>
		  </div>
		</spring:bind>
	</form:form>
    
     </div>    
        
   
<%@ include file="footer.jsp" %>