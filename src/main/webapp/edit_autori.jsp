<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Biblioteca</title>
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
	</body>
</html>