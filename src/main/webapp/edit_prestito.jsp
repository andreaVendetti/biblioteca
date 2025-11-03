<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="verify.jsp" %>
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
				<div class="text-center p-4">
					<c:choose>
				 	 	<c:when test="${errore != null}">
				 	 		Errore
				 	 		<p class="alert alert-danger">${errore}</p>
				 	 	</c:when>
				 	 </c:choose>
					<form action="savePrestito" method="get">
						<input type="hidden" name="idPrestito" value="${param.idPrestito }"> 
						<div class="my-3">
							<label class="form-label">Id Utente</label>
							<br>
							<input class="form-control" type="number" name="idUtente" value="${utente.getId()}" required>
						</div>
						<div class="my-3">
							<label class="form-label">Id Libro</label>
							<br>
							<input class="form-control" type="number" name="idLibro" value="${param.idLibro}" required>
						</div>
							<c:choose>
								<c:when test="${param.idPrestito == null || param.idPrestito == 0}">
									<div class="my-3">
										<label class="form-label">Data ritiro</label>
										<br>
										<input class="form-control" type="date" name="dataRitiro" required>
									</div>
								</c:when>
								<c:otherwise>
									<div class="my-3">
										<label class="form-label">Data restituzione</label>
										<br>
										<input class="form-control" type="date" name="dataRestituzione" required >
									</div>
								</c:otherwise>
							</c:choose>
						<button class="btn btn-primary btn-sm my-3" type="submit">Invia</button>
					</form>
						<a class="btn btn-secondary btn-sm" href="research">Torna indietro</a>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>