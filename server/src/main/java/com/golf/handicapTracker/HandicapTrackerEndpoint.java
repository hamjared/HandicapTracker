package com.golf.handicapTracker;
import static spark.Spark.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Properties;

import spark.Spark;


public class HandicapTrackerEndpoint{
	static Logger log = Logger.getLogger(HandicapTrackerEndpoint.class);
	static final String keystoreFile = "config/clientkeystore";
	
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
//    	BasicConfigurator.configure();
//    	log.setLevel(Level.OFF);

//    	serverSetup();
        
		  before((request, response) -> {
	          response.header("Access-Control-Allow-Origin", "*");
	          response.header("Access-Control-Request-Method", "*");
	          response.header("Access-Control-Allow-Headers", "*");
	          response.type("application/json");
	
	      });
        get("/hello", (req, res) -> "Hello World");
        
        
        path("/admin", () -> {
        	before( (req, res) -> {
            	System.out.println("Before protected  call");
            });
        	post("/insertNewTees", (req,res) -> {
        		return AdminEventHandler.handInsertNewTees(req, res);
            });
        	
        	post("/insertCourse", (req, res) -> { 
        		return AdminEventHandler.handleInsertCourse(req, res); 
        		});
        	
        });
        
        path("/user", () -> {
        	before( (req, res) -> {
            	System.out.println("Before protected  call");
            });
        	
        	post("/getHandicapIndex", (req, res) -> {
        		return UserEventHandler.getHandicapIndex(req, res);
        	});
        	post("/getCourseHandicap", (req, res) -> {
        		return UserEventHandler.getCourseHandicap(req, res);
        	});
        });
        
        
        
        post("/createUser", (req, res) -> {
        	HandicapTrackerDatabase con = new HandicapTrackerDatabase();
        	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
        	
        	try {
	        	con.createUser((String) body.get("username"), 
	        			(String) body.get("password"), 
	        			(String) body.get("email"), 
	        			(String) body.get("firstName"), 
	        			(String) body.get("lastName"));
	        	res.status(201);
	        	return "User Added";
        	} catch (SQLException e) {
        		res.status(409);
        		e.printStackTrace();
        		return "User not added";
        	}
        });
        
        post("/login", (req,res) -> {
        	HandicapTrackerDatabase con = new HandicapTrackerDatabase();
        	JSONObject body = (JSONObject) new JSONParser().parse(req.body());
        	
        	try {
        		if(con.login((String) body.get("username"), (String) body.get("password"))) {
        			res.status(200);
        			return "Login Succesful";
        		}
        			
        	} catch(SQLException e) {
        		res.status(409);
        		e.printStackTrace();
        		return "Invalid input";
        	}
        	
        	res.status(403);
        	return "Login not succesful";
        });
    }
    
    public static void serverSetup()  {
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
    	Spark.secure(keystoreFile, props.getProperty("apiKeyPassword"), null, null);
    }
}
