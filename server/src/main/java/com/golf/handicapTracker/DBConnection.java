package com.golf.handicapTracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	private Connection con;	
	private static final String url = "jdbc:postgresql://192.168.1.126:5432/handicapTracker";
	private static final String user = "jared";
	private static final String password = "";
	
	DBConnection(){
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean insertNewCourse(String courseName, String courseCity, String courseState, String teeColor, double rating, int slope ) {
		try {
			con.setAutoCommit(false);
			PreparedStatement stmt = con.prepareStatement("insert into course values (?,?,?)");
			stmt.setString(1, courseName);
			stmt.setString(2, courseCity);
			stmt.setString(3, courseState);
			stmt.executeUpdate();
			
			
			stmt = con.prepareStatement("insert into tees values (?,?,?,?,?,?)");
			stmt.setString(1, courseName);
			stmt.setString(2, courseCity);
			stmt.setString(3, courseState);
			stmt.setString(4, teeColor.toUpperCase());
			stmt.setDouble(5, rating);
			stmt.setInt(6, slope);
			
			stmt.executeUpdate();
			
			con.commit();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
		
		
	}
	
	
}
