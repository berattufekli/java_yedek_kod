package com.database;
import java.sql.*;

public class databaseUtil {
	static Connection con = null;
	
	public static Connection baglan() {
		try {
			//"jdbc::mysql://serverIPAdresi://db_ismi", "kullanici", "sifre"
			con = DriverManager.getConnection("jdbc:mysql://localhost/projedb","root", "mysql");
			return con;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage().toString());
			return null;
		}
	}
}
