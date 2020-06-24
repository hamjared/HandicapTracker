package com.handicaptracker;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class DatabaseConnectionPool
{

   private LinkedList<Connection> myConnections;
   private int myNumConnections;
   private String myDatabaseUrl;
   private String myDatabasePassword;
   private String myDatabaseUsername;

   DatabaseConnectionPool(int numConnections)
   {
      myConnections = new LinkedList<Connection>();
      myNumConnections = numConnections;
      this.setDatabaseproperties();
   }

   /**
    * 
    */
   public void initialize()
   {
      
      try {
         Class.forName("org.postgresql.Driver");
         for (int i = 0; i < myNumConnections; i++)
         {
            Connection con = DriverManager.getConnection(myDatabaseUrl, myDatabaseUsername, myDatabasePassword);
            myConnections.push(con);
         }
      } catch(SQLException e) {
         System.out.println("Could not connect to database");
         System.exit(1);
      } catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * @return
    * @throws NoSuchElementException
    */
   public Connection getConnection() throws NoSuchElementException
   {
      return myConnections.pop();
   }

   /**
    * @param connection
    */
   public void returnConnection(Connection connection)
   {
      myConnections.push(connection);
   }

   private void setDatabaseproperties()
   {
      Properties properties = new Properties();
      
      try
      {
         properties.load(new FileInputStream(
               getPropertiesFileName()));
      } catch (IOException e)
      {
         System.out.println("Database configuration file not found");
      }
      
      myDatabaseUrl = properties.getProperty("url");
      myDatabasePassword = properties.getProperty("password");
      myDatabaseUsername = properties.getProperty("username");

   }
   
   private String getPropertiesFileName() {
      return System.getenv("PRODUCTION") != null ? ("src/resources/databaseConfig.properties") : "src/resources/test_databaseConfig.properties";
   }
   

}

