<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="verify.jsp" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Biblioteca</title>
		<%@ include file="WEB-INF/bootstrap_link.jsp" %>
		<link rel="stylesheet" href="<c:url value='/css/sfondo.css' />">
	</head>
	<body>
		<div class="d-flex justify-content-center align-items-center vh-100">
			<div class="bg-white bg-opacity-90 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 400px;">
			 
				<h2 class="text-center">
					<c:choose> 
				  		<c:when test="${libro != null}">
				  			Modifica Libro
				  		</c:when>
				  		<c:otherwise>
				  			Nuovo Libro
				  		</c:otherwise>
				  	</c:choose>
				</h2>
				<div class="text-center my-4">
					<form action="saveLibro" method="post">
						<input type="hidden" name="id" value="${libro.getId()}">
						<div>
							<div class="my-3">
								<label class="form-label">Titolo</label>
								
								<input class="form-control text-center" type="text" name="titolo" maxlength="30" value="${libro.getTitolo()}" >
							</div>
							<div class="my-3">	
								<label class="form-label text-start">Genere</label>
								
								<input class="form-control text-center" type="text" name="genere" maxlength="30" value="${libro.getGenere()}" >
							</div>
							<div class="my-3">
								<label>Autore</label>
							
								<select class="form-select" name="opzione_autori">
									<c:forEach var="autore" items="${autori }">
										<option  value="${autore.getId() }">${autore.getNome()} ${autore.getCognome()}</option>
									</c:forEach>
										<option value="">Sconosciuto</option>
								</select>
							</div>
							<div class="my-3">
								<label class="form-label">Disponibilita</label>
							
								<input class="form-control text-center" type="number" name="disp" maxlength="30" value="${libro.getDisponibilita()}">
							</div>
							<div class="my-3">
								<!-- se elimina è true allora faccio visualizzare il bottone elimina -->
							
								<c:choose>
									<c:when test="${libro != null }">
										<button class="btn btn-danger" type="submit" name="eliminaButton" value="${elimina }">Elimina</button>
									</c:when>
								</c:choose>
								
								<button class="btn btn-primary" type="submit" name="modificaButton">Invia</button>
							</div>
							
							<a class="btn btn-secondary" href="research">Torna indietro</a>
						</div>
					</form>
				</div>
			</div>		
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>