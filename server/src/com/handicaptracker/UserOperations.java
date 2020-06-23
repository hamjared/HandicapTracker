package com.handicaptracker;

public class UserOperations extends Operations implements IUserOperations {

   @Override
   public boolean createUser(String username, String password, String email,
         String firstName, String lastName) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean deleteUser(String username, String password) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean changePassword(String username, String oldPassword,
         String newPassword) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public User login(String username, String password) {
      // TODO Auto-generated method stub
      return null;
   }

}
