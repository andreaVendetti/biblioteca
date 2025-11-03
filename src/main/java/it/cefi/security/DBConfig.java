package it.cefi.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {
	//Si istanzia una properties e un path costante
	private static final Properties props = new Properties();
	private static final String CONFIG_PATH;

	static {
		CONFIG_PATH = "C:\\Users\\andre\\Desktop\\sviluppo\\workspaceJava\\connessione\\db.properties";

		try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
			//il metodo load recupera una properties da una file 
			props.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Errore nel caricamento del file di configurazione: " + CONFIG_PATH, e);

		}
	}
	
	// recupero l'url. user e pass
	public static String getUrl() {
		return props.getProperty("db.url");
	}

	public static String getUser() {
		return props.getProperty("db.user");
	}
	
	public static String getPassword() {
		return props.getProperty("db.password");
	}
}
