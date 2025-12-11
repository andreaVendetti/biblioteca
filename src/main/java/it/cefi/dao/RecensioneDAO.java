package it.cefi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.cefi.models.Libro;
import it.cefi.models.Recensione;
import it.cefi.models.Utente;
import it.cefi.repositories.RecensioneRepository;

public class RecensioneDAO implements RecensioneRepository {

	//questo costruttore chiama la classe Class che tramite il metodo forName forza la connessione al connettore/driver
			public RecensioneDAO() throws ClassNotFoundException{
				Class.forName("com.mysql.cj.jdbc.Driver");
			}
		
	
	@Override
	public List<Recensione> getRecensioniByLibro(int offset, int idLibro) throws SQLException {
		List<Recensione> recensioni = new ArrayList<>();
		try(Connection connection = Connessione.getConnection()){
			String sql = "select r.id, u.username, r.utente, r.libro, r.data_recen, r.testo from recensioni as r"
					   + " join utenti as u on r.utente = u.id where r.libro = ? limit 10 offset ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, idLibro);
			statement.setInt(2, offset);
			ResultSet set =  statement.executeQuery();
			while(set.next()) {
				recensioni.add(new Recensione(set.getInt("id"),
											  new Libro(set.getInt("r.libro"), null, null, 0, null, null, null), 
											  new Utente(set.getInt("r.utente"),set.getString("u.username")), 
											  LocalDate.parse(set.getString("r.data_recen")),  set.getString("r.testo")));
			}
			
		}
		return recensioni;
	}

	@Override
	public List<Recensione> orderBy(int offset) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void add(Recensione recensione) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "insert into recensioni(utente, libro, data_recen, testo) values (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
		}
		
	}


	@Override
	public void update(int idRecensione) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(int idRece) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
