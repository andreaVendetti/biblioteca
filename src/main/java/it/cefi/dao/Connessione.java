package it.cefi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.cefi.security.DBConfig;

public class Connessione {

	// metodo che mi restituisce una connessione
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DBConfig.getUrl(),
            DBConfig.getUser(),
            DBConfig.getPassword()
        );
    }
}
