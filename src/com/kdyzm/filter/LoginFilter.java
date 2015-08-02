package com.kdyzm.filter;
/*
 * 用户登录过滤器，如果没有登录还想要访问安全资源，则重定向到登录界面
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kdyzm.domain.User;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		//使用该方法获得的的是带有项目根路径的路径名如：/bookstore/secure/orderServlet
		if(user==null)
		{
			request.getSession().setAttribute("requesturi", request.getRequestURI());//这里一定要放到这里
			response.sendRedirect(request.getContextPath()+"/user/login.jsp");
		}
		else
		{
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
