package it.cefi.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import it.cefi.models.Prestito;

public interface PrestitoRepository {

	Prestito getPrestito(int id) throws SQLException, ClassNotFoundException;
	
	List<Prestito> getAllPrestiti() throws SQLException, ClassNotFoundException;
	
	void addPrestito(Prestito prestito) throws SQLException;
	
	void updatePrestito(Prestito prestito) throws SQLException;
	
	List<Prestito> researchBy(int id) throws SQLException, ClassNotFoundException;
	
	LocalDate verifyDataRitorno(String dataRitorno);
}
