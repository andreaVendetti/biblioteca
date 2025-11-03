<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- per formattare la data --> 
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
			<div class="bg-white bg-opacity-75 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 400px;">
				<!-- Banner di avviso -->
				<c:if test="${not empty sessionScope.avvisiPrestiti}">
				    <div class="alert alert-danger">
				        <strong>Attenzione!</strong> Hai prestiti in ritardo:
				        <ul>
				            <c:forEach var="prestito" items="${sessionScope.avvisiPrestiti}">
				                <li>
				                    ${prestito.titolo} ritirato il 
				                    <fmt:formatDate value="${prestito.ritiroAsDate}" pattern="dd/MM/yyyy"/>
				                    <c:choose>
				                        <c:when test="${prestito.ritornoAsDate != null}">
				                            , restituito il <fmt:formatDate value="${prestito.ritornoAsDate}" pattern="dd/MM/yyyy"/>
				                        </c:when>
				                        <c:otherwise>
				                            , non ancora restituito
				                        </c:otherwise>
				                    </c:choose>
				                </li>
				            </c:forEach>
				        </ul>
				    </div>
				    <c:remove var="avvisiPrestiti" scope="session"/>
				</c:if>
			
				<h1>Cosa vuoi fare?</h1>
				<div class="text-center p-4">
					<c:choose>
						<c:when test="${utente.isAdmin() }">
							<i class="bi bi-person-fill-gear h2"></i>
						</c:when>
						<c:otherwise>
							<i class="bi bi-person-fill h2"></i>
						</c:otherwise>
					</c:choose>
					<div>
						<a class="btn btn-primary my-3" href="edit?id=${utente.getId()}">Modifica i tuoi dati personali</a>
					</div>
					<div>
						<a class="btn btn-primary my-3" href="research">Ricerca libri</a>
					</div>
					<div>
						<a class="btn btn-primary my-3" href="storico?id=${utente.getId()}">Storico dei prestiti</a>
					</div>
					<c:choose>
						<c:when test="${utente.isAdmin()}">
							<div>
							<a class="btn btn-primary my-3" href="utenti">Visualizza gli utenti</a>
							</div>
							<div>
								<a class="btn btn-primary my-3" href="edit_autori.jsp">Editor autori</a>
							</div>
						</c:when>
					</c:choose>
					<div>
						<a class="btn btn-danger" href="index.jsp">Logout</a>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>