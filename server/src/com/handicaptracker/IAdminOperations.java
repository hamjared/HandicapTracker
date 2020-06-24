package com.handicaptracker;

import java.sql.SQLException;

public interface IAdminOperations {

   /**
    * @param course Course to be added
    * @return
    * @throws SQLException 
    */
   public void addCourse(Course course) throws SQLException;

   /**
    * @param tee Tee to be added
    * @return
    * @throws SQLException 
    */
   public void addTees(Tee tee) throws SQLException;

   /**
    * @param Course course to be removed
    * @return
    */
   public boolean removeCourse(Course course);

   /**
    * @param tee Tee to be removed
    * @return
    */
   public boolean removeTee(Tee tee);

   /**
    * @param authorizingUser     Username of person granting admin priviliges
    * @param authorizingPassword Password of person granting admin priviliges
    * @param newAdminUserName    Username of person to grant priviliges to
    * @return
    */
   public boolean grantAdminPriviliges(String authorizingUser,
         String authorizingPassword, String newAdminUserName);

   /**
    * @param authorizingUser     Username of person revoking admin priviliges
    * @param authorizingPassword Password of person revoking admin priviliges
    * @param revokeAdminUserName Username of person to revoke priviliges from
    * @return
    */
   public boolean revokeAdminPriviliges(String authorizingUser,
         String authorizingPassword, String revokeAdminUserName);

}
