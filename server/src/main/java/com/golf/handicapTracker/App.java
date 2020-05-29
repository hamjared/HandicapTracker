package com.golf.handicapTracker;
import static spark.Spark.*;

import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class App 
{
	static Logger log = Logger.getLogger(App.class);
    public static void main( String[] args )
    {
//    	BasicConfigurator.configure();
//    	log.setLevel(Level.OFF);
        System.out.println( "Hello World!" );
        get("/hello", (req, res) -> "Hello World");
        post("/insertCourse", (req, res) -> {
        	HandicapTrackerDatabase con = new HandicapTrackerDatabase();
        	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
        	try {
	        	con.insertNewCourseWithTees((String)body.get("courseName"), 
	        			(String)body.get("courseCity"), 
	        			(String)body.get("courseState"), 
	        			(String)body.get("teeColor"), 
	        			(Double)body.get("rating"), 
	        			Integer.parseInt(body.get("slope").toString()));
	        	res.status(201);
	        	return "Course added";
        	} catch (SQLException e) {
        		res.status(409);
        		return "Course not added";
        	}
        	
        });
        
        post("/insertNewTees", (req,res) -> {
        	HandicapTrackerDatabase con = new HandicapTrackerDatabase();
        	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
        	
        	try {
	        	con.insertNewTees((String)body.get("courseName"), 
	        			(String)body.get("courseCity"), 
	        			(String)body.get("courseState"), 
	        			(String)body.get("teeColor"), 
	        			(Double)body.get("rating"), 
	        			Integer.parseInt(body.get("slope").toString()));
	        	res.status(201);
	        	return "Tees Added";
        	} catch (SQLException e) {
        		res.status(409);
        		return "Tees not added";
        	}
        });
    }
}
