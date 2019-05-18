package com.start.web;

import javax.servlet.http.HttpSession;

import com.start.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessiondUser";
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessiondUser = session.getAttribute(USER_SESSION_KEY);
		if(sessiondUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if(isLoginUser(session)) {
			return null;
		}
		
		return (User) session.getAttribute(USER_SESSION_KEY);
	}
}

