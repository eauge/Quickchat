package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
	private static SessionControl sc = new SessionControl();
	
	public static Result index() {
		return ok(index.render());
	}	
  
	public static Result user(String room) {
		SessionInfo sessionInfo = sc.addUser(room);
		return ok(user.render(sessionInfo));
	}
	
	public static Result loadfile(String url) {
		return ok("hello world");
	}
}