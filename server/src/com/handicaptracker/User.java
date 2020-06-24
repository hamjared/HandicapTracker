package com.handicaptracker;

public class User
{

   private String username;
   private String email;
   private String firstName;
   private String lastName;
   private boolean isAdmin;
   private boolean canChangePriviliges;
   private double handicapDifferential;

   public User()
   {

   }

   /**
    * @return
    */
   public String getUsername()
   {
      return username;
   }

   /**
    * @param username
    */
   public void setUsername(String username)
   {
      this.username = username;
   }

   /**
    * @return
    */
   public String getEmail()
   {
      return email;
   }

   /**
    * @param email
    */
   public void setEmail(String email)
   {
      this.email = email;
   }

   /**
    * @return
    */
   public String getFirstName()
   {
      return firstName;
   }

   /**
    * @param firstName
    */
   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   /**
    * @return
    */
   public String getLastName()
   {
      return lastName;
   }

   /**
    * @param lastName
    */
   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   /**
    * @return
    */
   public boolean isAdmin()
   {
      return isAdmin;
   }

   /**
    * @param isAdmin
    */
   public void setAdmin(boolean isAdmin)
   {
      this.isAdmin = isAdmin;
   }

   /**
    * @return
    */
   public boolean canChangePriviliges()
   {
      return canChangePriviliges;
   }

   /**
    * @param canChangePriviliges
    */
   public void setCanChangePriviliges(boolean canChangePriviliges)
   {
      this.canChangePriviliges = canChangePriviliges;
   }

   /**
    * @return
    */
   public double getHandicapDifferential()
   {
      return handicapDifferential;
   }

   /**
    * @param handicapDifferential
    */
   public void setHandicapDifferential(double handicapDifferential)
   {
      this.handicapDifferential = handicapDifferential;
   }

}
