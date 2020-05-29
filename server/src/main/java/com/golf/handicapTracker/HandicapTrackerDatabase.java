package com.golf.handicapTracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class HandicapTrackerDatabase {
	
	private Connection con;	
	private static final String url = "jdbc:postgresql://192.168.1.126:5432/handicapTracker";
	private static final String user = "jared";
	private static final String password = "";
	
	HandicapTrackerDatabase(){
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	HandicapTrackerDatabase(Connection con){
		this.con = con;
	}
	
	
	
	public void insertNewCourseWithTees(String courseName, String courseCity, String courseState, String teeColor, double rating, int slope ) throws SQLException {
		this.insertNewCourse(courseName, courseCity, courseState);
		this.insertNewTees(courseName, courseCity, courseState, teeColor, rating, slope);
		
	}
	
	public void insertNewTees(String courseName, String courseCity, String courseState, String teeColor, double rating, int slope) throws SQLException {
		PreparedStatement stmt;
		stmt = con.prepareStatement("insert into tees values (?,?,?,?,?,?)");
		stmt.setString(1, courseName);
		stmt.setString(2, courseCity);
		stmt.setString(3, courseState);
		stmt.setString(4, teeColor.toUpperCase());
		stmt.setDouble(5, rating);
		stmt.setInt(6, slope);
		
		stmt.executeUpdate();

		
	}
	
	public void insertNewCourse(String courseName, String courseCity, String courseState) throws SQLException {
		
		PreparedStatement stmt = con.prepareStatement("insert into course values (?,?,?)");
		stmt.setString(1, courseName);
		stmt.setString(2, courseCity);
		stmt.setString(3, courseState);
		stmt.executeUpdate(); 
			
		
	}
	
	
}
