package model.data.mysql.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.ModelException;

public class MySQLConnectionFactory {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1/streaming";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	
	
	public static Connection getConnection() throws ModelException {
		try {
			
			return DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao conectar com o banco " + sqle);
		}
		
	}
}
