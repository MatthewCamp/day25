package com.kdyzm.filter;
/*
 * Ȩ����֤�������������Ѿ���½������Ҫ���ʳ���Ȩ�޷�Χ�ķ��ʽ��й���
 * ������ù��������أ���һ�����Ѿ���½�ˡ�
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
		uri=uri.replaceAll(request.getContextPath(), "");//�õ��Ľ����ȥ����Ŀ���Ƶ�����url
		Admin admin=(Admin) session.getAttribute("admin");
		//��Ȼ�����������������һ�����Ѿ���½�˵Ĺ���Ա��
		boolean result=adminService.checkAdminHasAuth(admin,uri);
		if(result==false)//���û��Ȩ�޷�����
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//����û���ҵ�����ʵ����û��Ȩ�ޣ����Խ��ajax
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
