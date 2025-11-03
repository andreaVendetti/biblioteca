package it.cefi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.cefi.models.Prestito;
import it.cefi.repositories.PrestitoRepository;
import it.cefi.repositories.UtenteRepository;

public class PrestitoDAO implements PrestitoRepository {


		
		//questo costruttore chiama la classe Class che tramite il metodo forName forza la connessione al connettore/driver
			public PrestitoDAO() throws ClassNotFoundException{
				Class.forName("com.mysql.cj.jdbc.Driver");
			}
	
	@Override
	public Prestito getPrestito(int id) throws SQLException, ClassNotFoundException {
		Prestito prestito;
		try(Connection connection = Connessione.getConnection()){
			String sql = "select p.id, p.id_utente, l.titolo, p.id_libro, p.data_prestito, p.data_restituzione "
						+ "from prestiti as p inner join libri as l  on p.id_libro = l.id "
						+ "inner join utenti as u on u.id = p.id_utente where p.id = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			set.next();
			UtenteRepository utenteDAO = new UtenteDAO();
			prestito = new Prestito(set.getInt("p.id"),
									utenteDAO.getUtente(set.getInt("p.id_utente")), 
									set.getString("l.titolo"), 
									set.getInt("p.id_libro"),
									LocalDate.parse(set.getString("p.data_prestito")),
									verifyDataRitorno(set.getString("p.data_restituzione")));
//			prestito = new Prestito(set.getInt("p.id"),
//									set.getObject(new Utente(set.getInt("p.id_utente"), 
//															 set.getString("u.nome"), 
//															 set.getString("u.cognome"))), 
//									id, null, null);
		}
		return prestito;
	}

	@Override
	public List<Prestito> getAllPrestiti() throws SQLException, ClassNotFoundException {
		List<Prestito> prestiti = new ArrayList<>();
		try(Connection connection = Connessione.getConnection()){
			String sql = "select p.id, p.id_utente, l.titolo, p.id_libro, p.data_prestito, p.data_restituzione "
					+ "from prestiti as p inner join libri as l  on p.id_libro = l.id "
					+ "inner join utenti as u on u.id = p.id_utente";
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(sql);
			UtenteRepository utenteDAO = new UtenteDAO();
			while(set.next()) {		
					prestiti.add(new Prestito(set.getInt("p.id"),
							utenteDAO.getUtente(set.getInt("p.id_utente")), 
							set.getString("l.titolo"), 
							set.getInt("p.id_libro"),
							LocalDate.parse(set.getString("p.data_prestito")),
							verifyDataRitorno(set.getString("p.data_restituzione"))));					
			}
		}
		
		return prestiti.isEmpty() ? null : prestiti;
	}

	// da completare
	@Override
	public void addPrestito(Prestito prestito) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "insert into prestiti(id_utente, id_libro, data_prestito, data_restituzione) values (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, prestito.getUtente().getId());
			statement.setInt(2, prestito.getIdLibro());
			statement.setString(3, prestito.getRitiro().toString());
			statement.setString(4, prestito.getRitorno() != null ? prestito.getRitorno().toString() : null);
			
			
			statement.executeUpdate();
		}
		
	}

	@Override
	public void updatePrestito(Prestito prestito) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "update prestiti set data_prestito = ?, data_restituzione = ? where id = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, prestito.getRitiro().toString());
			statement.setString(2, prestito.getRitorno() != null ? prestito.getRitorno().toString() : null);
			statement.setInt(3, prestito.getIdPrestito());
			statement.executeUpdate();
		}
	}

	//permette di vedere tutti i libri di un determinato utente
	@Override
	public List<Prestito> researchBy(int id) throws SQLException, ClassNotFoundException {
		List<Prestito> prestiti = new ArrayList<>();
		try(Connection connection = Connessione.getConnection()){
			String sql = "select p.id, p.id_utente, l.titolo, p.id_libro, p.data_prestito, p.data_restituzione "
					+ "from prestiti as p inner join libri as l  on p.id_libro = l.id "
					+ "inner join utenti as u on u.id = p.id_utente where p.id_utente = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			UtenteRepository utenteDAO = new UtenteDAO();

			while(set.next()) {		
					prestiti.add(new Prestito(set.getInt("p.id"),
							utenteDAO.getUtente(set.getInt("p.id_utente")), 
							set.getString("l.titolo"), 
							set.getInt("p.id_libro"),
							LocalDate.parse(set.getString("p.data_prestito")),
							verifyDataRitorno(set.getString("p.data_restituzione"))));					
			}
			
		}
		return prestiti.isEmpty() ? null : prestiti;

	}

	
	// se dataRitorno è null rimane tale altrimenti lo cambia in data
	@Override
	public LocalDate verifyDataRitorno(String dataRitorno) {
		return dataRitorno == null ? null : LocalDate.parse(dataRitorno);
	}

	
}
