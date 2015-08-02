package com.kdyzm.listener;
/*
 * session����������session�Ĵ��������١�
 * */
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/*
 * ����session�Ĵ���������
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
		session.setAttribute("carlist", map);//һ�������Ự�����������ﳵ��
		System.out.println("����session��������"+session.getId());
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session=sessionEvent.getSession();
		sessionUtil.removeSession(session);
		System.out.println("��sesion�����٣�"+session.getId());
	}
}
