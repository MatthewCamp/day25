package com.kdyzm.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/*
 * �����࣬ʵ�ֶ�session����ɾ���
 */
public class SessionUtils {
	private static  SessionUtils instance;
	//��ŵ���userid-sesion��ֵ�ԡ�
	private HashMap<String,HttpSession>map;
	private SessionUtils(){
		map=new HashMap<String,HttpSession>();
	}
	//��ȡ�����ķ���
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
	//���session�ķ�����
	public void addSession(HttpSession session)
	{
		synchronized(SessionUtils.class)
		{
			map.put(session.getId(), session);
		}
	}
	//�Ƴ�session�ķ���
	public void removeSession(HttpSession session)
	{
		synchronized(SessionUtils.class)
		{
			map.remove(session.getId());
		}
	}
	//����sessionid��ȡsession����ķ�����
	public HttpSession getSession(String sessionId)
	{
		synchronized(SessionUtils.class)
		{
			return map.get(sessionId);
		}
	}
}
