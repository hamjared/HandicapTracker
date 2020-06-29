package com.handicaptracker;

import java.sql.SQLException;
import java.util.List;

public interface IGolferOperations {

	public void addRound(Round round) throws SQLException;
	
	public void deleteRound(Round round) throws SQLException;
	
	public Double getHandicapIndex(User user) throws SQLException;
	
	public Integer getCourseHandicap(Tee tee, User user) throws SQLException;
	
	public List<Round> getRounds(User user) throws SQLException;
		
}
