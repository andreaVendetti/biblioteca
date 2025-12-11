package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.LibroDAO;
import it.cefi.dao.RecensioneDAO;
import it.cefi.models.Recensione;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/visualBook")
public class LibroRecensioneServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("libroId");
		String pageParam = request.getParameter("page"); // parametro preso dalla url
		int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
		
		
		int offset = (page - 1) * PAGE_SIZE;
		int idLibro;
		idLibro = (id != null && !id.isBlank()) ? Integer.parseInt(id) : 0;
		if(idLibro != 0) {
			try {
				RecensioneDAO dao = new RecensioneDAO();
				List<Recensione> recensioni = dao.getRecensioniByLibro(offset, idLibro);
				LibroDAO daoL = new LibroDAO();
				for(Recensione r : recensioni) {
					r.setLibro(daoL.getLibro(r.getLibro().getId()));
				}
				
				request.setAttribute("recensioni", recensioni);
				request.getRequestDispatcher("libro.jsp").forward(request, response);
				
			} catch (ClassNotFoundException | SQLException e) {
				Logger.getLogger("recensione_logger").log(Level.WARNING, e.getMessage());
			}
		} else {
			
		}
		
	}
	
}
