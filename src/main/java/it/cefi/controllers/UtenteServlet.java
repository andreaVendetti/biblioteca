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

@WebServlet("/save")
public class UtenteServlet extends HttpServlet {

	private static final long serialVersionUID = -9194032541147351268L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("id") != null) {
			
			// creo un utente che prenndendo i dati dal form
			Utente utente = new Utente(Integer.parseInt(request.getParameter("id")), 
									   request.getParameter("nome"),
									   request.getParameter("cognome"),
									   request.getParameter("user"),
									   request.getParameter("password"),
									   Integer.parseInt(request.getParameter("ruolo")),
									   request.getParameter("email"));
			try {
				// creo un utente dao con l'interfaccia repository(per il possibile riutilizzo)
				// in modo tale da utilizzare i metodi
				UtenteRepository utenteDAO = new UtenteDAO();
				// se l'utente ha id 0 vuo dire che bisogna fare un insert altrimenti bisogna
				// aggiornare un utente
//				if (utente.getId() == 0) {
//					utenteDAO.encrypt(utente);
//				} else {
//				
//					utenteDAO.updateUtente(utente);
//					
//				}
				utenteDAO.encrypt(utente);
				request.setAttribute("idUtente", utente.getId());

				//se l'utente non ha id 0 va su home 
				String test = utente.getId() == 0 ? "index.jsp" : "home.jsp";
			
				
			
				request.getRequestDispatcher(test).forward(request, response);

			} catch (BadPaddingException | ClassNotFoundException | IllegalBlockSizeException 
					| InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | SQLException 
					| InvalidAlgorithmParameterException e) {
				Logger.getLogger("utente_log").log(Level.WARNING, e.getMessage());

			}
		}
	}

}
