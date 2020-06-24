package com.handicaptracker;

public interface IUserBuilder
{
   /**
    * @param userName
    * @return
    */
   public IUserBuilder username(String userName);
   
   /**
    * @param email
    * @return
    */
   public IUserBuilder email(String email);
   
   /**
    * @param firstName
    * @return
    */
   public IUserBuilder firstName(String firstName);
   
   /**
    * @param lastName
    * @return
    */
   public IUserBuilder lastName(String lastName);
   
   /**
    * @param isAdmin
    * @return
    */
   public IUserBuilder isAdmin(boolean isAdmin);
   
   /**
    * @param canChangePrivileges
    * @return
    */
   public IUserBuilder canChangePrivileges(boolean canChangePrivileges);
   
   /**
    * @param handicapDifferential
    * @return
    */
   public IUserBuilder handicapDifferential(double handicapDifferential);
   
   /**
    * @return
    */
   public User build();
}
