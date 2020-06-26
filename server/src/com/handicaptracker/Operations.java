package com.handicaptracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Operations implements ISearchOperations
{
   protected DatabaseConnectionPool myConnectionPool;

   Operations()
   {
      myConnectionPool = new DatabaseConnectionPool(2);
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

      Course course = null;
      while (rs.next())
      {

         IUserBuilder userBuilder = new UserBuilder();
         course = new CourseBuilder().id(rs.getInt(1)).name(rs.getString(2))
                  .city(rs.getString(3)).state(rs.getString(4))
                  .numHoles(rs.getInt(5)).build();
      }
      return course;
   }

}
