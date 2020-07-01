package com.handicaptracker;

import java.sql.Date;

public class Round {
   private String golferUserName;
   private int courseId;
   private String teeColor;
   private int score;
   private double differential;
   private Date datePlayed;
   
   public String toString() {
      return datePlayed + "  " + score + "  " + differential; 
   }
   
   public String getGolferUserName()
   {
      return golferUserName;
   }
   public void setGolferUserName(String golferUserName)
   {
      this.golferUserName = golferUserName;
   }
   public int getCourseId()
   {
      return courseId;
   }
   public void setCourseId(int courseId)
   {
      this.courseId = courseId;
   }
   public String getTeeColor()
   {
      return teeColor;
   }
   public void setTeeColor(String teeColor)
   {
      this.teeColor = teeColor;
   }
   public int getScore()
   {
      return score;
   }
   public void setScore(int score)
   {
      this.score = score;
   }
   public double getDifferential()
   {
      return differential;
   }
   public void setDifferential(double differential)
   {
      this.differential = differential;
   }
   public Date getDatePlayed()
   {
      return datePlayed;
   }
   public void setDatePlayed(Date datePlayed)
   {
      this.datePlayed = datePlayed;
   }
   
   
   
}
