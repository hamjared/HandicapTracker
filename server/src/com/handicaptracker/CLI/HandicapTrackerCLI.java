package com.handicaptracker.CLI;

import java.sql.SQLException;
import java.util.Scanner;

import com.handicaptracker.*;

public class HandicapTrackerCLI
{
   User user;
   Scanner input;

   public HandicapTrackerCLI()
   {
      input = new Scanner(System.in);
   }

   public void startup()
   {
      System.out.println("Welcome to Handicap Tracker");
      System.out.print(
               "Would you like to login or create accoutn [login/create]:");
      String option = input.nextLine().trim();
      switch (option.toLowerCase())
      {
      case "login":
         login();
         break;
      case "create":
         createUser();
         break;
      }
   }

   private void createUser()
   {
      System.out.print("username: ");
      String username = input.nextLine().trim();
      System.out.print("password: ");
      String password = input.nextLine().trim();
      System.out.print("first name: ");
      String firstName = input.nextLine().trim();
      System.out.print("last name: ");
      String lastName = input.nextLine().trim();
      System.out.print("email: ");
      String email = input.nextLine().trim();
      IUserOperations userOperations = new UserOperations();
      User user = new UserBuilder().username(username).email(email)
               .firstName(firstName).lastName(lastName).build();
      try
      {
         userOperations.createUser(user, password);
      } catch (SQLException e)
      {
         System.out.println("User creation failed, exiting");
         System.exit(1);
      }

      System.out.println("User created suucessfully");
      login();

   }

   public void login()
   {
      System.out.print("username: ");
      String username = input.nextLine().trim();
      System.out.print("password: ");
      String password = input.nextLine().trim();
      try
      {
         user = new UserOperations().login(username, password);
      } catch (InvalidCredentialsException | SQLException e)
      {
         System.out.println("Invalid Username or password, try again");
         login();
         return;
      }

      System.out.println("Hello: " + user);
      optionsMenu();

   }

   private void optionsMenu()
   {
      System.out.println("Options: ");
      adminOptions();
      System.out.println("\tAdd Round [ar]");
      System.out.println("\tCalculate Handicap Index [ch]");
      System.out.println("\tCalculate Course Index [cch]");
      System.out.println("\tExit [exit]");
      System.out.print("Option: ");

      String option = input.nextLine().trim().toLowerCase();

      switch (option)
      {
      case "ac":
         addCourse();
         break;
      case "at":
         addTee();
         break;
      case "ar":
         addRound();
         break;
      case "ch":
         calculateHandicapIndex();
         break;
      case "cch":
         calculateCourseHandicap();
         break;
      case "exit":
         System.exit(1);
         break;
      default:
         optionsMenu();
         return;

      }

   }

   private void calculateCourseHandicap()
   {
      System.out.println("Not yet implemented");
      optionsMenu();

   }

   private void calculateHandicapIndex()
   {
      System.out.println("Not yet implemented");
      optionsMenu();

   }

   private void addRound()
   {
      System.out.println("Not yet implemented");
      optionsMenu();

   }

   private void addTee()
   {
      IAdminOperations adminOperations = new AdminOperations();
      System.out.println("-----------ADD TEE----------");
      System.out.print("Course Name: ");
      String courseName = input.nextLine().trim();
      System.out.print("Course City: ");
      String courseCity = input.nextLine().trim();
      System.out.print("Course State: ");
      String courseState = input.nextLine().trim();
      System.out.print("Tee Color: ");
      String teeColor = input.nextLine();
      System.out.print("Rating: ");
      double rating = input.nextDouble();
      input.nextLine();
      System.out.print("Slope: ");
      int slope = input.nextInt();
      input.nextLine();
      System.out.print("Par: ");
      int par = input.nextInt();
      System.out.print("Yardage: ");
      int yardage = input.nextInt();
      input.nextLine();
      Course course = new CourseBuilder().name(courseName).city(courseCity)
               .state(courseState).build();
      Tee tee = new TeeBuilder().teeColor(teeColor).rating(rating).slope(slope)
               .par(par).yardage(yardage).course(course).build();
      System.out.println(tee);
      System.out.println("Confirm tee creation by typing password ");
      System.out.print("password: ");
      String password = input.nextLine();
      try
      {
         adminOperations.addTees(tee, user, password);
         System.out.println("Tee added succesfully");
      } catch (SQLException | InvalidCredentialsException e)
      {
         System.out.println("Tee creation failed. Try again? [y/n]: ");
         if (input.nextLine().equalsIgnoreCase("y"))
         {
            addCourse();
            return;
         }
      }

      optionsMenu();

   }

   private void addCourse()
   {
      IAdminOperations adminOperations = new AdminOperations();
      System.out.println("-----------ADD COURSE----------");
      System.out.print("Course Name: ");
      String courseName = input.nextLine().trim();
      System.out.print("Course City:");
      String courseCity = input.nextLine().trim();
      System.out.print("Course State:");
      String courseState = input.nextLine().trim();
      System.out.print("Number of Holes:");
      int numHoles = input.nextInt();
      input.nextLine();
      Course course = new CourseBuilder().name(courseName).city(courseCity)
               .state(courseState).numHoles(numHoles).build();
      System.out.println(course);
      System.out.println("Confirm course creation by typing password ");
      System.out.print("password: ");
      String password = input.nextLine();
      try
      {
         adminOperations.addCourse(course, user, password);
         System.out.println("Course added succesfully");
      } catch (SQLException | InvalidCredentialsException e)
      {
         System.out.println("Course creation failed. Try again? [y/n]: ");
         if (input.nextLine().equalsIgnoreCase("y"))
         {
            addCourse();
            return;
         }
      }

      optionsMenu();

   }

   private void adminOptions()
   {
      if (!user.isAdmin())
      {
         return;
      }
      System.out.println("\tAdd Course [ac]");
      System.out.println("\tAddTee [at]");

   }

}
