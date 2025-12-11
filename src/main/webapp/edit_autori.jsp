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
			<div class="bg-white bg-opacity-90 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 400px;">
				<div class="text-center p-4">
					<form action="saveAutore" method="post">
						<div class="my-3">
							<label class="form-label">Nome</label>
							<br>
							<input class="form-control text-center" size="60" type="text" name="nomeAutore" maxlength="30" value="${utente.nome}" required>
						</div>
						<div class="my-3">
							<label class="form-label">Cognome</label>
							<br>
							<input class="form-control text-center" type="text" name="cognomeAutore" maxlength="30" value="${utente.cognome}" required>
						</div>
						<button class="btn btn-primary" type="submit">Aggiungi</button>
					</form>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp" %>
	</body>
</html>