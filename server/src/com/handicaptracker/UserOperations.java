package com.handicaptracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class UserOperations extends Operations implements IUserOperations
{

   @Override
   public void createUser(String username, String password, String email,
         String firstName, String lastName)
         throws SQLException, NoSuchElementException
   {
      StringBuilder query = new StringBuilder("");
      query.append(
            "INSERT INTO users values (?,crypt(?,gen_salt('bf')),?, ?, ?, ?, ?)");
      Connection con = myConnectionPool.getConnection();
      PreparedStatement stmt = con.prepareStatement(query.toString());

      stmt.setString(1, username);
      stmt.setString(2, password);
      stmt.setString(3, email);
      stmt.setString(4, firstName);
      stmt.setString(5, lastName);
      stmt.setBoolean(6, false);
      stmt.setBoolean(7, false);

      stmt.executeUpdate();

      myConnectionPool.returnConnection(con);
   }

   @Override
   public void deleteUser(String username, String password) throws SQLException,
         NoSuchElementException, InvalidCredentialsException
   {
      login(username, password);

      StringBuilder query = new StringBuilder("");
      query.append("DELETE FROM users where username = ?");
      Connection con = myConnectionPool.getConnection();
      PreparedStatement stmt = con.prepareStatement(query.toString());
      stmt.setString(1, username);
      stmt.executeUpdate();

      myConnectionPool.returnConnection(con);
   }

   @Override
   public void changePassword(String username, String oldPassword,
         String newPassword) throws SQLException, NoSuchElementException,
         InvalidCredentialsException
   {
      login(username, oldPassword);

      StringBuilder query = new StringBuilder("");
      query.append("UPDATE users set password = crypt(?,gen_salt('bf')) ")
            .append("WHERE username = ?");
      Connection con = myConnectionPool.getConnection();
      PreparedStatement stmt = con.prepareStatement(query.toString());

      stmt.setString(1, newPassword);
      stmt.setString(2, username);

      stmt.executeUpdate();

      myConnectionPool.returnConnection(con);
   }

   @Override
   public User login(String username, String password)
         throws InvalidCredentialsException, SQLException
   {
      StringBuilder query = new StringBuilder("");
      query.append(
            "SELECT username, email, firstname, lastname, isAdmin, canchangepriviliges FROM Users where username = ? and password = crypt(?, password)");
      Connection con = myConnectionPool.getConnection();
      PreparedStatement stmt = con.prepareStatement(query.toString());
      stmt.setString(1, username);
      stmt.setString(2, password);
      stmt.executeQuery();
      myConnectionPool.returnConnection(con);

      ResultSet rs = stmt.getResultSet();
      User user = null;
      int rowCount = 0;
      while (rs.next())
      {
         rowCount++;
         IUserBuilder userBuilder = new UserBuilder();
         user = userBuilder.username(rs.getString(1)).email(rs.getString(2))
               .firstName(rs.getString(3)).lastName(rs.getString(4))
               .isAdmin(rs.getBoolean(5)).canChangePrivileges(rs.getBoolean(6))
               .build();
      }

      if (rowCount != 1)
      {
         throw new InvalidCredentialsException();
      }

      return user;

   }

}
