package com.golf.handicapTracker;

import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import spark.Request;
import spark.Response;

public class AdminEventHandler {

	public static String handleInsertCourse(Request req, Response res) throws ParseException {
		HandicapTrackerDatabase con = new HandicapTrackerDatabase();
        
    	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
    	try {
        	con.insertNewCourseWithTees((String)body.get("courseName"), 
        			(String)body.get("courseCity"), 
        			(String)body.get("courseState"), 
        			(String)body.get("teeColor"), 
        			Double.parseDouble(body.get("rating").toString()), 
        			Integer.parseInt(body.get("slope").toString()),
        			Integer.parseInt(body.get("par").toString()));
        	res.status(201);
        	return "Course added";
    	} catch (SQLException e) {
    		res.status(409);
    		return "Course not added";
    	}
	}


	public static String handInsertNewTees(Request req, Response res) throws ParseException {
		HandicapTrackerDatabase con = new HandicapTrackerDatabase();
    	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
    	
    	try {
        	con.insertNewTees((String)body.get("courseName"), 
        			(String)body.get("courseCity"), 
        			(String)body.get("courseState"), 
        			(String)body.get("teeColor"), 
        			Double.parseDouble(body.get("rating").toString()),
        			Integer.parseInt(body.get("slope").toString()),
        			Integer.parseInt(body.get("par").toString()));
        	res.status(201);
        	return "Tees Added";
    	} catch (SQLException e) {
    		res.status(409);
    		return "Tees not added";
    	}
	}
	
}
