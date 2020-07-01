package com.handicaptracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Operations implements ISearchOperations
{
   protected DatabaseConnectionPool myConnectionPool;

   public Operations()
   {
      myConnectionPool = DatabaseConnectionPool.getInstance();
      myConnectionPool.initialize();
   }

   @Override
   public Course getCourse(String courseName, String courseCity,
            String courseState) throws SQLException
   {
      StringBuilder query = new StringBuilder();
      query.append(
               "SELECT courseid, courseName, courseCity, courseState, numHoles ")
               .append("FROM course ")
               .append("WHERE courseName = ? and courseCity = ? and courseState = ?");
      Connection con = myConnectionPool.getConnection();

      ResultSet rs; 
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setString(1, courseName);
         stmt.setString(2, courseCity);
         stmt.setString(3, courseState);
         rs =  stmt.executeQuery();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con); 
      }

      rs.next();
      return new CourseBuilder().fromResultSet(rs).build();
   }

   @Override
   public List<Course> getAllCourses() throws SQLException
   {
      List<Course> courses = new ArrayList<Course>();
      StringBuilder query = new StringBuilder();
      query.append(
               "SELECT courseid, courseName, courseCity, courseState, numHoles ")
               .append("FROM course");
      Connection con = myConnectionPool.getConnection();

      ResultSet rs; 
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         rs =  stmt.executeQuery();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
      while(rs.next()) {
         courses.add(new CourseBuilder().fromResultSet(rs).build());
      }
      return courses;
   }

   @Override
   public List<Tee> getTees(Course course) throws SQLException
   {
      List<Tee> tees = new ArrayList<Tee>();
      StringBuilder query = new StringBuilder();
      query.append(
               "SELECT courseid, teecolor, rating, slope, par, yardage ")
               .append("FROM tees ")
               .append("WHERE courseId = ?");
      Connection con = myConnectionPool.getConnection();

      ResultSet rs; 
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setInt(1, course.getCourseId());
         rs =  stmt.executeQuery();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
      
      while(rs.next()) {
         tees.add(new TeeBuilder().fromResultSet(rs, course).build());
      }
      
      return tees;
   }

   @Override
   public Tee getTee(Course course, String teeColor) throws SQLException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public List<Course> searchByCourseName(String courseName) throws SQLException
   {
      List<Course> courses = new ArrayList<>();
      StringBuilder query = new StringBuilder();
      query.append(
               "SELECT courseid, courseName, courseCity, courseState, numHoles ")
               .append("FROM course ")
               .append("WHERE courseName = ?");
      Connection con = myConnectionPool.getConnection();

      ResultSet rs; 
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setString(1, courseName);
         rs =  stmt.executeQuery();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con); 
      }

      
      while(rs.next()) {
         courses.add(new CourseBuilder().fromResultSet(rs).build());
      }
      return courses;

   }

}
