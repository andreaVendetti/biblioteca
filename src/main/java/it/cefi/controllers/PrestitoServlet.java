package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.LibroDAO;
import it.cefi.dao.PrestitoDAO;
import it.cefi.dao.UtenteDAO;
import it.cefi.models.Libro;
import it.cefi.models.Prestito;
import it.cefi.models.Utente;
import it.cefi.repositories.LibroRepository;
import it.cefi.repositories.PrestitoRepository;
import it.cefi.repositories.UtenteRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/savePrestito")
public class PrestitoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Prestito prestito; // creazione prestito vuoto
		String idLibro = request.getParameter("idLibro"); // prendo l'id del libro, utente e prestito
//		String idUtente = request.getParameter("idUtente");
		//prendo l'utente della sessione e prendo i suoi campi 
		int idUtente = ((Utente) request.getSession().getAttribute("utente")).getId();
		String idPrestito = request.getParameter("idPrestito");
		int idPs;
		// controllo l'id se è nullo gli do un valore di 0 altrimenti lo trasformo in
		// int
		if (idLibro != null && !idLibro.isEmpty()) {
			try {
				if (idPrestito == null || idPrestito.isBlank()) {
					idPs = 0;
				} else {
					idPs = Integer.parseInt(idPrestito);
				}
				PrestitoRepository prestitoDAO = new PrestitoDAO();

				int idL = Integer.parseInt(idLibro);

				Libro libro;
				LibroRepository libroDAO = new LibroDAO();
				UtenteRepository utenteDAO = new UtenteDAO();
				Utente utente = utenteDAO.getUtente(idUtente);
				libro = libroDAO.getLibro(idL);

				// se l'id del prestito è maggiore di zero fa l'update altrimenti
				//l'add MA è DA COMPLETARE
				String dataRest = request.getParameter("dataRestituzione");
				if (idPs == 0) {
					prestito = new Prestito(idPs, 
											utente, 
											libro.getTitolo(),
											idL,
											LocalDate.parse(request.getParameter("dataRitiro")),
											prestitoDAO.verifyDataRitorno(dataRest));
					
					prestitoDAO.addPrestito(prestito);
					libro.setDisponibilita(libro.getDisponibilita() - 1);
					libroDAO.updateLibro(libro);
					response.sendRedirect("storico");
				} else {
					prestito = prestitoDAO.getPrestito(idPs);
					prestito.setRitorno(prestitoDAO.verifyDataRitorno(dataRest));
					
					// se la data inserita è prima della data di ritiro do un errore
					if(prestito.getRitiro().isAfter(prestito.getRitorno())) {
						request.setAttribute("errore", "La data inserita è prima della data di ritiro");
						request.getRequestDispatcher("edit_prestito.jsp").forward(request, response);
					} else {
						prestitoDAO.updatePrestito(prestito);
						libro.setDisponibilita(libro.getDisponibilita() + 1);
						libroDAO.updateLibro(libro);
						response.sendRedirect("storico");
						
					}
					
				}

				
				
				//request.getRequestDispatcher("storico?id=" + idUtente).forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				Logger.getLogger("prestito_log").log(Level.WARNING, e.getMessage());
			}
		}

	}

}
