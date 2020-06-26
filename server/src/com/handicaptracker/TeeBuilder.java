package com.handicaptracker;

public class TeeBuilder implements ITeeBuilder
{
   Tee tee;
   
   public TeeBuilder(){
      tee = new Tee();
   }
   @Override
   public ITeeBuilder course(Course course)
   {
     tee.setCourse(course);
     return this;
   }

   @Override
   public ITeeBuilder teeColor(String teeColor)
   {
      tee.setTeeColor(teeColor);
      return this;
   }

   @Override
   public ITeeBuilder rating(double rating)
   {
      tee.setRating(rating);
      return this;
   }

   @Override
   public ITeeBuilder slope(int slope)
   {
      tee.setSlope(slope);
      return this;
   }

   @Override
   public ITeeBuilder par(int par)
   {
      tee.setPar(par);
      return this;
   }

   @Override
   public ITeeBuilder yardage(int yardage)
   {
      tee.setYardage(yardage);
      return this;
   }
   @Override
   public Tee build()
   {
      return tee;
   }

}
