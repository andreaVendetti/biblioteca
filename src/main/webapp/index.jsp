<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		
				<div class="text-center p-4">					
					
					<h1 class="display-5">Benvenuto nella biblioteca web</h1>
					<c:choose>
						<c:when test="${attendi != null }">
							<div class="my-5">
								<h2 class="alert alert-primary">
									${attendi }
								</h2>
							</div>
						</c:when>
					</c:choose>
					
					<div class="my-5">
						<a class="btn btn-primary btn-lg w-100 mb-2" href="edit">Registrati</a>
					</div>
					
					<div class="my-5">
						<a class="btn btn-primary btn-lg w-100" href="login.jsp">Accedi</a>
					</div>
			
				</div>
			
			</div>	
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>