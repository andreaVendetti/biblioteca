package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.UtenteDAO;
import it.cefi.models.Utente;
import it.cefi.repositories.UtenteRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/utenti")
public class ListaUtentiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			UtenteRepository utenteDAO = new UtenteDAO();
			List<Utente> listaUtenti = utenteDAO.getUtenti();
			 
			HttpSession session = request.getSession();
			Utente utente = (Utente) session.getAttribute("utente");
			
			if(utente.getRuolo() == 2) {
				String idSlave = request.getParameter("slaveButton");
				if(idSlave != null) {
					//prendo l'id intero dell'admin slave e lo faccio diventare un admin
					int idS = Integer.parseInt(idSlave);
					Utente slave = utenteDAO.getUtente(idS);
					// se l'email ha un dominio preciso ovvero 'biblioteca' allora può diventare un admin
					if(slave.getEmail().matches("[a-zA-Z0-9].[a-zA-Z0-9_.\\+\\-%]{3,60}@biblioteca{1}\\.[a-zA-Z]{2,5}")) {
						slave.setRuolo(1);
						utenteDAO.updateUtente(slave);
						// se l'attributo non esiste refresha la pagina e ne crea uno
						if(session.getAttribute("successo") == null) {
							
							session.setAttribute("successo", "L'utente " + slave.getNome() + " " + slave.getCognome() + " e' appena diventato un admin");
							
							//Imposta un'intestazione di risposta con il nome e il valore intero specificati. 
							//Se l'intestazione era già stata impostata, il nuovo valore sovrascrive quello precedente. 
							//Il metodo containsHeader può essere utilizzato per verificare la presenza di un'intestazione 
							//prima di impostarne il valore.
							response.setIntHeader("Refresh", 0);
							// gli dico dove deve andare
							response.setContentType("utenti");
						}
						
					} else {
						session.setAttribute("fallimento",  "L'utente " + slave.getNome() + " " + slave.getCognome() + " non può diventare un admin");
					}
					
				}
	
			}
			
			
			request.setAttribute("clienti", listaUtenti);
			request.getRequestDispatcher("utenti.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger("utente_log").log(Level.WARNING, e.getMessage());
		}

	}

}
