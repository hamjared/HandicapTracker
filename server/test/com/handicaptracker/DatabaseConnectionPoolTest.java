package com.handicaptracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

public class DatabaseConnectionPoolTest extends TestCase
{
  
   DatabaseConnectionPool pool;
   private final static int numConnections = 2;
   
   protected void setUp() {
      pool = new DatabaseConnectionPool(numConnections);
   }
   
   public void testInitialize()
   {
      pool.initialize();
      for(int i = 0 ; i < numConnections; i++) {
         pool.getConnection();
      }
      
   }

   public void testGetConnection() throws SQLException
   {
      pool.initialize();
      for(int i = 0 ; i < numConnections; i++) {
         assertTrue(pool.getConnection().isValid(2));
      }
      
      try {
         pool.getConnection();
         assertTrue(false);
      } catch(NoSuchElementException e) {
         return;
      }
      
   }

   public void testReturnConnection() throws SQLException
   {
      pool.initialize();
      Connection con = null;
      for(int i = 0 ; i < numConnections; i++) {
         con = pool.getConnection();
         assertTrue(con.isValid(2));
      }
      pool.returnConnection(con);
      try {
         pool.getConnection();
         
      } catch(NoSuchElementException e) {
         assertTrue(false);
      }
   }

   
}
