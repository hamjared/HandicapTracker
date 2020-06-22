package com.golf.handicapTracker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface UtilityHandicapTrackerDatabaseAPI {

	
	/**
	 * @return Returns a List of Maps each of which have the following keys: courseName, courseCity, courseState
	 * @throws SQLException 
	 */
	public ArrayList<Course> getCourses() throws SQLException;
	
	/**
	 * @param courseName
	 * @param courseCity
	 * @param courseState
	 * @return Returns a list of tees associated with the course 
	 * @throws SQLException 
	 */
	public ArrayList<Tee> getTees(String courseName, String courseCity, String courseState) throws SQLException;
}
