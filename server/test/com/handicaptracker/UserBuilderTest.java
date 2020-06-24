package com.handicaptracker;

import junit.framework.TestCase;

public class UserBuilderTest extends TestCase
{

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   public void testBuild()
   {
      IUserBuilder userBuilder = new UserBuilder();

      User bob = userBuilder.username("bob20").email("bob@email.com")
            .firstName("bob").lastName("Smith").isAdmin(false)
            .canChangePrivileges(false).handicapDifferential(10.2).build();
      
      assertEquals("bob20", bob.getUsername());
      assertEquals("bob@email.com", bob.getEmail());
      assertEquals("Smith", bob.getLastName());
      assertEquals("bob", bob.getFirstName());
      assertEquals(10.2, bob.getHandicapDifferential());
      assertEquals(false, bob.isAdmin());
      assertEquals(false, bob.canChangePriviliges());
      
   }

}
