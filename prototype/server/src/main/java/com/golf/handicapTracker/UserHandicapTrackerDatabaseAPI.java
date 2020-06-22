package com.golf.handicapTracker;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface UserHandicapTrackerDatabaseAPI {
	public void createUser(String userName, String password, 
			String email, String firstName, String lastName) throws SQLException;
	
	
	public boolean login(String userName, String password) throws SQLException;
	
	
	public void insertRound(String userName, String courseName, 
			String courseCity, String courseState, String teeColor, 
			Timestamp timeStamp, int score) throws SQLException;
	
	
	public void deleteRound(String userName, Timestamp timeStamp) throws SQLException;
	
	
	public double getUserHandicapIndex(String user) throws SQLException;
	
	
	public int getCoursePar(String courseName, String courseCity, 
			String courseState, String teeColor) throws SQLException;
	
	
	public int getCourseSlope(String courseName, String courseCity, 
			String courseState, String teeColor) throws SQLException;
	
	
	public double getCourseRating(String courseName, String courseCity, 
			String courseState, String teeColor) throws SQLException;
	
	
	public int getCourseHandicap(String user, String courseName, 
			String courseCity, String courseState, String teeColor) throws SQLException;
}
