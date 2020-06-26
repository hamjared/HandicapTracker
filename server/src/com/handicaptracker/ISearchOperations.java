package com.handicaptracker;

import java.sql.SQLException;

public interface ISearchOperations {
   public Course getCourse(String courseName, String courseCity, String courseState) throws SQLException;
}
