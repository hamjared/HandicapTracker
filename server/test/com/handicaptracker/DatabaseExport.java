package com.handicaptracker;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

public class DatabaseExport {


   public static void main(String[] args) throws Exception {
        // database connection
        
        Connection jdbcConnection = DriverManager.getConnection(
            "jdbc:postgresql://localhost/handicaptrackertest", "jared", "marvin123");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // full database export
        IDataSet fullDataSet = connection.createDataSet();
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("src/resources/test_databaseXMLFiles/afterInsertHighlandMeadows.xml"));
   }

}