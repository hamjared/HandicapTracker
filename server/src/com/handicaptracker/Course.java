package com.handicaptracker;

import java.sql.SQLException;

public class Course {
   private int courseId;
   private String courseName;
   private String courseCity;
   private String courseState;
   private int numHoles;
   
   public Course() {
      
   }
   
   public String toString() {
      return courseName + "(" + courseCity + ", " + courseState + ")";
   }

   /**
    * @return
    */
   public int getCourseId()
   {
      if(courseId != 0) {
         return courseId;
      }
      ISearchOperations search = new Operations();
      try
      {
         Course course = search.getCourse(courseName, courseCity, courseState);
         courseId = course != null ? course.getCourseId() : 0;
      } catch (SQLException e)
      {
         
      }
      return courseId;
   }

   /**
    * @param courseId
    */
   public void setCourseId(int courseId)
   {
      this.courseId = courseId;
   }

   /**
    * @return
    */
   public String getCourseName()
   {
      return courseName;
   }

   /**
    * @param courseName
    */
   public void setCourseName(String courseName)
   {
      this.courseName = courseName;
   }

   /**
    * @return
    */
   public String getCourseCity()
   {
      return courseCity;
   }

   /**
    * @param courseCity
    */
   public void setCourseCity(String courseCity)
   {
      this.courseCity = courseCity;
   }

   /**
    * @return
    */
   public String getCourseState()
   {
      return courseState;
   }

   /**
    * @param courseState
    */
   public void setCourseState(String courseState)
   {
      this.courseState = courseState;
   }

   /**
    * @return
    */
   public int getNumHoles()
   {
      return numHoles;
   }

   /**
    * @param numHoles
    */
   public void setNumHoles(int numHoles)
   {
      this.numHoles = numHoles;
   }
   
   
}
