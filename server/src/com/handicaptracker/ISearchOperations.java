package com.handicaptracker;

import java.sql.SQLException;
import java.util.List;

public interface ISearchOperations {
   public Course getCourse(String courseName, String courseCity, String courseState) throws SQLException;
   
   public List<Course> getAllCourses() throws SQLException;
   
   public List<Tee> getTees(Course course) throws SQLException;
   
   public Tee getTee(Course course, String teeColor) throws SQLException;
   
   public List<Course> searchByCourseName(String courseName) throws SQLException;
   
   
   
   
}
