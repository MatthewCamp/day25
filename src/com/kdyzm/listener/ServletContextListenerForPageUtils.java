package com.kdyzm.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.kdyzm.utils.PageUtils;

public class ServletContextListenerForPageUtils implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	//��ʼ����ʱ���ȡ�û���������Ϣ������PageUtils�е�Properties���г�ʼ������
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext sc=arg0.getServletContext();
		String fileName=sc.getInitParameter("pageLocationConfig");
		if(fileName==null)
			fileName="views.properties";
		if(fileName.startsWith("/"))
		{
			String path=sc.getRealPath(fileName);
			try {
				InputStream is=new FileInputStream(path);
				PageUtils.setProperties(is);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else
		{
			InputStream is=ServletContextListenerForPageUtils.class.getClassLoader().getResourceAsStream(fileName);
			PageUtils.setProperties(is);
		}
	}

}
