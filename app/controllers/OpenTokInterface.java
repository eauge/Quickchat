package controllers;

import com.opentok.api.API_Config;
import com.opentok.api.OpenTokSDK;
import com.opentok.api.constants.SessionProperties;
import com.opentok.exception.OpenTokException;

public class OpenTokInterface {

	private OpenTokSDK sdk = null; 
	private String location = null;
	private SessionProperties properties = null;
	
	public OpenTokInterface () {
		InitOpenTokInterface(null, null);
		
	}
	
	public OpenTokInterface (SessionProperties properties) {
		InitOpenTokInterface(null, properties);	
	}
	
	public OpenTokInterface (String location) {
		InitOpenTokInterface(location, null);
	}
	
	private void InitOpenTokInterface (String location, SessionProperties properties) {
		this.location = location;
		this.properties = properties;
		this.sdk = new OpenTokSDK (API_Config.API_KEY, API_Config.API_SECRET);
	}
	
	public String createSession () throws OpenTokException {
		String session = sdk.create_session(this.location, this.properties).getSessionId();
		return session;
	}
	
	public String generateToken (String sessionId) throws OpenTokException {
		String token = sdk.generate_token(sessionId);
		return token;
	}
	
	public String generateToken (String sessionId, String role) throws OpenTokException {
		String token = sdk.generate_token(sessionId, role);
		return token;
	}
}
