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
		<div class="d-flex justify-content-center align-items-center py-4">
			<div class="bg-white bg-opacity-75 p-4 p-md-5 rounded shadow-sm w-100" >
				<form class="search-form row g-2 align-items-center" action="research" method="get">
						<div class="my-3">
							<select class="form-select-sm" name="options">
								<option  selected="selected" value="">Seleziona una scelta</option>
								<option value="tutti">Tutti i libri</option>
								<option value="l.titolo">Titolo</option>
								<option value="l.genere">Genere</option>
								<option value="a.cognome">Autore(cognome)</option>
								<option value="a.nome">Autore(nome)</option>
							</select>  
							<select class="form-select-sm" name="orders">
								<option value=" ">Seleziona una scelta</option>
								<option value="l.titolo%asc">Titolo asc</option>
								<option value="l.titolo%desc">Titolo disc</option>
								<option value="l.genere%asc">Genere asc</option>
								<option value="l.genere%desc">Genere disc</option>
								<option value="a.nome%asc">Autore nome asc</option>
								<option value="a.nome%desc">Autore nome disc</option>
								<option value="a.cognome%asc">Autore cognome asc</option>
								<option value="a.cognome%desc">Autore cognome disc</option>
								<option value="l.disponibilita%asc">Disponibilit&agrave; asc</option>
								<option value="l.disponibilita%desc">Disponibilit&agrave; disc</option>
							</select>
							<input class="form-control-sm" type="text" name="valore" maxlength="50">
		
							<button class="btn btn-primary btn-sm" type="submit">Ricerca</button>
						</div>
					<div class="table-responsive">
						<table class="table table-hover table-sm w-100">
							<thead>
								<tr class="table-info">
									<th>Copertina</th>
									<th>Titolo</th>
									<th>Genere</th>
									<th>Nome Autore</th>
									<th>Copie Disponibili</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${libri != null}">
										<c:forEach var="libro" items="${libri}">
											<tr class="my-2">
												<td hidden="hidden">
													<input type="hidden" name="id" value="${libro.getId()}">
												</td>
												<td>
													<c:choose>
														<c:when test="${libro.copertina == null }">
															<img class="img-responsive" height="100px" width="100px" alt="copertina" src="img/libri/generale.png" >
														</c:when>
														<c:otherwise>
															<img class="img-responsive" height="100px" width="100px" alt="copertina" src="${libro.getCopertina() }">
														</c:otherwise>
													</c:choose>
												</td>
												<td>${libro.getTitolo().toUpperCase()}</td>
												<td>${libro.getGenere().toUpperCase()}</td>
												<td>${libro.getAutore().getNome().toUpperCase()}
													${libro.getAutore().getCognome().toUpperCase()}
												</td>
												<td>${libro.getDisponibilita()}</td>
												<td>
													<c:choose>
														<c:when test="${utente.isAdmin()}">
															<a class="btn btn-primary btn-sm" href="editLibro?id=${libro.getId()}&idElimina=${true }">Modifica</a>
															<!-- gli passo un valore true per dirgli che lo devo eliminare -->
														</c:when>
														<c:when test="${libro.getDisponibilita() == 0 }">
															<p class="badge bg-danger">Libro non disponibile</p>
														</c:when>
														<c:otherwise>
															<a class="btn btn-primary btn-sm" href="edit_prestito.jsp?idLibro=${libro.getId()}&titolo=${libro.titolo}">Prestito</a>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:when test="${libri == null && avviso == null}">
										<tr>
											<td colspan="4" align="center"><p>Libro non trovato</p></td>
										</tr>
									</c:when>
									<c:when test="${ avviso != null}">
										<tr>
											<td colspan="4" align="center">${avviso }</td>
										</tr>
									</c:when>
								</c:choose>
							</tbody>
						</table>
							<div>
							    <c:if test="${page > 1}">
							        <a href="research?page=${page - 1}&size=${pageSize}&options=${param.options}&valore=${param.valore}">« Prev</a>
							    </c:if>
							    <span>Pagina ${page}</span>
							    <c:if test="${libri.size() == pageSize}">
							        <a href="research?page=${page + 1}&size=${pageSize}&options=${param.options}&valore=${param.valore}">Next »</a>
							    </c:if>
							</div>
					</div>
					<c:choose>
						<c:when test="${utente.isAdmin()}">
							<div>
								<a class="btn btn-primary" href="editLibro">Inserisci un libro</a>
							</div>
		
						</c:when>
					</c:choose>
		
					<p>Per restituire un libro andare nella pagina dello storico</p>
		
					<div class="my-2">
						<a class="btn btn-secondary" href="home.jsp">Torna indietro</a>
					</div>
				</form>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>