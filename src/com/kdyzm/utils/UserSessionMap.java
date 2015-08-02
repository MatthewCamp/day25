package com.kdyzm.utils;

import java.util.HashMap;

/*
 * 维护userid和session的对应关系
 */
public class UserSessionMap {
	private static UserSessionMap instance;
	private HashMap<String,String>map;
	private UserSessionMap()
	{
		map=new HashMap<String,String>();
	}
	public static UserSessionMap getInstance()
	{
		if(instance==null)
		{
			synchronized (UserSessionMap.class) {
				if(instance==null)
					instance=new UserSessionMap();
				return instance;
			}
		}
		return instance;
	}
	public void add(String userid,String sessionid)
	{
		synchronized (UserSessionMap.class) {
			map.put(userid, sessionid);
		}
	}
	public void remove(String userid)
	{
		synchronized (UserSessionMap.class) {
			map.remove(userid);
		}
	}
	public String getSessionId(String userid)
	{
		synchronized (UserSessionMap.class) {
			return map.get(userid);
		}
	}
}
