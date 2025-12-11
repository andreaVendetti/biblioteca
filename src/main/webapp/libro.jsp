<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="verify.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Biblioteca</title>
		<%@ include file="WEB-INF/bootstrap_link.jsp" %>
		<link rel="stylesheet" href="<c:url value='/css/sfondo.css' />">
	</head>
	<body>
		<div class="d-flex justify-content-center align-items-center vh-100">
  			<div class="bg-white bg-opacity-75 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 400px;">
				
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>