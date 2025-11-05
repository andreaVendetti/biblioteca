package it.cefi.repositories;

import java.sql.SQLException;
import java.util.List;

import it.cefi.models.Libro;


public interface LibroRepository {
	
	List<Libro> getLibri(int offset) throws SQLException;

	List<Libro> orderBy(String regex, int offset) throws SQLException;//da fare dopo

	List<Libro> researchBy(String ricerca, String valore, int offset) throws SQLException;

	Libro getLibro(int id) throws SQLException;
	
	void deleteLibro(int id) throws SQLException;

	void updateLibro(Libro libro) throws SQLException;

	void addLibro(Libro libro) throws SQLException;

	
	
}
