package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.LibroDAO;
import it.cefi.models.Libro;
import it.cefi.repositories.LibroRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/research")
public class ResearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// quanti libri devo visualizzare
	private static final int PAGE_SIZE = 10;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String option = request.getParameter("options"); // select
		String valore = request.getParameter("valore"); // input
		String ordine = request.getParameter("orders"); // ordine
		
		String pageParam = request.getParameter("page"); // parametro preso dalla url
		int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
		
		List<Libro> listaLibri = null;
		int offset = (page - 1) * PAGE_SIZE;
		try {
			LibroRepository libroDAO = new LibroDAO();
			// se l'opzione non è stata selezionata prende una lista di tutti i libri
			// altrimenti esegue la ricerca
			if (option != null) {

				
				if (option.equalsIgnoreCase("tutti")) {

					listaLibri = libroDAO.getLibri(offset);

				} else if (option != null || option.matches("[a-zA-Z]")) {

					listaLibri = libroDAO.researchBy(option, valore, offset);

				}

				
				// se l'ordine è stato selezionato ordina la lista
				if (ordine != null && listaLibri != null) {
					boolean ord = Boolean.parseBoolean(ordine);
					listaLibri = libroDAO.orderBy(listaLibri, ord);
				}
			
		
			} else {
				request.setAttribute("avviso", "Per vedere i libri fai una ricerca");
			}

		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger("libro_log").log(Level.WARNING, e.getMessage());
		}

		request.setAttribute("page", page);
		request.setAttribute("libri", listaLibri);
		request.setAttribute("pageSize", PAGE_SIZE);
		request.getRequestDispatcher("research.jsp").forward(request, response);
	}

}
