package com.handicaptracker;

public class UserBuilder implements IUserBuilder
{
   
   User user;
   
   public UserBuilder() {
      user = new User();
   }

   @Override
   public IUserBuilder username(String userName)
   {
      user.setUsername(userName);
      return this;
   }

   @Override
   public IUserBuilder email(String email)
   {
      user.setEmail(email);
      return this;
   }

   @Override
   public IUserBuilder firstName(String firstName)
   {
      user.setFirstName(firstName);
      return this;
   }

   @Override
   public IUserBuilder lastName(String lastName)
   {
      user.setLastName(lastName);
      return this;
   }

   @Override
   public IUserBuilder isAdmin(boolean isAdmin)
   {
    user.setAdmin(isAdmin);
    return this;
   }

   @Override
   public IUserBuilder canChangePrivileges(boolean canChangePriviliges)
   {
      user.setCanChangePriviliges(canChangePriviliges);
      return this;
   }

   @Override
   public IUserBuilder handicapDifferential(double handicapDifferential)
   {
      user.setHandicapDifferential(handicapDifferential);
      return this;
   }

   @Override
   public User build()
   {
      return user;
   }

}
