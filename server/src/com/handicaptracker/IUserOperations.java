package com.handicaptracker;

public interface IUserOperations {

   /**
    * @param username
    * @param password
    * @param email
    * @param firstName
    * @param lastName
    * @return Returns true upon successful user creation, otherwise returns
    *         false
    */
   public boolean createUser(String username, String password, String email,
         String firstName, String lastName);

   /**
    * @param username
    * @param password
    * @return Returns true upon successful user deletion, otherwise returns
    *         false
    */
   public boolean deleteUser(String username, String password);

   /**
    * @param username
    * @param oldPassword
    * @param newPassword
    * @return Returns true upon successful password change, otherwise returns
    *         false
    */
   public boolean changePassword(String username, String oldPassword,
         String newPassword);

   /**
    * @param username
    * @param password
    * @return Returns true if the password matches the username given, otherwise
    *         returns false.
    */
   public User login(String username, String password);

}
