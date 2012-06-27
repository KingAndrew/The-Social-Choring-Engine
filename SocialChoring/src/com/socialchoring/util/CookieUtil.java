package com.socialchoring.util;

import javax.servlet.http.Cookie;

public class CookieUtil {

	public static Cookie getCookie(Cookie[] cookies, String cookieName, String defaultValue) {
		if (cookies != null) {

			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName()))
					return (cookie);
			}
		}
		return new Cookie(cookieName, defaultValue);
	}

	public static String getCookieValue(Cookie[] cookies, String cookieName) {
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return (cookie.getValue());
		}
		return null;
	}
}
