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
	 * 验证登录,重点方法，关键是购物车的同步问题。
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
		//验证失败
		if(user==null)
		{
			request.getSession().setAttribute("msg", "用户名或者密码错误！");
			return "logoin";
		}
		else
		{
			HttpSession currentSession=request.getSession();
			currentSession.setAttribute("user", user);
			//TODO 这里是实现购物车跨浏览器访问的关键               
			UserSessionMap userSessionMap=UserSessionMap.getInstance();
			SessionUtils sessionUtils=SessionUtils.getInstance();
			String sessionid=userSessionMap.getSessionId(user.getId());
			HttpSession userSession=sessionUtils.getSession(sessionid);
			if(sessionid==null)//在用户和session的绑定map中没找到说明服务器没有保存用户的信息，需要全部保存。
			{
				userSessionMap.add(user.getId(), currentSession.getId());
				userSession=sessionUtils.getSession(currentSession.getId());
//				currentSession.setMaxInactiveInterval(-1);//设置session永远不过期//默认是30分钟。
			}
			else	//如果之前登陆过，就需要合并购物车并保存当前购物车的状态。
			{
				Map<String,Car>currentcar=(Map<String, Car>) currentSession.getAttribute("carlist");
				Map<String,Car>lastListCar= (Map<String, Car>) userSession.getAttribute("carlist");
				userSession.setAttribute("backupCar", currentcar);//为退出之后能够显示出之前的购物车状态做准备。
				if(currentcar==null)//登陆了但是还没有购物,直接切换session
				{
					if(lastListCar!=null)
					currentSession.setAttribute("carlist", lastListCar);
				}
				else	//如果当前购物车不为空，则合并购物车
				{
					Map<String,Car>backupCar=new HashMap<String,Car>();
					backupCar.putAll(currentcar);
					if(lastListCar==null)//如果之前没有购买过，但是现在买了。则直接覆盖购物车即可。
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
					else//如果之前购物车不为空而且当前购物车也不为空
					{
						for(String bookid:currentcar.keySet())
						{
							lastListCar.put(bookid, currentcar.get(bookid));
						}
						currentSession.setAttribute("carlist", lastListCar);
					}
				}
			}
			//在这里要恢复现场，怎样保存登陆之前的页面？
			//
			String requestpath=(String) currentSession.getAttribute("requesturi");
			if(requestpath!=null)//如果是被拦截之后的登陆，则跳转到拦截之前的页面
			{
				request.getSession().removeAttribute("requesturi");//使用完成之后马上删除掉。
				response.sendRedirect("http://localhost:8080"+requestpath);
			}
			else//如果是直接登陆的，则跳转到首页
			{
				response.sendRedirect(request.getContextPath()+"/user/flushparent.jsp");
			}
		}
		return null;
	}
	/*
	 * 用户注册的请求的方法
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
		request.getSession().setAttribute("msg", "您已经注册成功，请登录！");
		return "302:logoin";
	}
	/*
	 * 对比验证码是否是相同的方法，Ajax会使用异步请求的方式来请求该方法。
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
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//如果对比不一致，则报告404
		}
		return null;
	}
	/*
	 * 验证用户名是否重复
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
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//如果用户名已经存在，则报告404
		}
		return null;
	}
	/*
	 * 验证邮箱是否有重复
	 */
	public String checkEmailHasExists(HttpServletRequest request,HttpServletResponse response)
	{
		String email=request.getParameter("email");
		User user=userService.checkEmailHasExists(email);
		if(user!=null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//如果用户名已经存在，则报告404
		}
		return null;
	}
	//用户退出执行的方法
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
			System.out.println("删除cookie:"+cookie);
			response.addCookie(cookie);
		}
		return "302:index";
	}
}
