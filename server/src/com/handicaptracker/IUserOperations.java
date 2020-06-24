package com.handicaptracker;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public interface IUserOperations {

   /**
    * @param username
    * @param password
    * @param email
    * @param firstName
    * @param lastName
    * @throws SQLException 
    */
   public void createUser(String username, String password, String email,
         String firstName, String lastName) throws SQLException;

   /**
    * @param username
    * @param password
    * @return Returns true upon successful user deletion, otherwise returns
    *         false
    * @throws InvalidCredentialsException 
    * @throws NoSuchElementException 
    * @throws SQLException 
    */
   public void deleteUser(String username, String password) throws SQLException, NoSuchElementException, InvalidCredentialsException;

   /**
    * @param username
    * @param oldPassword
    * @param newPassword
    * @return Returns true upon successful password change, otherwise returns
    *         false
    * @throws NoSuchElementException 
    * @throws SQLException 
    */
   public void changePassword(String username, String oldPassword,
         String newPassword) throws SQLException, NoSuchElementException, InvalidCredentialsException;

   /**
    * @param username
    * @param password
    * @return Returns true if the password matches the username given, otherwise
    *         returns false.
    * @throws SQLException 
    */
   public User login(String username, String password) throws InvalidCredentialsException, SQLException;

}
