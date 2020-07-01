package com.handicaptracker;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ITeeBuilder
{
   /**
    * @param course
    * @return
    */
   public ITeeBuilder course(Course course);
   
   /**
    * @param teeColor
    * @return
    */
   public ITeeBuilder teeColor(String teeColor);
   
   /**
    * @param rating
    * @return
    */
   public ITeeBuilder rating(double rating);
   
   /**
    * @param slope
    * @return
    */
   public ITeeBuilder slope(int slope);
   
   /**
    * @param par
    * @return
    */
   public ITeeBuilder par(int par);
   
   /**
    * @param yardage
    * @return
    */
   public ITeeBuilder yardage(int yardage);
   

   /**
    * @param rs
    * @param course
    * @return
    * @throws SQLException
    */
   public ITeeBuilder fromResultSet(ResultSet rs, Course course) throws SQLException;
   
   /**
    * @return
    */
   public Tee build();
}
