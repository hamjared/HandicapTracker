package com.handicaptracker;

import junit.framework.TestCase;

public class TeeBuilderTest extends TestCase
{

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   public void testTeeBuilder()
   {
      TeeBuilder teeBuilder = new TeeBuilder();
      Course hm = new CourseBuilder().name("Highland Meadows").city("Windsor")
               .state("CO").numHoles(18).build();

      Tee tee = teeBuilder.course(hm).teeColor("BLUE").rating(70.2).slope(128)
               .par(71).yardage(6500).build();
      
      assertEquals(71,tee.getPar());
      assertEquals(128, tee.getSlope());
      assertEquals(70.2, tee.getRating());
      assertEquals("BLUE", tee.getTeeColor());
      
   }

}
