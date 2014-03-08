package controllers;

import java.util.HashMap;
import java.util.Map;

import com.opentok.api.API_Config;
import com.opentok.exception.OpenTokException;



public class SessionControl {
	
	private Map <String, String> sessions;
	private OpenTokInterface oti;
	
	public SessionControl () {
		sessions = new HashMap<String, String>();
		oti = new OpenTokInterface();
	}
	
	private boolean exists(String room) {
		if(sessions.containsKey(room)) {
			return true;
		} else {
			return false;
		}
	}
	
	public SessionInfo addUser (String room) {
		String sessionId = null;
		String token = null;
		SessionInfo sessionInfo = null;
		if(exists(room)) {
			// create a new token for an existing room
			sessionId = sessions.get(room);
		} else {
			// create a new session and a token for it
			sessionId = createNewSession(room);
			
		}
		token = generateNewToken(sessionId);
		sessionInfo = new SessionInfo(Integer.toString(API_Config.API_KEY), sessionId, token, room, "http://localhost:8080/");
		return sessionInfo;
	}
	
	private String createNewSession(String room) {
		String sessionId = null;
		try {
			sessionId = oti.createSession();
		} catch (OpenTokException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		sessions.put(room, sessionId);
		return sessionId;
	}
	
	private String generateNewToken(String sessionId) {
		String token = null;
		try {
			token = oti.generateToken(sessionId);
		} catch (OpenTokException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}
	
}
