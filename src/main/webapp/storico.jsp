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
		<div class="d-flex justify-content-center align-items-center py-4">
			<div class="bg-white bg-opacity-75 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 650px;">
				<div class="table-responsive">
					<table class="table table-hover table-sm w-100">
						<thead>
							<tr class="table-info">
								<c:choose>
									<c:when test="${utente.isAdmin() }">
										<th>Utente</th>
									</c:when>
								</c:choose>
									<th>Libro</th>
									<th>Data ritiro</th>
									<th>Data restituzione</th>
							</tr>
						</thead>
						<tbody> 
							<c:choose>
								<c:when test="${listaStorico != null}">
									<c:forEach var="prestito" items="${listaStorico}">
										<tr>
											<c:choose>
												<c:when test="${utente.isAdmin()}">
													<td>${prestito.getUtente().getNome()} ${prestito.getUtente().getCognome()}</td>
												</c:when>
											</c:choose>
											<td>${prestito.getTitolo() }</td>
											<td>${prestito.getRitiro() }</td>
											<td>${prestito.getRitorno() }</td>
											<td>
											<c:choose>
													<c:when test="${prestito.getRitorno() == null && utente.isAdmin() == false }">
														<td><a class="btn btn-primary" href="edit_prestito.jsp?idLibro=${prestito.getIdLibro()}&idPrestito=${prestito.getIdPrestito()}">Restituisci</a></td>
													</c:when>
													
													<c:when test="${utente.isAdmin() && prestito.isLate() }">
														<form action="avviso" method="post">
																<button class="btn btn-warning" type="submit" name="avvisoButton" value="${prestito.getUtente().getId() }">Invia l'avviso</button>
														</form>
													</c:when>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:when test="${ listaStorico == null}">
									<tr>
										<td colspan="4"><p class="alert alert-secondary text-center">Non ci sono prestiti</p></td>
									</tr>
								</c:when>
							</c:choose>
						</tbody>
					</table>
				</div>
				<a class="btn btn-secondary" href="home.jsp">Torna indietro</a>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>