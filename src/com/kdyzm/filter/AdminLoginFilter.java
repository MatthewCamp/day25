package com.kdyzm.filter;
/*���˹���Ա��½����֤������*/
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

public class AdminLoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpSession session=request.getSession();
		if(session.getAttribute("admin")!=null)//����Ѿ���½�ͷ���
		{
			chain.doFilter(req, resp);
		}
		else	//���û�е�½�����ز������ض���
		{
			HttpServletResponse response=(HttpServletResponse) resp;
			response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
