package com.handicaptracker;

import java.sql.Date;

public class RoundBuilder implements IRoundBuilder
{

   
   Round round;
   
   public RoundBuilder() {
      round = new Round();
   }
   
   @Override
   public IRoundBuilder datePlayed(Date datePlayed)
   {
      round.setDatePlayed(datePlayed);
      return this;
   }

   @Override
   public IRoundBuilder golferUsername(String golferUsername)
   {
      round.setGolferUserName(golferUsername);
      return this;
   }

   @Override
   public IRoundBuilder courseId(int courseId)
   {
      round.setCourseId(courseId);
      return this;
   }

   @Override
   public IRoundBuilder teeColor(String teeColor)
   {
      round.setTeeColor(teeColor);
      return this;
   }

   @Override
   public IRoundBuilder score(int score)
   {
      round.setScore(score);
      return this;
   }

   @Override
   public IRoundBuilder differential(double differential)
   {
      round.setDifferential(differential);
      return this;
   }

   @Override
   public Round build()
   {
      return round;
   }

}
