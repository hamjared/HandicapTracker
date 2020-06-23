package com.handicaptracker;

public class AdminOperations extends Operations implements IAdminOperations {

   @Override
   public boolean addCourse(Course course) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean addTees(Tee tee) {
      // TODO Auto-generated method stub
      return false;
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
