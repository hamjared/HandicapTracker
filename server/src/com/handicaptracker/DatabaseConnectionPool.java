package com.handicaptracker;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class DatabaseConnectionPool
{

   private LinkedList<Connection> myConnections;
   private int myNumConnections = 2;
   private String myDatabaseUrl;
   private String myDatabasePassword;
   private String myDatabaseUsername;
   private static DatabaseConnectionPool instance = new DatabaseConnectionPool();

   private DatabaseConnectionPool()
   {
      myConnections = new LinkedList<Connection>();
      this.setDatabaseproperties();
      this.initialize();
   }
   
   public static DatabaseConnectionPool getInstance() {
      return instance;
   }

   /**
    * 
    */
   public void initialize()
   {
       myConnections.clear();
      try
      {
         Class.forName("org.postgresql.Driver");
         for (int i = 0; i < myNumConnections; i++)
         {
            Connection con = DriverManager.getConnection(myDatabaseUrl,
                  myDatabaseUsername, myDatabasePassword);
            myConnections.push(con);
         }
      } catch (SQLException e)
      {
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

         InputStream inStream = this.getClass().getClassLoader()
               .getResourceAsStream(getPropertiesFileName());
         properties.load(inStream);
      } catch (IOException e)
      {
         System.out.println("Database configuration file not found");
      }

      myDatabaseUrl = properties.getProperty("url");
      myDatabasePassword = properties.getProperty("password");
      myDatabaseUsername = properties.getProperty("username");

   }

   private String getPropertiesFileName()
   {
      Properties properties = new Properties();
      InputStream inStream = this.getClass().getClassLoader()
            .getResourceAsStream("config/environment.properties");
      try
      {
         properties.load(inStream);
      } catch (IOException e)
      {
         // TODO Auto-generated catch block
         return "config/test_databaseConfig.properties";
      }
      return properties.getProperty("PRODUCTION") != null
            ? ("config/databaseConfig.properties")
            : "config/test_databaseConfig.properties";
   }

}
