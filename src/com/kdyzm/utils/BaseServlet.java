package com.kdyzm.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//声明一个抽象类作为基类Servlet
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String commondType=request.getParameter("cmd");
		if("".equals(commondType)||commondType==null)
		{
			commondType="defaultMethod";
		}
			try {
				Method method=this.getClass().getMethod(commondType, HttpServletRequest.class,HttpServletResponse.class);
				Object result=method.invoke(this, request,response);
				if(result!=null)//表名进行了转发或者重定向。
				{
					boolean redirect=false;//默认假设是转发
					String key=result.toString();
					if(key.startsWith("302"))//表明是重定向
					{
						key=key.split(":")[1];
						redirect=true;
					}
					String page=PageUtils.getPage(key);
//					System.out.println(page);
					if(page==null)
					{
						throw new RuntimeException("没有配置key值！："+key);
					}
					else
					{
						if(redirect)
						{
							//进行重定向
							response.sendRedirect(request.getContextPath()+page);
						}
						else
						{
							//进行转发
							request.getRequestDispatcher(page).forward(request, response);
						}
					}
				}
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	//默认方法必须被子类实现
	public abstract String defaultMethod(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException ;
}
