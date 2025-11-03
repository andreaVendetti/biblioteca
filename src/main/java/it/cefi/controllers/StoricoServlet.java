package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.PrestitoDAO;
import it.cefi.dao.UtenteDAO;
import it.cefi.models.Prestito;
import it.cefi.models.Utente;
import it.cefi.repositories.PrestitoRepository;
import it.cefi.repositories.UtenteRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/storico")
public class StoricoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Prestito> storico = null;
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		if(utente != null) {
			int idUtente = utente.getId();
			//int idU = Integer.parseInt(idUtente);
			try {
				PrestitoRepository prestitoDAO = new PrestitoDAO();
				UtenteRepository utenteDAO = new UtenteDAO();
				utente = utenteDAO.getUtente(idUtente);
				if(utente.isAdmin()) {
					storico = prestitoDAO.getAllPrestiti();
				} else {
					storico = prestitoDAO.researchBy(idUtente);					
				}
				
				
			} catch (ClassNotFoundException | SQLException e) {
				Logger.getLogger("storico_log").log(Level.WARNING, e.getMessage());
			}
			
		}
	
		request.setAttribute("listaStorico", storico);
		request.getRequestDispatcher("storico.jsp").forward(request, response);
	}
	
}
