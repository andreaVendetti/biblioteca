package it.cefi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.cefi.models.Autore;
import it.cefi.models.Libro;
import it.cefi.repositories.LibroRepository;

public class LibroDAO implements LibroRepository {

	// connessione che avviene tramite l'aggiunta del connettore nella cartella
	// webapp/WEB-INF/lib/mysql-connector ecc
	// e quest'URL con user e password

	// questo costruttore chiama la classe Class che tramite il metodo forName forza
	// la connessione al connettore/driver
	public LibroDAO() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

	// restituisce una lista di tutti i libri della tabella
	@Override
	public List<Libro> getLibri(int offset) throws SQLException {
		List<Libro> listaLibri = new ArrayList<>();
		try (Connection connection = Connessione.getConnection()) {
			String sql = "select l.id, l.titolo, l.genere, l.disponibilita, a.id, a.nome, a.cognome, a.id "
						+"from libri as l " 
						+ "left join autori as a " 
						+ "on a.id = l.autore "
						+ " limit 10 offset ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, offset);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				listaLibri.add(new Libro(set.getInt("id"),
										 set.getString("titolo"), 
										 set.getString("genere"), 
										 set.getInt("disponibilita"),
										 new Autore(set.getInt("a.id"), 
										 set.getString("a.nome"), 
										 set.getString("a.cognome"))));
			}
		}

		return listaLibri;
	}
	
	//modificato il metodo orderBy
	@Override
	public List<Libro> orderBy(String regex, int offset) throws SQLException {
		List<Libro> ordinati = new ArrayList<>();
		String[] ordine = regex.split("%");
		String campo = ordine[0];
		String ord = ordine[1].equalsIgnoreCase("desc") ? "desc" : "asc";
		if(!campo.matches("[a-zA-Z_.]+")) {
			campo = "l.id";
		}
		try (Connection connection = Connessione.getConnection()) {
			String sql = "select l.id, l.titolo, l.genere, l.disponibilita, a.id, a.nome, a.cognome, a.id "
						+"from libri as l " 
						+ "left join autori as a " 
						+ "on a.id = l.autore "
						+ " order by " + campo + " " + ord 
						+ " limit 10 offset ?";
			PreparedStatement statement = connection.prepareStatement(sql);			
			statement.setInt(1, offset);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				ordinati.add(new Libro(set.getInt("id"),
										 set.getString("titolo"), 
										 set.getString("genere"), 
										 set.getInt("disponibilita"),
										 new Autore(set.getInt("a.id"), 
										 set.getString("a.nome"), 
										 set.getString("a.cognome"))));
			}
		}
//		prima versione di ordinamento
//		if(ordine) {
//			ordinati = lista.stream().sorted((l1, l2) -> l1.getTitolo().compareToIgnoreCase(l2.getTitolo())).toList();
//		} else {
//			ordinati = lista.stream().sorted((l1, l2) -> l1.getTitolo().compareToIgnoreCase(l2.getTitolo())).toList().reversed();
//		}
		
		return ordinati;
	}

	//metodo dinamico che permette di ricercare tramite un campo che associamo
	@Override
	public List<Libro> researchBy(String ricerca, String valore, int offset) throws SQLException {
		List<Libro> listaLibri = new ArrayList<>();

		try (Connection connection = Connessione.getConnection()) {
			//mi creo uno StringBuilder per aggiungere ad esso le percentuali per la query
			StringBuilder builder = new StringBuilder(valore);
			builder.insert(0, "%");
			builder.append("%");
			String sql = "select l.id, l.titolo, l.genere, l.disponibilita, a.id, a.nome, a.cognome " +
		             "from libri as l " +
		             "inner join autori as a on a.id = l.autore " +
		             "where " + ricerca + " like ? " + 
		             " limit 10 offset ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, builder.toString());
			statement.setInt(2, offset);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				listaLibri.add(new Libro(set.getInt("id"),
										 set.getString("titolo"), 
										 set.getString("genere"), 
										 set.getInt("disponibilita"),
										 new Autore(set.getInt("a.id"), 
										 set.getString("a.nome"), 
										 set.getString("a.cognome"))));
			}
		}
		//se la lista è vuota  restituisc null
		if(listaLibri.isEmpty()) {
			return null;
		} else {
			return listaLibri;			
		}
	}


	//metodo che serve per eliminare un libro
	@Override
	public void deleteLibro(int id) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "delete from libri where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
		}

	}

	//metodo che serve per aggiornare un libro
	@Override
	public void updateLibro(Libro libro) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "update libri set titolo = ?, genere = ?, autore = ?, disponibilita = ?  where id = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, libro.getTitolo().toLowerCase());
			statement.setString(2, libro.getGenere().toLowerCase());
			
			if(libro.getIdAutore() != null) {
				statement.setInt(3, libro.getIdAutore());				
			} else {
				//inseriamo null se autore non selezionato
				statement.setNull(3, java.sql.Types.INTEGER);
			}
			statement.setInt(4, libro.getDisponibilita());
			statement.setInt(5, libro.getId());
			statement.executeUpdate();
		}

	}

	@Override
	public void addLibro(Libro libro) throws SQLException {
		try(Connection connection = Connessione.getConnection()){
			String sql = "insert into libri(titolo, genere, autore, disponibilita) values (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, libro.getTitolo().toLowerCase());
			statement.setString(2, libro.getGenere().toLowerCase());
			if(libro.getIdAutore() != null) {
				statement.setInt(3, libro.getIdAutore());				
			} else {
				//inseriamo null se autore non selezionato
				statement.setNull(3, java.sql.Types.INTEGER);
			}
			statement.setInt(4, libro.getDisponibilita());
			statement.executeUpdate();
		}

	}

	@Override
	public Libro getLibro(int id) throws SQLException {
		Libro libro;
		try(Connection connection = Connessione.getConnection()){
			String sql = "select * from libri where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			set.next();
			libro = new Libro(set.getInt("id"),
							  set.getString("titolo"), 
							  set.getString("genere"), 
							  set.getInt("disponibilita"), 
							  new Autore(set.getInt("autore"),
									  	 null, 	
									  	 null));
		}
		return libro;
	}
// funziona 
//	@Override
//	public Libro getLibro(int id) throws SQLException {
//	    Libro libro = null;
//	    try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
//	        String sql = "select l.id, l.titolo, l.genere, l.disponibilita, "
//	                   + "a.id as autore_id, a.nome as autore_nome, a.cognome as autore_cognome "
//	                   + "from libri as l "
//	                   + "inner join autori as a on a.id = l.autore "
//	                   + "where l.id = ?";
//
//	        PreparedStatement statement = connection.prepareStatement(sql);
//	        statement.setInt(1, id);
//
//	        ResultSet set = statement.executeQuery();
//	        if (set.next()) {
//	            libro = new Libro(
//	                set.getInt("id"),
//	                set.getString("titolo"),
//	                set.getString("genere"),
//	                set.getInt("disponibilita"),
//	                new Autore(
//	                    set.getInt("autore_id"),
//	                    set.getString("autore_nome"),
//	                    set.getString("autore_cognome")
//	                )
//	            );
//	        }
//	    }
//	    return libro;
//	}




}
