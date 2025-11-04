package it.cefi.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.PrestitoDAO;
import it.cefi.dao.UtenteDAO;
import it.cefi.models.Prestito;
import it.cefi.models.Utente;
import it.cefi.repositories.PrestitoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/avviso")
public class AvvisoServlet extends HttpServlet {

	private static final long serialVersionUID = 5703879079981092205L;
	String webhookUrl = System.getenv("N8N_WEBHOOK_URL");


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String avviso = request.getParameter("avvisoButton");
		if (avviso != null) {
			int idUtente = Integer.parseInt(avviso);
			try {
				PrestitoRepository prestitoDAO = new PrestitoDAO();
				List<Prestito> prestitiInRitardo = prestitoDAO.researchBy(idUtente)
						.stream()
						.filter(Prestito::isLate)
						.toList();

				if (!prestitiInRitardo.isEmpty()) {
					UtenteDAO dao = new UtenteDAO();
					dao.impostaAvvisoInviato(idUtente); //Banner sulla home (resta invariato)

					//Recupero i dati dell’utente per inviare l’email
					Utente utente = dao.getUtente(idUtente);

					//Invio dell’email a n8n (in un thread separato)
					for (Prestito p : prestitiInRitardo) {
						new Thread(() -> inviaEmailN8n(utente, p.getTitolo())).start();
					}
				}

				response.sendRedirect("home.jsp");
			} catch (ClassNotFoundException | SQLException e) {
				Logger.getLogger("avviso_log").log(Level.WARNING, e.getMessage());
			}
		}
	}

	private void inviaEmailN8n(Utente utente, String titoloLibro) {
		try {
			URL url = new URL(webhookUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setDoOutput(true);

			String json = String.format(
				"{\"nome\":\"%s\", \"cognome\":\"%s\", \"email\":\"%s\", \"titolo\":\"%s\"}",
				utente.getNome(),
				utente.getCognome(),
				utente.getEmail(),
				titoloLibro
			);

			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = json.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			System.out.println("Email inviata via n8n. Codice HTTP: " + conn.getResponseCode());
			conn.disconnect();
		} catch (Exception e) {
			Logger.getLogger("n8n_log").log(Level.WARNING, e.getMessage());
		}
	}
}
