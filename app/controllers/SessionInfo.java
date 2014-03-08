package controllers;

public class SessionInfo {

	public String apiKey;
	public String sessionId;
	public String token;
	public String name;
	public String location;
	
	public SessionInfo (String apiKey, String sessionId, String token, String name, String location) {
		this.apiKey = apiKey;
		this.sessionId = sessionId;
		this.token = token;
		this.name = name;
		this.location = location;
	}
}
