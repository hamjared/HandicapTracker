package com.handicaptracker;

import java.sql.PreparedStatement;
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
      PreparedStatement stmt = super.getConnectionPool()
                                    .getConnection()
                                    .prepareStatement(query.toString());

      stmt.setString(1, username);
      stmt.setString(2, password);
      stmt.setString(3, email);
      stmt.setString(4, firstName);
      stmt.setString(5, lastName);
      stmt.setBoolean(6, false);
      stmt.setBoolean(7, false);

      stmt.executeUpdate();
   }

   @Override
   public boolean deleteUser(String username, String password)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean changePassword(String username, String oldPassword,
         String newPassword)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public User login(String username, String password)
   {
      // TODO Auto-generated method stub
      return null;
   }

}
