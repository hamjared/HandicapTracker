package com.handicaptracker;

import java.sql.SQLException;

public class CourseBuilder implements ICourseBuilder
{

   Course course;

   public CourseBuilder()
   {
      course = new Course();
   }

   @Override
   public ICourseBuilder id(int id)
   {
      course.setCourseId(id);
      return this;
   }

   @Override
   public ICourseBuilder name(String name)
   {
      course.setCourseName(name);
      return this;
   }

   @Override
   public ICourseBuilder state(String state)
   {
      course.setCourseState(state);
      return this;
   }

   @Override
   public ICourseBuilder city(String city)
   {
      course.setCourseCity(city);
      return this;
   }

   @Override
   public ICourseBuilder numHoles(int numHoles)
   {
      course.setNumHoles(numHoles);
      return this;
   }

   @Override
   public Course build()
   {
      
      return course;
   }

}
