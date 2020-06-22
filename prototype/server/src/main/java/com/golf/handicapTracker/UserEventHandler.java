package com.golf.handicapTracker;

import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import spark.Request;
import spark.Response;

public class UserEventHandler {
	
	public static String getHandicapIndex(Request req, Response res) throws ParseException {
		HandicapTrackerDatabase con = new HandicapTrackerDatabase();
        
    	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
    	try {
        	double handicapIndex = con.getUserHandicapIndex((String)body.get("username"));
        	res.status(201);
        	return "" + handicapIndex;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		res.status(409);
    		return "Handicap index not found";
    	}
	}

	public static String getCourseHandicap(Request req, Response res) throws ParseException {
		HandicapTrackerDatabase con = new HandicapTrackerDatabase();
        
    	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
    	try {
        	double courseHandicap = con.getCourseHandicap((String) body.get("username"), 
        												 (String) body.get("courseName"), 
        												 (String) body.get("courseCity"), 
        												 (String) body.get("courseState"), 
        												 (String) body.get("teeColor"));
        	res.status(201);
        	return "" + courseHandicap;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		res.status(409);
    		return "Course Handicap not found";
    	}
		
	}

}

