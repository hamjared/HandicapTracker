package com.handicaptracker;

public class Operations implements ISearchOperations {
   protected DatabaseConnectionPool myConnectionPool;
   
   Operations() {
      myConnectionPool = new DatabaseConnectionPool(2);
      myConnectionPool.initialize();
   }
   
}
