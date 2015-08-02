package com.kdyzm.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/*
 * 单例类，实现对session的增删查改
 */
public class SessionUtils {
	private static  SessionUtils instance;
	//存放的是userid-sesion键值对。
	private HashMap<String,HttpSession>map;
	private SessionUtils(){
		map=new HashMap<String,HttpSession>();
	}
	//获取单例的方法
	public static SessionUtils getInstance()
	{
		if(instance==null)
		{
			synchronized (SessionUtils.class) {
				if(instance==null)
				{
					instance=new SessionUtils();
				}
				return instance;
			}
		}
		return instance;
	}
	//添加session的方法。
	public void addSession(HttpSession session)
	{
		synchronized(SessionUtils.class)
		{
			map.put(session.getId(), session);
		}
	}
	//移出session的方法
	public void removeSession(HttpSession session)
	{
		synchronized(SessionUtils.class)
		{
			map.remove(session.getId());
		}
	}
	//根据sessionid获取session对象的方法。
	public HttpSession getSession(String sessionId)
	{
		synchronized(SessionUtils.class)
		{
			return map.get(sessionId);
		}
	}
}
