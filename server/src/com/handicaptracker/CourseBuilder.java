package com.handicaptracker;

import java.sql.ResultSet;
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

   @Override
   public ICourseBuilder fromResultSet(ResultSet rs) throws SQLException
   {

      this.id(rs.getInt(1))
            .name(rs.getString(2))
            .city(rs.getString(3))
            .state(rs.getString(4))
            .numHoles(rs.getInt(5));

      return this;

   }

}
