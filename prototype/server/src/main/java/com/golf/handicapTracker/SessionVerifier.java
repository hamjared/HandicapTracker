package com.golf.handicapTracker;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;

public class SessionVerifier {
	
	private static  Map<String, HashMap<String, String>> activeSessions;

	public SessionVerifier() {
		activeSessions = new HashMap<String, HashMap<String, String>>();
	}
	
	public void addNewSession(String ip, String token, String userName) {
		HashMap<String, String> sessionInfo = new HashMap<>();
		sessionInfo.put("token", token);
		sessionInfo.put("userName", userName);
		activeSessions.put(ip, sessionInfo);
		
	}
	
	public String verifySession(String ip, String token) throws InvalidIpException, InvalidTokenException {
		if(activeSessions.get(ip) == null) {
			throw new InvalidIpException();
		}
		
		JWTVerifier tokenVerifier;
		
		try {
			tokenVerifier = JWT.require(Algorithm.HMAC256("marvin123")).build();
			tokenVerifier.verify(token);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			throw new InvalidTokenException();
		}
		
		
		
		return null;
	}
	


}
