<!DOCTYPE html>
<%@page import="it.cefi.dao.UtenteDAO"%>
<%@page import="it.cefi.repositories.UtenteRepository"%>
<%@page import="it.cefi.models.Utente"%>
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
			  <h2>
			  	<c:choose> 		
			  		<c:when test="${utente.id > 0}">
			  	
			  			Modifica Utente
			  		</c:when>
			  		
			  		<c:otherwise>
			  			Nuovo Utente
			  		</c:otherwise>
			  	</c:choose>
			  </h2>
			  
			  <form action="save" method="post">
					<div class="text-center">
						<input type="hidden" name="id" value="${utente.id}">
						<div class="my-3">
							<label class="form-label">Nome</label>
							<br>
							<input class="form-control text-center" size="60" type="text" name="nome" maxlength="30" value="${utente.nome}" required>
						</div>
						<div class="my-3">
							<label class="form-label">Cognome</label>
							<br>
							<input class="form-control text-center" type="text" name="cognome" maxlength="30" value="${utente.cognome}" required>
						</div>
						<div class="my-3">
							<label class="form-label">Username</label>
							<br>
							<input class="form-control text-center" type="text" name="user" maxlength="30" value="${utente.username}"required>
						</div>
						<div class="my-3">
    						<label class="form-label">Email</label>
    						<br>
    						<input class="form-control text-center" type="email" name="email" maxlength="50" value="${utente.email}" placeholder="esempio@dominio.com" required>
						</div>
						<div class="my-3">
							<label class="form-label">Password</label>
							<br>
							<input class="form-control text-center" type="password" name="password" maxlength="30" value="${utente.password}" required>
						</div>
						<input type="hidden" name="ruolo" value="${utente.ruolo}">
						<button class="btn btn-primary my-2" type="submit">Invia</button>
						<br>
						<c:choose> 
				  			<c:when test="${utente.id > 0}">
								<a class="btn btn-primary my-2" href="home.jsp">Torna indietro</a>
				  			</c:when>
				  			<c:otherwise>
				  				<a class="btn btn-secondary my-2" href="index.jsp">Torna indietro</a>
				  			</c:otherwise>
				  		</c:choose>
					</div>	
				</form>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>