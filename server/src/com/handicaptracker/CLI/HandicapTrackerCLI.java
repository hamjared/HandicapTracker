package com.handicaptracker.CLI;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
               "Would you like to login or create account [login/create]:");
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
      System.out.println("\tList Rounds [lr]");
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
      case "lr":
         listRounds();
         break;
      case "exit":
         System.exit(1);
         break;
      default:
         optionsMenu();
         return;

      }

   }

   private void listRounds()
   {
      IGolferOperations golfer = new GolferOperations();
      List<Round> rounds = null;
      try
      {
         rounds = golfer.getRounds(user);
      } catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      rounds.forEach((round) -> {
         System.out.println(round);
      });
      this.optionsMenu();
      
   }

   private void calculateCourseHandicap()
   {
      IGolferOperations golfer = new GolferOperations();
      System.out.print("Course: ");
      String courseName = input.nextLine();
      ISearchOperations search = new Operations();
      List<Course> courses = null;
      try
      {
         courses = search.searchByCourseName(courseName);
      } catch (SQLException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      System.out.println(courses);
      System.out.print("Select course: ");
      int courseIndex = input.nextInt();
      input.nextLine();
      System.out.println("Tees: ");
      List<Tee> tees = null;
      try
      {
         tees = search.getTees(courses.get(courseIndex));
      } catch (SQLException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      System.out.println(tees);
      System.out.print("Select Tee: ");
      int teeIndex = input.nextInt();
      input.nextLine();
      int courseHandicap;
      try
      {
         courseHandicap = golfer.getCourseHandicap(tees.get(teeIndex), user);
      } catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         this.optionsMenu();
         return;
      }
      System.out.println("Your Course Handicap is : " + courseHandicap);
      this.optionsMenu();

   }

   private void calculateHandicapIndex()
   {
      IGolferOperations golfer = new GolferOperations();
      double hci;
      try
      {
         hci = golfer.getHandicapIndex(user);
      } catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         this.optionsMenu();
         return;
      }
      System.out.println("Your Handicap Index is : " + hci);
      this.optionsMenu();
   }

   private void addRound()
   {
      ISearchOperations search = new Operations();
      System.out.println("Courses:");
      List<Course> courses = null;
      try
      {
         courses = search.getAllCourses();
      } catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      System.out.println(courses);
      System.out.print("Selected course: ");
      int courseIndex = input.nextInt();
      input.nextLine();
      System.out.println("Tees: ");
      List<Tee> tees = null;
      try
      {
        tees = search.getTees(courses.get(courseIndex));
      } catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      System.out.println(tees);
      System.out.println("Selected Tee: ");
      int teeIndex = input.nextInt();
      input.nextLine();
      System.out.print("Dateplayed (mm/dd/YYYY): ");
      String date = input.nextLine();
      System.out.print("score: ");
      int score = input.nextInt();
      input.nextLine();
      IGolferOperations golfer = new GolferOperations();
      Round round = new RoundBuilder().courseId(courses.get(courseIndex).getCourseId())
            .datePlayed(this.dateConverter(date))
            .golferUsername(user.getUsername())
            .teeColor(tees.get(teeIndex).getTeeColor())
            .score(score).build();
      try
      {
         golfer.addRound(round);
      } catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      System.out.println(round);
      System.out.println( "Round added successfulyy!");
      this.optionsMenu();

   }

   private void addTee() 
   {
      
      IAdminOperations adminOperations = new AdminOperations();
      System.out.println("-----------ADD TEE----------");
      System.out.print("Course Name: ");
      String courseName = input.nextLine().trim();
      ISearchOperations search = new Operations();
      List<Course> courses = null;
      try
      {
         courses = search.searchByCourseName(courseName);
      } catch (SQLException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      System.out.println(courses);
      System.out.print("Select Course: ");
      int courseIndex = input.nextInt();
      input.nextLine();
      Course course = courses.get(courseIndex);
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
            addTee();
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
   
   
   private java.sql.Date dateConverter(String date){
      SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
      java.util.Date jDate = null;
      try
      {
         jDate = formatter.parse(date);
      } catch (ParseException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return new java.sql.Date(jDate.getTime());
      
   }

}
