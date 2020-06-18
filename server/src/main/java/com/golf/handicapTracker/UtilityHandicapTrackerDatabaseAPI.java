package com.golf.handicapTracker;

import java.util.ArrayList;
import java.util.Map;

public interface UtilityHandicapTrackerDatabaseAPI {

	
	/**
	 * @return Returns a List of Maps each of which have the following keys: courseName, courseCity, courseState
	 */
	public ArrayList<Map<String, String>> getCourses();
	
	/**
	 * @param courseName
	 * @param courseCity
	 * @param courseState
	 * @return Returns a list of tees associated with the course 
	 */
	public ArrayList<String> getTees(String courseName, String courseCity, String courseState);
}
