<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="pgTitle" value="List Users" scope="request" />
<%@ include file="header.jsp" %>
    
	<div class="container">
     <h1>All Users</h1>
     <table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Name</th>
					<th>Email</th>
				</tr>
			</thead>

			<c:forEach var="user" items="${users}">
			    <tr>
				<td>
					${user.id}
				</td>
				<td>${user.username}</td>
				<td>${user.email}</td>
			    </tr>
			</c:forEach>
		</table>
     </div>    
        
   
<%@ include file="footer.jsp" %>