package com.handicaptracker;

import java.sql.SQLException;

public interface IGolferOperations {

	public void addRound(Round round) throws SQLException;
	
	public void deleteRound(Round round) throws SQLException;
	
	public Double getHandicapIndex(User user) throws SQLException;
	
	public Integer getCourseHandicap(Tee tee, User user) throws SQLException;
	
	public void getRounds(User user) throws SQLException;
	
	public void getRounds(User use, int limit) throws Exception;
		
}
