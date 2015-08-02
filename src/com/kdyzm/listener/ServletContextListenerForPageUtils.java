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

	//初始化的时候读取用户的配置信息，并对PageUtils中的Properties进行初始化操作
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
