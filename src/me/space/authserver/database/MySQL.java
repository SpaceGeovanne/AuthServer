package me.space.authserver.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import me.space.authserver.main.Main;

public class MySQL {

	public static Connection con = null;
	
	public static void startConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/spacenetwork", "space", "space");
			createTable();
			System.out.println("[AuthServer - Database] Conexão com o banco de dados estabelecida!");
		} catch (Exception e) {
			System.out.println("[AuthServer - Database] Falha ao conectar ao banco de dados...");
			Main.getPlugin().getPluginLoader().disablePlugin(Main.getPlugin());
		}
	}
	
	public static boolean isAlive() {
		try {
			return !(con == null || con.isClosed() || !con.isValid(5));
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static void createTable() {
		PreparedStatement stm = null;
		
		try {
			stm = (PreparedStatement) con.prepareStatement("CREATE TABLE IF NOT EXISTS `authserver` (`player` VARCHAR(24), `password` VARCHAR(60))");
			stm.execute();
			stm.close();
			System.out.println("[Evento - SQL] Tabela criada/carregada corretamente!");
		} catch (SQLException e) {
			System.out.println("[Evento - SQL] Erro ao criar/carregar a tabela!");
			e.printStackTrace();
			Main.getPlugin().getPluginLoader().disablePlugin(Main.getPlugin());
		} 
	}
	
	public static void stop() {
		if(isAlive()) {
			try {
				con.close();
				System.out.println("[AuthServer - Database] Conexão com o SQL finalizada corretamente!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
