package com.handicaptracker;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import junit.framework.TestCase;

public class UserOperationsTest extends TestCase
{

   private static IDatabaseTester databaseTester;
   private IUserOperations userOperations;

   protected void setUp() throws Exception
   {
      super.setUp();
      DriverManager.registerDriver(new org.postgresql.Driver());
      Map<String, String> conProps = DatabaseConnectionProperties
            .getDatabaseConnectionProperties();
      databaseTester = new JdbcDatabaseTester("org.postgresql.Driver",
            conProps.get("url"), conProps.get("username"),
            conProps.get("password"));

      userOperations = new UserOperations();

   }

   public void testCreateUser() throws Exception
   {
      beforeEach();
      IDatabaseConnection con = databaseTester.getConnection();
      userOperations.createUser("bob100", "password", "bob@email.com", "Bob",
            "Smith");
      ITable actualDataSet = con.createDataSet().getTable("users");
      ITable expectedDataSet = new FlatXmlDataSetBuilder()
            .build(new File(
                  "src/resources/test_databaseXMLFiles/afterInsertBob.xml"))
            .getTable("users");

      assertEquals(expectedDataSet.getRowCount(), actualDataSet.getRowCount());

      int rowCount = actualDataSet.getRowCount();
      ArrayList<Column> columns = new ArrayList<>(
            Arrays.asList(actualDataSet.getTableMetaData().getColumns()));

      for (int i = 0; i < rowCount; i++)
      {
         for (Column column : columns)
         {
            String columnName = column.getColumnName();
            if (columnName.equals("password")
                  || columnName.contentEquals("isadmin")
                  || columnName.contentEquals("canchangepriviliges"))
            {
               continue;
            }
            assertEquals(expectedDataSet.getValue(i, column.getColumnName()),
                  actualDataSet.getValue(i, column.getColumnName()));
         }
      }

   }

   public void testDeleteUser() throws Exception
   {
      beforeEach();
      IDatabaseConnection con = databaseTester.getConnection();
      userOperations.createUser("bob100", "password", "bob@email.com", "Bob",
            "Smith");
      userOperations.deleteUser("bob100", "password");
      IDataSet actualDataSet = con.createDataSet();
      FlatXmlDataSet expectedDataSet = new FlatXmlDataSetBuilder()
            .build(new File(
                  "src/resources/test_databaseXMLFiles/EmptyDB.xml"));

      assertEquals(expectedDataSet.getTable("users").getRowCount(), actualDataSet.getTable("users").getRowCount());
   }

   public void testChangePassword() throws Exception
   {
      beforeEach();
      IDatabaseConnection con = databaseTester.getConnection();
      userOperations.createUser("bob100", "password", "bob@email.com", "Bob",
            "Smith");
      ITable before = con.createDataSet().getTable("users");
      userOperations.changePassword("bob100", "password", "newPassword");
      ITable after = con.createDataSet().getTable("users");

      assertFalse(before.getValue(0, "password")
            .equals(after.getValue(0, "password")));
   }
   
   public void testChangePasswordInvalidOldPassword() throws Exception
   {
      beforeEach();

      userOperations.createUser("bob100", "password", "bob@email.com", "Bob",
            "Smith");
      boolean caughtInvalidCredentialException = false;
      try {
         userOperations.changePassword("bob100", "wrong", "newPassword");
      } catch(InvalidCredentialsException e) {
         caughtInvalidCredentialException = true;
      }
      
      assertTrue(caughtInvalidCredentialException);

   }

   public void testLogin() throws Exception
   {
      beforeEach();

      userOperations.createUser("bob100", "password", "bob@email.com", "Bob",
            "Smith");
      
      User user = userOperations.login("bob100", "password");
      
      assertEquals("Bob", user.getFirstName());
   }

   private void beforeEach() throws Exception
   {
      databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
      databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(
            new File("src/resources/test_databaseXMLFiles/EmptyDB.xml")));
      databaseTester.onSetup();
   }

}
