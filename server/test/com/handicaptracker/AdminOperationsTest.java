package com.handicaptracker;

import java.io.File;
import java.sql.DriverManager;
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
   }

   public void testAddCourse() throws Exception
   {
      beforeEach();
      IDatabaseConnection con = databaseTester.getConnection();
      Course course = new CourseBuilder().name("Highland Meadows")
            .city("Windsor").state("CO").numHoles(18).build();
      adminOperations.addCourse(course);
      
      ITable actualDataSet = con.createDataSet().getTable("course");
      ITable expectedDataSet = new FlatXmlDataSetBuilder()
            .build(new File(
                  "src/resources/test_databaseXMLFiles/afterInsertHighlandMeadows.xml"))
            .getTable("course");

      assertEquals(expectedDataSet.getRowCount(), actualDataSet.getRowCount());

      int rowCount = actualDataSet.getRowCount();
      ArrayList<Column> columns = new ArrayList<>(
            Arrays.asList(actualDataSet.getTableMetaData().getColumns()));

      for (int i = 0; i < rowCount; i++)
      {
         for (Column column : columns)
         {
            String columnName = column.getColumnName();
            if (columnName.equals("courseid") || columnName.contentEquals("numholes"))
            {
               continue;
            }
            assertEquals(expectedDataSet.getValue(i, column.getColumnName()),
                  actualDataSet.getValue(i, column.getColumnName()));
         }
      }
   }

   public void testAddTees()
   {
      fail("Not yet implemented");
   }

   public void testRemoveCourse()
   {
      fail("Not yet implemented");
   }

   public void testRemoveTee()
   {
      fail("Not yet implemented");
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
      databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(
            new File("src/resources/test_databaseXMLFiles/EmptyDB.xml")));
      databaseTester.onSetup();
   }

}
