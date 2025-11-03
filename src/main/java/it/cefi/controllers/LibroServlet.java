package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.AutoreDAO;
import it.cefi.dao.LibroDAO;
import it.cefi.models.Autore;
import it.cefi.models.Libro;
import it.cefi.repositories.AutoreRepository;
import it.cefi.repositories.LibroRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/saveLibro")
public class LibroServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Autore autore = null; // creo un autore null
		String idAutore = request.getParameter("opzione_autori"); //prendo l'idAutore
		if (idAutore != null && !idAutore.isEmpty()) { // se l'id autore non è null e non è vuoto
			try {
				int id = Integer.parseInt(idAutore);
				AutoreRepository autoreDAO = new AutoreDAO();
				autore = autoreDAO.getAutore(id); // recuperiamo l'autor dal DB
			} catch (ClassNotFoundException | SQLException e) {
				Logger.getLogger("autore_log").log(Level.WARNING, e.getMessage());
			}
		}
		
		//Passiamo autore.getId() solo se non è null, altrimenti passiamo null
		Integer autoreId = (autore != null) ? autore.getId() : null;
		
		
		String libroId = request.getParameter("id");
		int idLibro = (libroId != null && !libroId.isBlank()) ? Integer.parseInt(libroId) : 0;
		
		//passiamo autoreId preso precedentemente perchè così può essere null
		Libro libro = new Libro(idLibro, 
							    request.getParameter("titolo"),
						 	    request.getParameter("genere"), 
						 	    Integer.parseInt(request.getParameter("disp")), 
						 	    autoreId);
		
		//prendo il valore all'interno di eliminaButton e controllo che non sia null e a seguire faccio il cast
		String elimina = request.getParameter("eliminaButton");
		boolean idElimina = false;
		if(elimina != null) {
			 idElimina = Boolean.parseBoolean(elimina);		
		}
		
		//se idUtente è diverso da  0 ed elimina è true  allora elimino il libro altrimenti lo modifico
		try {
			LibroRepository libroDAO = new LibroDAO();
			
			if(libro.getId() != 0 &&  idElimina == false) {
				libroDAO.updateLibro(libro);
			} else if( libro.getId() != 0 && idElimina == true) {
					libroDAO.deleteLibro(libro.getId());// se il libro è collegato alla tabella prestiti non lo elimina quindi è da controllare
			} else {
				libroDAO.addLibro(libro);
			}
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger("libro_log").log(Level.WARNING, e.getMessage());
		}
		
	}
}
