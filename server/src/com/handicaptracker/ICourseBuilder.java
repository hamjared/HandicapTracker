package com.handicaptracker;

public interface ICourseBuilder
{
   /**
    * @param id
    * @return
    */
   public ICourseBuilder id(int id);
   
   /**
    * @param name
    * @return
    */
   public ICourseBuilder name(String name);
   
   /**
    * @param state
    * @return
    */
   public ICourseBuilder state(String state);
   
   /**
    * @param city
    * @return
    */
   public ICourseBuilder city(String city);
   
   /**
    * @param numHoles
    * @return
    */
   public ICourseBuilder numHoles(int numHoles);
   
   /**
    * @return
    */
   public Course build();
}
