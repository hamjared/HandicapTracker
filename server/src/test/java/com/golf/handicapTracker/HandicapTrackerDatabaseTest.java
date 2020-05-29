package com.golf.handicapTracker;
import java.io.File;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;



public class HandicapTrackerDatabaseTest {
	private static IDatabaseTester databaseTester;
	@BeforeAll
	public static void setUp() throws Exception{
		databaseTester = new JdbcDatabaseTester("org.postgresql.Driver", 
				"jdbc:postgresql://localhost:5432/handicaptrackertest", 
				"jared", 
				"marvin123");
		
		
	}
	
	
	@BeforeEach
	public void  beforeEach() throws Exception {
		databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
		databaseTester.setDataSet(new FlatXmlDataSetBuilder().build(new File("EmptyDB.xml")));
		databaseTester.onSetup();
		
	}

	
	@Test
	public void testInsertNewCourseWithTees() throws Exception {
		
		IDatabaseConnection con = databaseTester.getConnection();
		HandicapTrackerDatabase db = new HandicapTrackerDatabase(con.getConnection());
		db.insertNewCourseWithTees("Highland Meadows", "Windsor", "CO", "BLUE", 70.2, 128);
		IDataSet actualDataSet = con.createDataSet();
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("AfterInsertHM.xml"));
		Assertion.assertEquals(expectedDataSet, actualDataSet);
	
	}
	
	@Test
	public void testInsertDuplicateCourse() throws Exception {
		IDatabaseConnection con = databaseTester.getConnection();
		HandicapTrackerDatabase db = new HandicapTrackerDatabase(con.getConnection());
		db.insertNewCourseWithTees("Highland Meadows", "Windsor", "CO", "BLUE", 70.2, 128);
		Assertions.assertThrows(SQLException.class, () -> {
			db.insertNewCourseWithTees("Highland Meadows", "Windsor", "CO", "BLUE", 70.2, 128);
		});
		Assertions.assertThrows(SQLException.class, () -> {
			db.insertNewCourseWithTees("Highland Meadows", "Windsor", "CO", "WHITE", 70.2, 128);
		});
	}


	

}
