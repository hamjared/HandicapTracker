package com.handicaptracker;

import java.sql.Date;

public interface IRoundBuilder
{
   public IRoundBuilder datePlayed(Date datePlayed);
   
   public IRoundBuilder golferUsername(String golferUsername);
   
   public IRoundBuilder courseId(int courseId);
   
   public IRoundBuilder teeColor(String teeColor);
   
   public IRoundBuilder score(int score);
   
   public IRoundBuilder differential(double differential);
   
   public Round build();
   
   
}
