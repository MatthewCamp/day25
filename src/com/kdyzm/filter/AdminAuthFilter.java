package com.kdyzm.filter;
/*
 * 权限认证过滤器，对于已经登陆但是想要访问超出权限范围的访问进行过滤
 * 如果被该过滤器拦截，则一定是已经登陆了。
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

import com.kdyzm.admin.service.AdminService;
import com.kdyzm.domain.Admin;

public class AdminAuthFilter implements Filter {
	private AdminService adminService=new AdminService();
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		HttpSession session=request.getSession();
		String uri=request.getRequestURI();
		uri=uri.replaceAll(request.getContextPath(), "");//得到的结果是去掉项目名称的请求url
		Admin admin=(Admin) session.getAttribute("admin");
		//既然到了这个过滤器，则一定是已经登陆了的管理员。
		boolean result=adminService.checkAdminHasAuth(admin,uri);
		if(result==false)//如果没有权限访问则
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//告诉没有找到，事实上是没有权限，可以结合ajax
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
