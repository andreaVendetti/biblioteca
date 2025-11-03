package it.cefi.dao;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import it.cefi.models.Utente;
import it.cefi.repositories.UtenteRepository;

public class UtenteDAO implements UtenteRepository {


	// questo costruttore chiama la classe Class che tramite il metodo forName forza
	// la connessione al connettore/driver
	public UtenteDAO() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

	// aggiunge un utente alla tabella utenti
	@Override
	public boolean addUtente(Utente utente) throws SQLException {
		boolean inserito = false;
		try (Connection connection = Connessione.getConnection()) {
			String sql = "insert into utenti(nome, cognome, username, password, ruolo, email) values (?, ?, ?, ?, ?, ?) ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, utente.getNome());
			statement.setString(2, utente.getCognome());
			statement.setString(3, utente.getUsername());
			statement.setString(4, utente.getPassword());
			statement.setInt(5, utente.getRuolo());
			statement.setString(6, utente.getEmail());

			statement.executeUpdate();
			inserito = true;
		} catch (Exception e) {
			Logger.getLogger("encrypt_log").log(Level.WARNING, e.getMessage());
		}
		return inserito;
	}

	// modifica un utente all'interno della tabella utenti
	@Override
	public void updateUtente(Utente utente) throws SQLException {
		try (Connection connection = Connessione.getConnection()) {
			String sql = "update utenti set nome = ?, cognome = ?,  username = ?,  password = ?, ruolo = ?, email = ? where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, utente.getNome());
			statement.setString(2, utente.getCognome());
			statement.setString(3, utente.getUsername());
			statement.setString(4, utente.getPassword());
			statement.setInt(5, utente.getRuolo());
			statement.setString(6, utente.getEmail());
			statement.setInt(7, utente.getId());
			statement.executeUpdate();
		}
	}

	// elimina un utente dalla tabella utenti
	@Override
	public void deleteUtente(int id) throws SQLException {
		try (Connection connection = Connessione.getConnection()) {
			String sql = "delete from utenti where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
		}

	}

	// retituisce una lista di utenti
	@Override
	public List<Utente> getUtenti() throws SQLException {
		List<Utente> listaUtenti = new ArrayList<>();
		try (Connection connection = Connessione.getConnection()) {
			String sql = "select * from utenti";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Utente utente = new Utente(set.getInt("id"), 
										   set.getString("nome"), 
										   set.getString("cognome"),
										   set.getString("username"),
										   set.getString("password"), 
										   set.getInt("ruolo"),
										   set.getString("email"));
				
				utente.setAvvisoInviato(set.getInt("avviso_inviato"));

				listaUtenti.add(utente);
			}
		}
		return listaUtenti;
	}

	// restituisce un utente
	@Override
	public Utente getUtente(int id) throws SQLException {
		Utente utente;
		try (Connection connection = Connessione.getConnection()) {
			String sql = "select *  from utenti where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			set.next();
			utente = new Utente(set.getInt("id"), 
								set.getString("nome"), 
								set.getString("cognome"),
								set.getString("username"), 
								set.getString("password"), 
								set.getInt("ruolo"), 
								set.getString("email"));
			
			utente.setAvvisoInviato(set.getInt("avviso_inviato"));

		}
		return utente;
	}

	// metodo per la criptazione da completare (connessione e query)
		@Override
		public void encrypt(Utente utente) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException,
				InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
			// tramite la cifratura che si vuole utilizzare si crea unua chiave
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(128);
			SecretKey secretKey;
			byte[] iv = null;
			if (utente.getId() == 0) {
				// genero una chiave segreta con generateKey
				secretKey = generator.generateKey();

			} else {
				try (Connection connection = Connessione.getConnection()) {
					// query che mi restituisce chiave e iv 
					String sql = "select chiave, iv from cript where id_utente = ? ";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setInt(1, utente.getId());
					ResultSet set = statement.executeQuery();
					set.next();
					// oggetto blob (Binary Large Object) che rappresenta la chiave fintanto la transazione è reale
					Blob keyBlob = set.getBlob("chiave");
					// restituisce un array di byte passandogli la posizione   iniziale e la lunghezza del blob
					byte[] keyBytes = keyBlob.getBytes(1, (int) keyBlob.length());
					// Usiamo SecretKeySpec per ricostruire la chiave dai suoi byte.
					secretKey = new SecretKeySpec(keyBytes, "AES");
					// recupero dell'iv
					iv = set.getBytes("iv");
				}

			}

			// restituisce un oggetto cipher per dirgli la tipologia di criptazione(aes cioè
			// a blocchi),
			// cbc concatenazione dei blocchi e il tipo di padding per aggiungere eventuali
			// "spazi"
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// inizializzo un oggetto cipher
			
			if(iv == null) {
				//inizializzo l'ogetto cipher passandogli la modalità e la chiave 
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
				//creazione dell'iv
				iv = cipher.getIV();
			} else {
				//inizializzo l'ogetto passandogli la modalità, chiave e l'iv recuperato tramite la query
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
			}
			
			// cripta la password trasformata in un array di byte
			byte[] crypted = cipher.doFinal(utente.getPassword().getBytes(StandardCharsets.UTF_8));
			// array di byte di un iv per la criptazione e decriptazione delle password

			String criptedPass = Base64.getEncoder().encodeToString(crypted);
			
			// setto la password passandogli quella criptata
			utente.setPassword(criptedPass);

			if (utente.getId() == 0) {

				// se l'utente è stato inserito correttamente vado avanti
				if (addUtente(utente)) {

					// prendo l'ultimo utente inserito
					utente = getUtenti().getLast();
					try (Connection connection = Connessione.getConnection()) {
						String sql = "insert into cript(id_utente, chiave, iv) values (?, ?, ?)";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setInt(1, utente.getId());

						// Ottieni solo i byte grezzi della chiave (16 byte per AES-128)
						// getEncode() restituisce nel suo formato primitivo(byte[]) e se non lo
						// supporta restituisce null
						byte[] chiaveCodificata = secretKey.getEncoded();
						// Salva l'array di byte nel BLOB
						statement.setBytes(2, chiaveCodificata);

						statement.setBytes(3, iv);
						statement.executeUpdate();
					}
				}
			} else {

				updateUtente(utente);
			}

		}

		// metodo per la decriptazione da completare
		@Override
		public String decrypt(int id) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException,
				InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
			byte[] arrayIv;
			SecretKey secretKey;
			String cryptedPass;
			try (Connection connection = Connessione.getConnection()) {
				String sql = "select c.chiave, c.iv, u.password from cript as c join utenti as u "
						+ "on c.id_utente = u.id where c.id_utente = ? ";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, id);
				ResultSet set = statement.executeQuery();

				if (!set.next()) {
					// Gestisci il caso in cui non ci siano dati per questo ID
					return null;
				}

				// 1. RECUPERO DELLA CHIAVE SEGRETA (SecretKey)
				// set.getBlob() restituisce un oggetto Blob, non la chiave.
				// Dobbiamo estrarre i byte dal Blob e ricostruire la chiave.
				Blob keyBlob = set.getBlob("c.chiave");

				byte[] keyBytes = keyBlob.getBytes(1, (int) keyBlob.length());
				// Usiamo SecretKeySpec per ricostruire la chiave dai suoi byte.
				secretKey = new SecretKeySpec(keyBytes, "AES");

				// 2. RECUPERO DELL'IV (Initialization Vector)
				// L'IV è stato salvato come byte[], quindi lo recuperiamo con getBytes().
				arrayIv = set.getBytes("c.iv");

				// 3. RECUPERO DELLA PASSWORD CRIPTATA
				// È stata salvata come stringa Base64, quindi la recuperiamo come tale.
				cryptedPass = set.getString("u.password");
			}

			// 4. DECRIPTAZIONE
			// Prima di tutto, dobbiamo decodificare la password da Base64 a un array di
			// byte.
			byte[] cryptedPassBytes = Base64.getDecoder().decode(cryptedPass);

			Cipher decipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			decipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(arrayIv));

			// Ora decriptiamo i byte corretti
			byte[] decryptedBytes = decipher.doFinal(cryptedPassBytes);

			// 5. CONVERSIONE A STRINGA
			// Convertiamo l'array di byte decriptato in una stringa usando il charset
			// corretto.
			return new String(decryptedBytes, StandardCharsets.UTF_8);

		}

	// set sul DB avviso!
	public void impostaAvvisoInviato(int idUtente) throws SQLException {
		try (Connection connection = Connessione.getConnection()) {
			String sql = "UPDATE utenti SET avviso_inviato = 1 WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, idUtente);
			ps.executeUpdate();
			ps.close();
		}
	}

	public void resetAvviso(int idUtente) throws SQLException {
		try (Connection connection = Connessione.getConnection()) {
			String sql = "UPDATE utenti SET avviso_inviato = 0 WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, idUtente);
			ps.executeUpdate();
			ps.close();
		}
	}

	@Override
	public Utente getUtenteByEmail(String email) throws SQLException {
		Utente utente;
		try(Connection connection = Connessione.getConnection()){
			String sql = "select * from utenti where email = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet set = statement.executeQuery();
			set.next();
			utente = new Utente(set.getInt("id"), 
								set.getString("nome"), 
								set.getString("cognome"),
								set.getString("username"), 
								set.getString("password"), 
								set.getInt("ruolo"), 
								set.getString("email"));
		}
		return utente;
	}

}
