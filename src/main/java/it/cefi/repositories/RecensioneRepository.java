package it.cefi.repositories;

import java.sql.SQLException;
import java.util.List;

import it.cefi.models.Recensione;

public interface RecensioneRepository {

	void add(Recensione recensione) throws SQLException;
	
	void update(int idRecensione) throws SQLException;
	
	void delete(int idRece) throws SQLException;
	
	List<Recensione> getRecensioniByLibro(int offset, int idLibro) throws SQLException;
	
	List<Recensione> orderBy(int offset) throws SQLException;
	
}
