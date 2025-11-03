package it.cefi.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.cefi.dao.AutoreDAO;
import it.cefi.models.Autore;
import it.cefi.repositories.AutoreRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/saveAutore")
public class AutoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Autore autore = null;
		String nome = request.getParameter("nomeAutore");
		String cognome = request.getParameter("cognomeAutore");
		
		if(nome != null && cognome != null) {
			autore = new Autore(nome, cognome);
			try {
				AutoreRepository autoreDAO = new AutoreDAO();
				autoreDAO.createAutore(autore);
			} catch (ClassNotFoundException | SQLException e) {
				Logger.getLogger("autore_log").log(Level.WARNING, e.getMessage());
			}
		}
		
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
	
	
	
}
