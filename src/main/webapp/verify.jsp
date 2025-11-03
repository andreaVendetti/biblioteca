<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Biblioteca</title>
	</head>
	<body>
		<c:choose>
			<c:when test="${utente == null}">
					<c:redirect url="index.jsp"/>
			</c:when>
		</c:choose>
	</body>
</html>