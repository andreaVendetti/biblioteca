<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Biblioteca</title>
		<%@ include file="WEB-INF/bootstrap_link.jsp"%>
		<link rel="stylesheet" href="<c:url value='/css/sfondo.css' />">
	</head>
	<body>
		<div class="d-flex justify-content-center align-items-center vh-100">
			<div class="bg-white bg-opacity-90 p-4 p-md-5 rounded shadow-sm w-100" style="max-width: 400px;">
				<div class="text-center">
					<form action="recupero" method="post">
					 	<h2 class="text-primary">Recupero Password</h2>	 
						<div class="my-3">
							<c:choose>
								<c:when test="${param.reset == false }">
									<label class="form-label">Inserisci la tua email</label>
									<br>
									<input class="form-control text-center" type="text" name="email" size="60" required>
									<button class="btn btn-primary my-3" name="sendButton" type="submit">Invia</button>
								</c:when>		
								<c:otherwise>
									<div class="my-3">
										<label class="form-label">Inserisci una nuova password</label>
																				<input type="hidden" name="paramEmail" value="${param.email }">
										<input class="form-control text-center" type="password" name="password" size="60" required>
										
										<button class="btn btn-primary my-3" name="resetButton" value="${param.reset }" type="submit">Invia</button>
									</div>
								</c:otherwise>					
							</c:choose>
						</div>
					</form>
				</div>
			</div>
		</div>
		<%@ include file="WEB-INF/bootstrap_script.jsp"%>
	</body>
</html>