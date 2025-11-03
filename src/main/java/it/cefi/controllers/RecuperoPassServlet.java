package it.cefi.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import it.cefi.dao.UtenteDAO;
import it.cefi.models.Utente;
import it.cefi.repositories.UtenteRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/recupero")
public class RecuperoPassServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String N8N_URL = "https://andrea22.app.n8n.cloud/webhook-test/avviso-biblioteca";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String email = request.getParameter("email");
		String reset = request.getParameter("resetButton");
		HttpSession session = request.getSession();
		//se l'email esiste e reset no allora creo un thread per inviare l'email
		//creo il thread per evitare collisioni
		if(email != null && reset == null) {
			new Thread(() -> inviaEmailN8n(email)).start();
			session.setAttribute("attendi", "controlla l'email");
		}
		
		if(reset != null) {
			boolean res = Boolean.parseBoolean(reset);
			if(res) {
				String pass = request.getParameter("password");
				try {
					UtenteRepository utenteDAO = new UtenteDAO();
					//recupero l'utente tramite l'email setto la password e la codifico
					Utente utente = utenteDAO.getUtenteByEmail(request.getParameter("paramEmail"));
					utente.setPassword(pass);
					utenteDAO.encrypt(utente);
					
					if(session.getAttribute("attendi").equals("controlla l'email")) {
						session.setAttribute("attendi", "la modifica è avvenuta con successo");
						response.setIntHeader("Refresh", 0);
						response.setContentType("index.jsp");
					}
					
				} catch (ClassNotFoundException | SQLException | InvalidKeyException 
						| NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
						| BadPaddingException | InvalidAlgorithmParameterException e) {
					Logger.getLogger("reset_log").log(Level.WARNING, e.getMessage());
				}
			}
			
		}
		response.sendRedirect("index.jsp");
	}

	private void inviaEmailN8n(String email) {
		try {
			// mi creo una url passandogli quella di n8n
			URL url = new URL(N8N_URL);
			// mi creo un oggetto httpUrlConnection per aprirmi una connessione con la metodologia http
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setDoOutput(true);
			
			String json = String.format("{\"email\":\"%s\"}", email);
			
			try(OutputStream os = conn.getOutputStream()){
				byte[] input = json.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}
			System.out.println("Email inviata via n8n. Codice HTTP: " + conn.getResponseCode());
			conn.disconnect();
			
		} catch (IOException e) {
			Logger.getLogger("n8n_log").log(Level.WARNING, e.getMessage());
		}
	}
}
