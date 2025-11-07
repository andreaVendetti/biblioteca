<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="verify.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Biblioteca</title>
		<%@ include file="WEB-INF/bootstrap_link.jsp"%>
		<link rel="stylesheet" href="<c:url value='/css/sfondo.css' />">
	</head>
	<body>
		<div class="d-flex justify-content-center align-items-center py-4">
			<div class="bg-white bg-opacity-75 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 600px;">
				<div class="table-responsive">		
					<c:choose>
						<c:when test="${clienti != null}">
							<div>
								<table class="table table-hover table-sm w-100">
									<thead>
										<tr class="table-info">
											<th>Id</th>
											<th>Nome</th>
											<th>Cognome</th>
											<th>Username</th>
											<th>Admin</th>
											<th>Abilita</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="cliente" items="${clienti}">
											<tr>
												<td>${cliente.getId()}</td>
												<td>${cliente.getNome()}</td>
												<td>${cliente.getCognome()}</td>
												<td>${cliente.getUsername() }</td>
												<c:choose>
													<c:when test="${cliente.getRuolo() == 1 }">
														<td>Slave</td>
													</c:when>
													<c:when test="${cliente.getRuolo() == 2 }">
														<td>Master</td>
													</c:when>
													<c:otherwise>
														<td>User</td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${utente.getRuolo() == 2 && cliente.getRuolo() == 0}">
														<td>
															<form action="utenti" method="get">
																<button class="btn btn-success btn-sm" type="submit" name="slaveButton" value="${cliente.getId() }">Rendi uno Slave</button>
															</form>
														</td>
													</c:when>
													<c:when test="${cliente.getRuolo() == 1 }">
														<td>
															<button class="btn btn-success btn-sm" type="submit" name="slaveButton" value="${cliente.getId() }" disabled>Rendi uno Slave</button>
														</td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<c:choose>
								<c:when test="${fallimento != null }">
									Operazione non andata a buon fine.
									<p class="alert alert-warning">${fallimento }</p>	
									<c:remove var="fallimento" scope="session"/>
								</c:when>
								<c:when test="${successo != null }">
									Operazione andata a buon fine.
									<p class="alert alert-success">${successo }</p>
								</c:when>
							</c:choose>
						</c:when>
					</c:choose>
					<a class="btn btn-secondary" href="home.jsp">Torna indietro</a>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>