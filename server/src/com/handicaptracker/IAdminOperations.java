package com.handicaptracker;

public interface IAdminOperations {

   /**
    * @param course Course to be added
    * @return
    */
   public boolean addCourse(Course course);

   /**
    * @param tee Tee to be added
    * @return
    */
   public boolean addTees(Tee tee);

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
