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
			<div class="bg-white bg-opacity-90 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 400px;">
				<div class="text-center">
					<form action="login" method="post">
						 	 <c:choose>
						 	 	<c:when test="${errore != null}">
						 	 		<div class="alert alert-danger" role="alert">
						 	 			Errore
						 	 			<p>${errore}</p>
						 	 		</div>
						 	 	</c:when>
						 	 </c:choose>
						 <h2 class="text-primary">Accedi</h2>	 
						<div class="my-3">
							<label class="form-label">Username</label>
							<br>
							<input class="form-control text-center" type="text" name="username" size="60" required>
						</div>
						<div class="my-3">
							<label class="form-label">Password</label>
							<br>
							<input class="form-control text-center" type="password" name="password"  required>
						</div>
						<button class="btn btn-primary" type="submit">Accedi</button>
						<br>
						<a class="btn btn-primary my-3" href="recupero_pass.jsp?reset=${false }">Password dimenticata?</a>
					</form>
					<a class="btn btn-secondary my-3" href="index.jsp">Torna indietro</a>
					<br>
					<label class="my-3">Non sei registrato?</label>
					<br>
					<a class="btn btn-primary my-2" href="edit">Clicca qui!</a>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>