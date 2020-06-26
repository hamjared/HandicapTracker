package com.handicaptracker;

import java.sql.SQLException;

public interface IAdminOperations {


   /**
    * @param course
    * @param user
    * @param password
    * @throws SQLException
    * @throws InvalidCredentialsException
    */
   public void addCourse(Course course, User user, String password) throws SQLException, InvalidCredentialsException;

   /**
    * @param tee
    * @param user
    * @param password
    * @throws SQLException
    * @throws InvalidCredentialsException 
    */
   public void addTees(Tee tee, User user, String password) throws SQLException, InvalidCredentialsException;

   /**
    * @param course
    * @param user
    * @param password
    * @return
    * @throws InvalidCredentialsException 
    * @throws SQLException 
    */
   public void removeCourse(Course course, User user, String password) throws SQLException, InvalidCredentialsException;

   /**
    * @param tee
    * @param user
    * @param password
    * @return
    * @throws InvalidCredentialsException 
    * @throws SQLException 
    */
   public void removeTee(Tee tee, User user, String password) throws SQLException, InvalidCredentialsException;

   /**
    * @param authorizingUser
    * @param authorizingPassword
    * @param newAdminUserName
    * @return
    * @throws InvalidCredentialsException 
    * @throws SQLException 
    */
   public boolean grantAdminPriviliges(String authorizingUser,
         String authorizingPassword, String newAdminUserName) throws SQLException, InvalidCredentialsException;

   /**
    * @param authorizingUser
    * @param authorizingPassword
    * @param revokeAdminUserName
    * @return
    * @throws InvalidCredentialsException 
    * @throws SQLException 
    */
   public boolean revokeAdminPriviliges(String authorizingUser,
         String authorizingPassword, String revokeAdminUserName) throws SQLException, InvalidCredentialsException;

}
