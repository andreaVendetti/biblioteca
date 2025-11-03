package it.cefi.repositories;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import it.cefi.models.Utente;

public interface UtenteRepository {

	boolean addUtente(Utente utente) throws SQLException;

	void updateUtente(Utente utente) throws SQLException;

	void deleteUtente(int id) throws SQLException;

	List<Utente> getUtenti() throws SQLException;

	Utente getUtente(int id) throws SQLException;

	Utente getUtenteByEmail(String email) throws SQLException;
	
	void encrypt(Utente utente)throws SQLException, 
									  NoSuchAlgorithmException, 
									  NoSuchPaddingException, 
									  InvalidKeyException, 
									  IllegalBlockSizeException, 
									  BadPaddingException, 
									  InvalidAlgorithmParameterException;

	String decrypt(int id)throws SQLException, 
								 NoSuchAlgorithmException, 
								 NoSuchPaddingException, 
								 InvalidKeyException, 
								 IllegalBlockSizeException, 
								 BadPaddingException,
								 InvalidAlgorithmParameterException;
}
