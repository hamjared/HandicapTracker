package com.handicaptracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminOperations extends Operations implements IAdminOperations {

   @Override
   public void addCourse(Course course) throws SQLException {
      StringBuilder query = new StringBuilder();
      query.append("INSERT INTO course VALUES (default, ?, ?, ?, ?)");
      Connection con = myConnectionPool.getConnection();
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setString(1, course.getCourseName());
         stmt.setString(2, course.getCourseCity());
         stmt.setString(3, course.getCourseState());
         stmt.setInt(4, course.getNumHoles());
         stmt.executeUpdate();
      } catch (SQLException e)
      {
         throw e;
      }finally {
         myConnectionPool.returnConnection(con);
      }
   }

   @Override
   public void addTees(Tee tee) throws SQLException {
      // TODO Auto-generated method stub
      StringBuilder query = new StringBuilder();
      query.append("INSERT INTO tees VALUES (?, ?, ?, ?, ?, ?)");
      Connection con = myConnectionPool.getConnection();
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setInt(1, tee.getCourse().getCourseId());
         stmt.setString(2, tee.getTeeColor());
         stmt.setDouble(3, tee.getRating());
         stmt.setInt(4, tee.getSlope());
         stmt.setInt(5,  tee.getPar());
         stmt.setInt(6, tee.getYardage());
         stmt.executeUpdate();
      } catch (SQLException e)
      {
         throw e;
      }finally {
         myConnectionPool.returnConnection(con);
      }
   }

   @Override
   public boolean removeCourse(Course course) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean removeTee(Tee tee) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean grantAdminPriviliges(String authorizingUser,
         String authorizingPassword, String newAdminUserName) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean revokeAdminPriviliges(String authorizingUser,
         String authorizingPassword, String revokeAdminUserName) {
      // TODO Auto-generated method stub
      return false;
   }

}
