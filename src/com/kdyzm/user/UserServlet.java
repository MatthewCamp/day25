package com.kdyzm.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.kdyzm.buy.car.Car;
import com.kdyzm.domain.User;
import com.kdyzm.user.service.UserService;
import com.kdyzm.utils.BaseServlet;
import com.kdyzm.utils.SessionUtils;
import com.kdyzm.utils.UserSessionMap;

public class UserServlet extends BaseServlet {
	private UserService userService=new UserService();
	private static final long serialVersionUID = 4390268739373407533L;
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return null;
	}
	/*
	 * ��֤��¼,�ص㷽�����ؼ��ǹ��ﳵ��ͬ�����⡣
	 */
	public String loginCheck(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String username=request.getParameter("username");
		username=new String(username.getBytes("iso-8859-1"),"utf-8");
		String password=request.getParameter("password");
		User user=new User();
		user.setPassword(password);
		if(username.contains("@"))
		{
			user.setEmail(username);
			user=userService.checkUserByEmail(user);
		}
		else
		{
			user.setName(username);
			user=userService.checkUserByName(user);
		}
		//��֤ʧ��
		if(user==null)
		{
			request.getSession().setAttribute("msg", "�û��������������");
			return "logoin";
		}
		else
		{
			HttpSession currentSession=request.getSession();
			currentSession.setAttribute("user", user);
			//TODO ������ʵ�ֹ��ﳵ����������ʵĹؼ�               
			UserSessionMap userSessionMap=UserSessionMap.getInstance();
			SessionUtils sessionUtils=SessionUtils.getInstance();
			String sessionid=userSessionMap.getSessionId(user.getId());
			HttpSession userSession=sessionUtils.getSession(sessionid);
			if(sessionid==null)//���û���session�İ�map��û�ҵ�˵��������û�б����û�����Ϣ����Ҫȫ�����档
			{
				userSessionMap.add(user.getId(), currentSession.getId());
				userSession=sessionUtils.getSession(currentSession.getId());
//				currentSession.setMaxInactiveInterval(-1);//����session��Զ������//Ĭ����30���ӡ�
			}
			else	//���֮ǰ��½��������Ҫ�ϲ����ﳵ�����浱ǰ���ﳵ��״̬��
			{
				Map<String,Car>currentcar=(Map<String, Car>) currentSession.getAttribute("carlist");
				Map<String,Car>lastListCar= (Map<String, Car>) userSession.getAttribute("carlist");
				userSession.setAttribute("backupCar", currentcar);//Ϊ�˳�֮���ܹ���ʾ��֮ǰ�Ĺ��ﳵ״̬��׼����
				if(currentcar==null)//��½�˵��ǻ�û�й���,ֱ���л�session
				{
					if(lastListCar!=null)
					currentSession.setAttribute("carlist", lastListCar);
				}
				else	//�����ǰ���ﳵ��Ϊ�գ���ϲ����ﳵ
				{
					Map<String,Car>backupCar=new HashMap<String,Car>();
					backupCar.putAll(currentcar);
					if(lastListCar==null)//���֮ǰû�й�����������������ˡ���ֱ�Ӹ��ǹ��ﳵ���ɡ�
					{
						if(currentcar!=null)
						{
							lastListCar=new HashMap<String,Car>();
							for(Map.Entry<String, Car>entry:currentcar.entrySet())
							{
								lastListCar.put(entry.getKey(), entry.getValue());
							}
							userSession.setAttribute("carlist", lastListCar);
						}
					}
					else//���֮ǰ���ﳵ��Ϊ�ն��ҵ�ǰ���ﳵҲ��Ϊ��
					{
						for(String bookid:currentcar.keySet())
						{
							lastListCar.put(bookid, currentcar.get(bookid));
						}
						currentSession.setAttribute("carlist", lastListCar);
					}
				}
			}
			//������Ҫ�ָ��ֳ������������½֮ǰ��ҳ�棿
			//
			String requestpath=(String) currentSession.getAttribute("requesturi");
			if(requestpath!=null)//����Ǳ�����֮��ĵ�½������ת������֮ǰ��ҳ��
			{
				request.getSession().removeAttribute("requesturi");//ʹ�����֮������ɾ������
				response.sendRedirect("http://localhost:8080"+requestpath);
			}
			else//�����ֱ�ӵ�½�ģ�����ת����ҳ
			{
				response.sendRedirect(request.getContextPath()+"/user/flushparent.jsp");
			}
		}
		return null;
	}
	/*
	 * �û�ע�������ķ���
	 */
	public String register(HttpServletRequest request,HttpServletResponse response)
	{
		User user=new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		userService.register(user);
		request.getSession().setAttribute("msg", "���Ѿ�ע��ɹ������¼��");
		return "302:logoin";
	}
	/*
	 * �Ա���֤���Ƿ�����ͬ�ķ�����Ajax��ʹ���첽����ķ�ʽ������÷�����
	 */
	public String compareCheckCode(HttpServletRequest request,HttpServletResponse response)
	{
		String checkcode=request.getParameter("checkcode");
		String checkcodex=(String) request.getSession().getAttribute("checkcode");
		/*System.out.println(checkcode);
		System.out.println(checkcodex);
		System.out.println();*/
		if(!checkcode.equals(checkcodex))
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//����ԱȲ�һ�£��򱨸�404
		}
		return null;
	}
	/*
	 * ��֤�û����Ƿ��ظ�
	 */
	public String checkUserHasExists(HttpServletRequest request,HttpServletResponse response)
	{
		String username=request.getParameter("username");
		try {
			username=new String(username.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user=userService.checkUserHasExists(username);
		if(user!=null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//����û����Ѿ����ڣ��򱨸�404
		}
		return null;
	}
	/*
	 * ��֤�����Ƿ����ظ�
	 */
	public String checkEmailHasExists(HttpServletRequest request,HttpServletResponse response)
	{
		String email=request.getParameter("email");
		User user=userService.checkEmailHasExists(email);
		if(user!=null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//����û����Ѿ����ڣ��򱨸�404
		}
		return null;
	}
	//�û��˳�ִ�еķ���
	public String exit(HttpServletRequest request,HttpServletResponse response)
	{
		request.getSession().removeAttribute("user");
		/*Map<String,Car>backupCar=(Map<String, Car>) request.getSession().getAttribute("backupCar");
		request.getSession().removeAttribute("carlist");
		if(backupCar==null)
		{
			backupCar=new HashMap<String,Car>();
			request.getSession().setAttribute("carlist", backupCar);
		}*/
		Cookie []cookies=request.getCookies();
		for(Cookie cookie:cookies)
		{
			cookie.setMaxAge(0);
			cookie.setPath(request.getContextPath());
			System.out.println("ɾ��cookie:"+cookie);
			response.addCookie(cookie);
		}
		return "302:index";
	}
}
