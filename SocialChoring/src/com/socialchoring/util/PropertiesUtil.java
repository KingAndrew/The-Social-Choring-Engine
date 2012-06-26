package com.socialchoring.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	static Properties properties;
	static {
		properties = new Properties();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			properties.load(classLoader.getResourceAsStream("res/system.properties"));
		} catch (IOException e) {
		}

	}

	public static String readProperty(String key) {
		return properties.getProperty(key);

	}

}
