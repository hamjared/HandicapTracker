package com.handicaptracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GolferOperations extends Operations implements IGolferOperations
{

   @Override
   public void addRound(Round round) throws SQLException
   {
      StringBuilder query = new StringBuilder();
      query.append("INSERT INTO rounds VALUES (?, ?, ?, ?, ?, null)");
      Connection con = myConnectionPool.getConnection();
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setDate(1, round.getDatePlayed());
         stmt.setString(2, round.getGolferUserName());
         stmt.setInt(3, round.getCourseId());
         stmt.setString(4, round.getTeeColor());
         stmt.setInt(5, round.getScore());
         stmt.executeUpdate();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
   }

   @Override
   public void deleteRound(Round round) throws SQLException
   {
      StringBuilder query = new StringBuilder();
      query.append(
            "Delete from rounds where datePlayed = ? and golferUsername = ?");
      Connection con = myConnectionPool.getConnection();
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setDate(1, round.getDatePlayed());
         stmt.setString(2, round.getGolferUserName());
         stmt.executeUpdate();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }
   }

   @Override
   public Double getHandicapIndex(User user) throws SQLException
   {
      StringBuilder query = new StringBuilder();
      query.append("SELECT gethandicapindex(?)");
      Connection con = myConnectionPool.getConnection();

      ResultSet rs;
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setString(1, user.getUsername());
         rs = stmt.executeQuery();
      } catch (SQLException e)
      {
         throw e;
      } finally
      {
         myConnectionPool.returnConnection(con);
      }

      while (rs.next())
      {
         return rs.getDouble(1);
      }

      return null;

   }

   @Override
   public Integer getCourseHandicap(Tee tee, User user) throws SQLException
   {
      double handicapIndex = getHandicapIndex(user);

      double courseHandicap = handicapIndex * tee.getSlope() / 113.0
            + (tee.getRating() - tee.getPar());

      return (int) Math.round(courseHandicap);

   }

   @Override
   public List<Round> getRounds(User user) throws SQLException
   {
      List<Round> rounds = new ArrayList<Round>();
      StringBuilder query = new StringBuilder();
      query.append(
            "SELECT dateplayed, golferusername, courseid, teecolor, score, differential ")
            .append("FROM rounds ")
            .append("WHERE golferusername = ? ")
            .append("ORDER BY dateplayed");
      Connection con = myConnectionPool.getConnection();

      ResultSet rs;
      try
      {
         PreparedStatement stmt = con.prepareStatement(query.toString());
         stmt.setString(1, user.getUsername());
         rs = stmt.executeQuery();
      } catch (SQLException e)
      {
         throw e; 
      } finally
      {
         myConnectionPool.returnConnection(con);
      }

      
      
      while (rs.next())
      {
         Round round = new RoundBuilder().datePlayed(rs.getDate(1))
               .golferUsername(rs.getString(2))
               .courseId(rs.getInt(3))
               .teeColor(rs.getString(4))
               .score(rs.getInt(5))
               .differential(rs.getDouble(6))
               .build();
         
         rounds.add(round);
      }

      return rounds;

   }


}
