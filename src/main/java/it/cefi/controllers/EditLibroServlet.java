package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

@WebServlet("/editLibro")
public class EditLibroServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Libro libro = null; 
		String idLibro = request.getParameter("id");// recupero l'id dalla url
		if (idLibro != null && !idLibro.isEmpty()) {
			try {
				int id = Integer.parseInt(idLibro);
				LibroRepository libroDAO = new LibroDAO();
				libro = libroDAO.getLibro(id); //prendo il libro
			} catch (ClassNotFoundException | SQLException e) {
				
			}
		}
		List<Autore> autori = new ArrayList<>();
		try {
			AutoreRepository autoreDAO = new AutoreDAO();
			autori.addAll(autoreDAO.getAllAutori());
		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger("autore-log").log(Level.WARNING, e.getMessage());
		}
		
		//prendo idElimina da research e lo passo come attributo a edit.jsp
		String idElimina = request.getParameter("idElimina");
		boolean elimina = false;
		if(idElimina != null) {
			elimina = Boolean.parseBoolean(idElimina);
		}
		
		request.setAttribute("elimina", elimina);
		request.getSession().setAttribute("autori", autori);
		request.setAttribute("libro", libro);
		request.getRequestDispatcher("edit_libro.jsp").forward(request, response);
	}
}
