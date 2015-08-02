package com.kdyzm.listener;
/*
 * session监听器监听session的创建和销毁。
 * */
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/*
 * 监听session的创建和销毁
 */

import com.kdyzm.buy.car.Car;
import com.kdyzm.utils.SessionUtils;
public class HttpSessionListenerForUser implements HttpSessionListener {
	private SessionUtils sessionUtil=SessionUtils.getInstance();
	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		HttpSession session=sessionEvent.getSession();
		sessionUtil.addSession(session);
		Map<String,Car>map=new HashMap<String,Car>();
		session.setAttribute("carlist", map);//一旦创建会话立即创建购物车。
		System.out.println("有新session被创建："+session.getId());
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session=sessionEvent.getSession();
		sessionUtil.removeSession(session);
		System.out.println("有sesion被销毁："+session.getId());
	}
}
