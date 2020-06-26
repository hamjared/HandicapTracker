package com.handicaptracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminOperations extends Operations implements IAdminOperations
{

   @Override
   public void addCourse(Course course, User user, String password)
            throws SQLException, InvalidCredentialsException
   {
      checkIsAdmin(user, password);
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
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
   }

   @Override
   public void addTees(Tee tee, User user, String password)
            throws SQLException, InvalidCredentialsException
   {
      checkIsAdmin(user, password);
      if (tee.getCourse().getCourseId() == 0)
      {
         ISearchOperations search = new Operations();
         tee.setCourse(search.getCourse(tee.getCourse().getCourseName(),
                  tee.getCourse().getCourseCity(),
                  tee.getCourse().getCourseState()));
      }
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
         stmt.setInt(5, tee.getPar());
         stmt.setInt(6, tee.getYardage());
         stmt.executeUpdate();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
   }

   @Override
   public void removeCourse(Course course, User user, String password)
            throws SQLException, InvalidCredentialsException
   {
      checkIsAdmin(user, password);
      
      StringBuilder query = new StringBuilder();
      query.append("DELETE FROM course WHERE courseName = ? and courseCity = ? and courseState = ?");
      Connection con = myConnectionPool.getConnection();
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setString(1, course.getCourseName());
         stmt.setString(2, course.getCourseCity());
         stmt.setString(3, course.getCourseState());
         stmt.executeUpdate();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
      
   }

   @Override
   public void removeTee(Tee tee, User user, String password)
            throws SQLException, InvalidCredentialsException
   {
checkIsAdmin(user, password);
      
      StringBuilder query = new StringBuilder();
      query.append("DELETE FROM tees WHERE courseId = ? and teeColor = ?");
      Connection con = myConnectionPool.getConnection();
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setInt(1, tee.getCourse().getCourseId());
         stmt.setString(2, tee.getTeeColor());
         stmt.executeUpdate();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
   }

   @Override
   public boolean grantAdminPriviliges(String authorizingUser,
            String authorizingPassword, String newAdminUserName)
            throws SQLException, InvalidCredentialsException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean revokeAdminPriviliges(String authorizingUser,
            String authorizingPassword, String revokeAdminUserName)
            throws SQLException, InvalidCredentialsException
   {
      // TODO Auto-generated method stub
      return false;
   }

   private void checkIsAdmin(User user, String password)
            throws InvalidCredentialsException, SQLException
   {
      IUserOperations userOperations = new UserOperations();
      user = userOperations.login(user.getUsername(), password);
      if (!user.isAdmin())
      {
         throw new InvalidCredentialsException();
      }

   }

   private void checkCanGrantPrivileges(User user, String password)
            throws InvalidCredentialsException
   {

   }
}
