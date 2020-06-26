package com.handicaptracker;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import junit.framework.TestCase;

public class AdminOperationsTest extends TestCase
{

   private static IDatabaseTester databaseTester;
   IAdminOperations adminOperations;
   Course hm ;
   User bob;
   protected void setUp() throws Exception
   {
      super.setUp();
      DriverManager.registerDriver(new org.postgresql.Driver());
      Map<String, String> conProps = DatabaseConnectionProperties
               .getDatabaseConnectionProperties();
      databaseTester = new JdbcDatabaseTester("org.postgresql.Driver",
               conProps.get("url"), conProps.get("username"),
               conProps.get("password"));
      adminOperations = new AdminOperations();
      hm = new CourseBuilder().name("Highland Meadows")
               .city("Windsor").state("CO").numHoles(18).build();
      bob = new UserBuilder().username("bob").email("bob@email.com")
               .firstName("bob").lastName("smith").build();
      
   }

   public void testAddCourse() throws Exception
   {
      beforeEach();
      IDatabaseConnection con = databaseTester.getConnection();
      User bob = new UserBuilder().username("bob").email("bob@email.com")
               .firstName("bob").lastName("smith").build();
      
      adminOperations.addCourse(hm, bob, "password");

      ITable actualDataSet = con.createDataSet().getTable("course");
      ITable expectedDataSet = new FlatXmlDataSetBuilder().build(new File(
               "src/resources/test_databaseXMLFiles/afterInsertHighlandMeadows.xml"))
               .getTable("course");
      con.close();

      assertEquals(expectedDataSet.getRowCount(), actualDataSet.getRowCount());

      int rowCount = actualDataSet.getRowCount();
      ArrayList<Column> columns = new ArrayList<>(
               Arrays.asList(actualDataSet.getTableMetaData().getColumns()));

      for (int i = 0; i < rowCount; i++)
      {
         for (Column column : columns)
         {
            String columnName = column.getColumnName();
            if (columnName.equals("courseid")
                     || columnName.contentEquals("numholes"))
            {
               continue;
            }
            assertEquals(expectedDataSet.getValue(i, column.getColumnName()),
                     actualDataSet.getValue(i, column.getColumnName()));
         }
      }
   }

   public void testAddCourseSWithInvalidCredentials() throws SQLException
   {
      User alice = new UserBuilder().username("alice").email("alice@email.com")
               .firstName("alice").lastName("jones").build();

      UserOperations userOperations = new UserOperations();
      userOperations.createUser(alice, "password");
      Course course = new CourseBuilder().name("Highland Meadows")
               .city("Windsor").state("CO").numHoles(18).build();

      boolean caughtException = false;
      try
      {
         adminOperations.addCourse(course, alice, "password");
      } catch (InvalidCredentialsException e)
      {
         caughtException = true;
      }

      assertTrue("Should exert due to alice having insfficient priviliges",
               caughtException);
   }

   public void testAddTees() throws Exception
   {
      beforeEach();
      adminOperations.addCourse(hm, bob, "password");
      Tee tee = new TeeBuilder().course(hm).teeColor("BLUE").rating(70.2).slope(128).par(71).yardage(6500).build();
      adminOperations.addTees(tee, bob, "password");
      
      IDatabaseConnection con = databaseTester.getConnection();
      ITable dataSet = con.createDataSet().getTable("tees");
      
      assertEquals(1, dataSet.getRowCount());

      assertEquals("BLUE", dataSet.getValue(0, "teeColor"));
      con.close();
      
   }

   public void testRemoveCourse() throws Exception
   {
      beforeEach();
      IDatabaseConnection con = databaseTester.getConnection();
      adminOperations.addCourse(hm, bob, "password");
      ITable dataSet = con.createDataSet().getTable("course");
      assertEquals(1, dataSet.getRowCount());
      
      adminOperations.removeCourse(hm, bob, "password");
      dataSet = con.createDataSet().getTable("course");
      con.close();
      assertEquals(0, dataSet.getRowCount());
      
   }

   public void testRemoveTee() throws Exception
   {
      beforeEach();
      IDatabaseConnection con = databaseTester.getConnection();
      adminOperations.addCourse(hm, bob, "password");
      Tee tee = new TeeBuilder().course(hm).teeColor("BLUE").rating(70.2).slope(128).par(71).yardage(6500).build();
      adminOperations.addTees(tee, bob, "password");
      ITable dataSet = con.createDataSet().getTable("tees");
      assertEquals(1, dataSet.getRowCount());
      
      adminOperations.removeTee(tee, bob, "password");
      dataSet = con.createDataSet().getTable("tees");
      con.close();
      assertEquals(0, dataSet.getRowCount());
      
   }

   public void testGrantAdminPriviliges()
   {
      fail("Not yet implemented");
   }

   public void testRevokeAdminPriviliges()
   {
      fail("Not yet implemented");
   }

   private void beforeEach() throws Exception
   {
      databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
      databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(new File(
               "src/resources/test_databaseXMLFiles/EmptyDB.xml")));
      databaseTester.onSetup();
      
      Map<String, String> conProps = DatabaseConnectionProperties
               .getDatabaseConnectionProperties();
      Connection con = DriverManager.getConnection(conProps.get("url"),
               conProps.get("username"), conProps.get("password"));
      Statement stmt = con.createStatement();
      stmt.executeUpdate(
               "INSERT INTO users values ('bob',crypt('password',gen_salt('bf')),'bob@email.com', 'bob', 'smith', true, true)");
      con.close();

   }

}
