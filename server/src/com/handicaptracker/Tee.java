package com.handicaptracker;

public class Tee {
   private Course course;
   private String teeColor;
   private double rating;
   private int slope;
   private int par;
   private int yardage;
   public Course getCourse()
   {
      return course;
   }
   
   public String toString() {
      return teeColor + "(" + rating + "/" + slope + " " + yardage + "yards)"; 
   }
   public void setCourse(Course course)
   {
      this.course = course;
   }
   public String getTeeColor()
   {
      return teeColor;
   }
   public void setTeeColor(String teeColor)
   {
      this.teeColor = teeColor;
   }
   public double getRating()
   {
      return rating;
   }
   public void setRating(double rating)
   {
      this.rating = rating;
   }
   public int getSlope()
   {
      return slope;
   }
   public void setSlope(int slope)
   {
      this.slope = slope;
   }
   public int getPar()
   {
      return par;
   }
   public void setPar(int par)
   {
      this.par = par;
   }
   public int getYardage()
   {
      return yardage;
   }
   public void setYardage(int yardage)
   {
      this.yardage = yardage;
   }
   
   
}
