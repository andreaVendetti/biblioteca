package it.cefi.repositories;

import java.sql.SQLException;
import java.util.List;

import it.cefi.models.Autore;

public interface AutoreRepository {

	public Autore getAutore(int id) throws SQLException;
	
	public void createAutore(Autore autore) throws SQLException;
	
	public void deleteAutore(int id) throws SQLException;
	
	public List<Autore> getAllAutori() throws SQLException;
	
	
	
}
