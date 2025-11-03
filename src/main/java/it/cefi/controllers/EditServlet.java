package it.cefi.controllers;

import java.io.IOException;
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

/*
Mapping: /edit Tipologia: GET Funzione: Riceve un parametro id, legge l’utente corrispondente e lo invia alla JSP per la modifica
*/
@WebServlet("/edit")
public class EditServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utente utente = new Utente(); // Oggetto utente vuoto (nuovo)
		String idParam = request.getParameter("id");// leggo id dal request

		// Se nella URL mi arriva un parametro id (es. edit?id=5)
		if (idParam != null && !idParam.isEmpty()) {
			try {
				int id = Integer.parseInt(idParam);
				UtenteRepository utenteDAO = new UtenteDAO();
				utente = utenteDAO.getUtente(id);
				utente.setPassword(utenteDAO.decrypt(utente.getId()));
			} catch (BadPaddingException | ClassNotFoundException | InvalidAlgorithmParameterException 
					| IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | SQLException e) {
				Logger.getLogger("utente_log").log(Level.WARNING, e.getMessage());
			}
		}

		// metto l'utente nel request
		request.setAttribute("utente", utente);
		// passo il controllo alla JSP
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}
}
