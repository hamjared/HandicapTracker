package com.golf.handicapTracker;
import static spark.Spark.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Hello world!
 *
 */
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
        	System.out.println(req.body());
        	DBConnection con = new DBConnection();
        	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
        	System.out.println(body.get("slope").toString());
        	boolean success = con.insertNewCourse((String)body.get("courseName"), 
        			(String)body.get("courseCity"), 
        			(String)body.get("courseState"), 
        			(String)body.get("teeColor"), 
        			(Double)body.get("rating"), 
        			Integer.parseInt(body.get("slope").toString()));
        	
        	if(success) {
        		return "Course Added";
        	}
        	else {
        		return "Course not added";
        	}
        });
    }
}
