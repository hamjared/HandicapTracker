package com.golf.handicapTracker;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;



public class HandicapTrackerDatabase implements AdminHandicapTrackerDatabaseAPI, UserHandicapTrackerDatabaseAPI, UtilityHandicapTrackerDatabaseAPI {
	
	private Connection con;	
	private static final String url = "jdbc:postgresql://10.0.0.185:5432/handicapTracker";
	private static final String user = "jared";
	
	public HandicapTrackerDatabase(){
		 
		try {
			con = DriverManager.getConnection(url, user, getDBPassword());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	HandicapTrackerDatabase(Connection con){
		this.con = con;
	}
	
	private String getDBPassword() {
		Properties props = new Properties();
    	try {
			props.load(new FileInputStream("config/config.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props.getProperty("dbpassword");
	}


	
	public void insertNewCourseWithTees(String courseName, String courseCity, String courseState, String teeColor, double rating, int slope, int par ) throws SQLException {
		this.insertNewCourse(courseName, courseCity, courseState);
		this.insertNewTees(courseName, courseCity, courseState, teeColor, rating, slope, par);
		
	}
	
	public void insertNewTees(String courseName, String courseCity, String courseState, String teeColor, double rating, int slope, int par) throws SQLException {
		PreparedStatement stmt;
		stmt = con.prepareStatement("insert into tees values (?,?,?,?,?,?, ?)");
		stmt.setString(1, courseName);
		stmt.setString(2, courseCity);
		stmt.setString(3, courseState);
		stmt.setString(4, teeColor.toUpperCase());
		stmt.setDouble(5, rating);
		stmt.setInt(6, slope);
		stmt.setInt(7, par);
		
		stmt.executeUpdate();

		
	}
	
	public void insertNewCourse(String courseName, String courseCity, String courseState) throws SQLException {
		
		PreparedStatement stmt = con.prepareStatement("insert into course values (?,?,?)");
		stmt.setString(1, courseName);
		stmt.setString(2, courseCity);
		stmt.setString(3, courseState);
		stmt.executeUpdate(); 
			
	}
	
	public void createUser(String userName, String password, 
			String email, String firstName, String lastName) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("insert into users values (?,crypt(?,gen_salt('bf')), ?, ?, ?)");
		stmt.setString(1, userName);
		stmt.setString(2, password);
		stmt.setString(3, email);
		stmt.setString(4, firstName);
		stmt.setString(5, lastName);
		stmt.executeUpdate();
	}
	
	public boolean login(String userName, String password) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT userName FROM Users where userName = ? and password = crypt(?, password)");
		stmt.setString(1, userName);
		stmt.setString(2, password);
		System.out.println(stmt);
		stmt.executeQuery();
		
		ResultSet rs = stmt.getResultSet();
		int rowCount = 0;
		while(rs.next()) {
			rowCount++;
		}
		if(rowCount == 1) {
			return true;
		}
		 return false;
	}
	
	public void insertRound(String userName, String courseName, String courseCity, 
			String courseState, String teeColor, Timestamp timeStamp, int score) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("INSERT INTO Rounds values (?, ?, ?, ?, ?, ?, ?)");
		stmt.setTimestamp(1, timeStamp);
		stmt.setString(2, userName);
		stmt.setString(3, courseName);
		stmt.setString(4, courseCity);
		stmt.setString(5, courseState);
		stmt.setString(6, teeColor);
		stmt.setInt(7, score);
		
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void deleteRound(String userName, Timestamp timeStamp) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("DELETE FROM Rounds WHERE datePlayed = ? AND golferusername = ?");
		stmt.setTimestamp(1, timeStamp);
		stmt.setString(2, userName);
		
		stmt.executeUpdate();
		stmt.close();
	}
	
	public double getUserHandicapIndex(String user) throws SQLException  {
		PreparedStatement stmt = con.prepareStatement("SELECT getHandicapIndex(?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, user);
		System.out.println(stmt.toString());
		ResultSet rs = stmt.executeQuery();
		
		rs.first();
		return rs.getDouble(1);
		
	}
	
	public int getCoursePar(String courseName, String courseCity, String courseState, String teeColor) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT par FROM course NATURAL JOIN tees "
				+ "WHERE courseName = ? and courseCity = ?"
				+ "and courseState = ? and teeColor = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, courseName);
		stmt.setString(2, courseCity);
		stmt.setString(3, courseState);
		stmt.setString(4, teeColor);
		System.out.println(stmt.toString());
		ResultSet rs = stmt.executeQuery();
		
		rs.first();
		return rs.getInt(1);
	}
	
	public int getCourseSlope(String courseName, String courseCity, String courseState, String teeColor) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT slope FROM course NATURAL JOIN tees "
				+ "WHERE courseName = ? and courseCity = ?"
				+ "and courseState = ? and teeColor = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, courseName);
		stmt.setString(2, courseCity);
		stmt.setString(3, courseState);
		stmt.setString(4, teeColor);
		System.out.println(stmt.toString());
		ResultSet rs = stmt.executeQuery();
		
		rs.first();
		return rs.getInt(1);
	}
	
	public double getCourseRating(String courseName, String courseCity, String courseState, String teeColor) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT rating FROM course NATURAL JOIN tees "
				+ "WHERE courseName = ? and courseCity = ?"
				+ "and courseState = ? and teeColor = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		stmt.setString(1, courseName);
		stmt.setString(2, courseCity);
		stmt.setString(3, courseState);
		stmt.setString(4, teeColor);
		System.out.println(stmt.toString());
		ResultSet rs = stmt.executeQuery();
		
		rs.first();
		return rs.getDouble(1);
	}
	
	public int getCourseHandicap(String user, String courseName, String courseCity, String courseState, String teeColor) throws SQLException {
		double handicapIndex = this.getUserHandicapIndex(user);
		int slope = this.getCourseSlope(courseName, courseCity, courseState, teeColor);
		double rating = this.getCourseRating(courseName, courseCity, courseState, teeColor);
		int par = this.getCoursePar(courseName, courseCity, courseState, teeColor);
		
		return (int) Math.round(handicapIndex * slope/113.0 + (rating - par));
	}

	@Override
	public ArrayList<Map<String, String>> getCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getTees(String courseName, String courseCity, String courseState) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
