package com.handicaptracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DatabaseConnectionProperties
{

   public static Map<String, String> getDatabaseConnectionProperties(){
      Properties properties = new Properties();
      HashMap<String, String> connectionProperties = new HashMap<>();
      try
      {
         properties.load(new FileInputStream(getPropertiesFileName()));
      } catch (IOException e)
      {
         System.out.println("Database configuration file not found");
      }

      connectionProperties.put("url", properties.getProperty("url"));
      connectionProperties.put("username", properties.getProperty("username"));
      connectionProperties.put("password", properties.getProperty("password"));
      return connectionProperties;
   }
   
   private static String getPropertiesFileName() {
      return System.getenv("PRODUCTION") != null ? ("src/resources/databaseConfig.properties") : "src/resources/test_databaseConfig.properties";
   }
   
}
