package com.handicaptracker;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import junit.framework.TestCase;

public class GolferOperationsTest extends TestCase
{

   private static IDatabaseTester databaseTester;
   IAdminOperations adminOperations;
   IGolferOperations golferOperations;
   Course hm;
   Tee hmTee;
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
      golferOperations = new GolferOperations();
      hm = new CourseBuilder().name("Highland Meadows").city("Windsor")
               .state("CO").numHoles(18).build();
      bob = new UserBuilder().username("bob").email("bob@email.com")
               .firstName("bob").lastName("smith").build();
      hmTee = new TeeBuilder().course(hm).teeColor("BLUE").rating(70.2)
               .slope(128).par(71).yardage(6562).build();

   }

   public void testAddRound() throws Exception
   {
      beforeEach();
      Round round = new RoundBuilder().courseId(hm.getCourseId())
               .datePlayed(new Date(System.currentTimeMillis()))
               .golferUsername(bob.getUsername()).teeColor("BLUE").score(85)
               .build();
      golferOperations.addRound(round);
      IDatabaseConnection con = databaseTester.getConnection();
      ITable actualDataSet = con.createDataSet().getTable("rounds");
      assertEquals(1, actualDataSet.getRowCount());
      assertEquals(85, actualDataSet.getValue(0, "score"));
      BigDecimal differential = (BigDecimal) actualDataSet.getValue(0,
               "differential");
      assertEquals(13, differential.intValue());

      golferOperations.deleteRound(round);
      actualDataSet = con.createDataSet().getTable("rounds");
      assertEquals(0, actualDataSet.getRowCount());
   }

   public void testGetHandicapIndex20Rounds() throws Exception
   {
      beforeEach();
      for (int i = 0; i < 20; i++)
      {
         Round round = new RoundBuilder().courseId(hm.getCourseId())
                  .teeColor("BLUE")
                  .datePlayed(new Date(
                           System.currentTimeMillis() + i * 90_000_000l))
                  .score(70 + i).golferUsername(bob.getUsername()).build();
         golferOperations.addRound(round);

      }

      BigDecimal handicapIndex = new BigDecimal(
               golferOperations.getHandicapIndex(bob));
      BigDecimal expectedHandicapIndex = new BigDecimal(3.6);
      assertEquals(expectedHandicapIndex.setScale(1, BigDecimal.ROUND_HALF_UP),
               handicapIndex.setScale(1, BigDecimal.ROUND_HALF_UP));

   }
   

   
   public void testGetHandicapIndex19Rounds() throws Exception
   {
      beforeEach();
      for (int i = 0; i < 19; i++)
      {
         Round round = new RoundBuilder().courseId(hm.getCourseId())
                  .teeColor("BLUE")
                  .datePlayed(new Date(
                           System.currentTimeMillis() + i * 90_000_000l))
                  .score(70 + i).golferUsername(bob.getUsername()).build();
         golferOperations.addRound(round);

      }

      BigDecimal handicapIndex = new BigDecimal(
               golferOperations.getHandicapIndex(bob));
      BigDecimal expectedHandicapIndex = new BigDecimal(1.5);
      assertEquals(expectedHandicapIndex.setScale(1, BigDecimal.ROUND_HALF_UP),
               handicapIndex.setScale(1, BigDecimal.ROUND_HALF_UP));

   }

   public void testGetCourseHandicapIndex() throws Exception
   {
      beforeEach();
      for (int i = 0; i < 5; i++)
      {
         Round round = new RoundBuilder().courseId(hm.getCourseId())
                  .teeColor("BLUE")
                  .datePlayed(new Date(
                           System.currentTimeMillis() + i * 90_000_000l))
                  .score(70 + i).golferUsername(bob.getUsername()).build();
         golferOperations.addRound(round);

      }

      int actualCourseHandicap = golferOperations.getCourseHandicap(hmTee, bob);
      int expectedCourseHandicap = -1;
      assertEquals(expectedCourseHandicap, actualCourseHandicap);
   }

   public void testGetRoundsUser() throws Exception
   {
      beforeEach();
      for (int i = 0; i < 20; i++)
      {
         Round round = new RoundBuilder().courseId(hm.getCourseId())
                  .teeColor("BLUE")
                  .datePlayed(new Date(
                           System.currentTimeMillis() + i * 90_000_000l))
                  .score(70 + i).golferUsername(bob.getUsername()).build();
         golferOperations.addRound(round);

      }
      
      List<Round> rounds = golferOperations.getRounds(bob);
      assertEquals(20, rounds.size());
   }


   private void beforeEach() throws Exception
   {
      databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
      databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(
               new File("src/resources/test_databaseXMLFiles/EmptyDB.xml")));
      databaseTester.onSetup();

      Map<String, String> conProps = DatabaseConnectionProperties
               .getDatabaseConnectionProperties();
      Connection con = DriverManager.getConnection(conProps.get("url"),
               conProps.get("username"), conProps.get("password"));
      Statement stmt = con.createStatement();
      stmt.executeUpdate(
               "INSERT INTO users values ('bob',crypt('password',gen_salt('bf')),'bob@email.com', 'bob', 'smith', true, true)");
      adminOperations.addCourse(hm, bob, "password");
      adminOperations.addTees(hmTee, bob, "password");
      con.close();

   }

}
