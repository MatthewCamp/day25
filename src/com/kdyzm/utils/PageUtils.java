package com.kdyzm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PageUtils {
	private static Properties properties=new Properties();
	public static void setProperties(InputStream is)
	{
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//∑µªÿ“≥√Êurl
	public static String getPage(String key) {
		return properties.getProperty(key);
	}
	
}
