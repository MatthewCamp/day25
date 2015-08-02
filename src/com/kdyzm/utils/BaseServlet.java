package com.kdyzm.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//����һ����������Ϊ����Servlet
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
				if(result!=null)//����������ת�������ض���
				{
					boolean redirect=false;//Ĭ�ϼ�����ת��
					String key=result.toString();
					if(key.startsWith("302"))//�������ض���
					{
						key=key.split(":")[1];
						redirect=true;
					}
					String page=PageUtils.getPage(key);
//					System.out.println(page);
					if(page==null)
					{
						throw new RuntimeException("û������keyֵ����"+key);
					}
					else
					{
						if(redirect)
						{
							//�����ض���
							response.sendRedirect(request.getContextPath()+page);
						}
						else
						{
							//����ת��
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
	//Ĭ�Ϸ������뱻����ʵ��
	public abstract String defaultMethod(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException ;
}
