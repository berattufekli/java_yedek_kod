package com.database;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import javax.management.RuntimeErrorException;

import com.mysql.cj.protocol.Message;

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
	
	public static String md5sifrele(String icerik) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] sifrelenmis=md.digest(icerik.getBytes());
			
			BigInteger no = new BigInteger(1, sifrelenmis);
			
			String hashİcerik = no.toString();
			while (hashİcerik.length()<32) {
				hashİcerik = "0"+hashİcerik;
			}
			return hashİcerik;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
