<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="header.jsp" %>



	<div class="container">
		<h1>bbstats home</h1>
		<c:choose>
			<c:when test="${empty currentUser}">
				<a href="${contextPath}/login">Login to bbstats</a>
			</c:when>
			<c:otherwise>
        Welcome ${currentUser.username}<br>
				<a href="${contextPath}/angular-demo.html">Angular demo</a>
				
			</c:otherwise>
		</c:choose>
	</div>
<%@ include file="footer.jsp" %>