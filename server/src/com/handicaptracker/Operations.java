package com.handicaptracker;

public class Operations implements ISearchOperations {
   private DatabaseConnectionPool connectionPool;
   
   protected DatabaseConnectionPool getConnectionPool() {
      return connectionPool;
   }
}
