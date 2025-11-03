package it.cefi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.cefi.models.Autore;
import it.cefi.repositories.AutoreRepository;

public class AutoreDAO implements AutoreRepository {

	//questo costruttore chiama la classe Class che tramite il metodo forName forza la connessione al connettore/driver
		public AutoreDAO() throws ClassNotFoundException{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
	
	@Override
	public Autore getAutore(int id) throws SQLException {
		Autore autore; // mi dichiaro un autore 
		try(Connection connection = Connessione.getConnection()){
			String sql = "select * from autori where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			set.next();
			autore = new Autore(set.getInt("id"), set.getString("nome"), set.getString("cognome"));
		}
		return autore;
	}

	@Override
	public void createAutore(Autore autore) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "insert into autori(nome, cognome) values (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, autore.getNome().toLowerCase());
			statement.setString(2, autore.getCognome().toLowerCase());
			statement.executeUpdate();
		}
		
	}

	@Override
	public void deleteAutore(int id) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "delete from autori where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		}
		
	}

	@Override
	public List<Autore> getAllAutori() throws SQLException {
		List<Autore> lista = new ArrayList<>();
		try(Connection connection = Connessione.getConnection()){
			String sql = "select * from autori";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while(set.next()) {
				lista.add(new Autore(set.getInt("id"), set.getString("nome"), set.getString("cognome")));
			}
		}
		
		return lista;
	}

}
