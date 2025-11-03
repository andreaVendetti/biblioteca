package it.cefi.controllers;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 5423729263711166866L;

	/*
	 * Perché utilizziamo il metodo forward() in caso di errore

	->forward(): VANTAGGI!

	1-Non crea una nuova richiesta HTTP,

	2-Mantiene gli attributi (request.setAttribute("errore", "messaggio")),

	3-Ci permette di mostrare un messaggio direttamente in login.jsp senza che noi perdiamo i dati.

	AL CONTRARIO!
	->sendRedirect(): VANTAGGI:

	1-Dice al nostro browser di fare una nuova richiesta,

	2-Perde tutti gli attributi request,

	3-Però per noi è adatto quando vuoi spostarti su un’altra pagina in modo definitivo,
	come dopo un login corretto.
	 */
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//			UtenteRepository utenteDAO = new UtenteDAO();
//			List<Utente> listaUtenti = utenteDAO.getUtenti();
//
//			boolean trovato = false;
//			for (Utente u : listaUtenti) {
//				String user = request.getParameter("username");
//				String pass = request.getParameter("password");
//				
//				//se pass ed user corrispondono si entra
//				if (u.getUsername().equalsIgnoreCase(user) && u.getPassword().equalsIgnoreCase(pass)) {
//				
//					request.getSession().setAttribute("utente", u);
//					response.sendRedirect("home.jsp");
//					trovato = true;
//				}
//			}
//			//se non è stato trovato nessun utente
//			if(!trovato) {
//				request.setAttribute("errore", "Username o Password errati");
//				request.getRequestDispatcher("login.jsp").forward(request, response);
//			}
//
//		} catch (ClassNotFoundException | SQLException e) {
//			Logger.getLogger("utente_log").log(Level.WARNING, e.getMessage());
//		}
//	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			UtenteRepository utenteDAO = new UtenteDAO();
			List<Utente> listaUtenti = utenteDAO.getUtenti();
			
			boolean trovato = false;
			String user = request.getParameter("username");
			String pass = request.getParameter("password");
			
			 for (Utente u : listaUtenti) {
	                
				 if (u.getUsername().equalsIgnoreCase(user) && utenteDAO.decrypt(u.getId()).equalsIgnoreCase(pass)) {
	                    trovato = true;
	                    HttpSession session = request.getSession();
	                    session.setAttribute("utente", u);

	                    // Mostra il banner SOLO se l'admin ha inviato l'avviso
	                    if (u.getAvvisoInviato() == 1) {
	                        PrestitoRepository prestitoDAO = new PrestitoDAO();
	                        List<Prestito> prestitiInRitardo = prestitoDAO.researchBy(u.getId());
	                        session.setAttribute("avvisiPrestiti", prestitiInRitardo);

	                        // Resetto l'avviso (lo mostro solo una volta)
	                        UtenteDAO dao = new UtenteDAO();
	                        dao.resetAvviso(u.getId());
	                    }

	                    response.sendRedirect("home.jsp");
	                    break;
	                }
	            }
			//se non è stato trovato nessun utente
			if(!trovato) {
				request.setAttribute("errore", "Username o Password errati");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			
		} catch (BadPaddingException | ClassNotFoundException | InvalidAlgorithmParameterException 
				| IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | SQLException e) {
			Logger.getLogger("utente_log").log(Level.WARNING, e.getMessage());
		}
	}

}
