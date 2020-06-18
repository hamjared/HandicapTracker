package com.golf.handicapTracker;

import java.sql.SQLException;

public interface AdminHandicapTrackerDatabaseAPI {
	public void insertNewCourseWithTees(String courseName, String courseCity, 
			String courseState, String teeColor, double rating, int slope, int par) throws SQLException;
	
	public void insertNewTees(String courseName, String courseCity, 
			String courseState, String teeColor, double rating, int slope, int par) throws SQLException;
	
	public void insertNewCourse(String courseName, String courseCity, String courseState) throws SQLException;
	
	
}
