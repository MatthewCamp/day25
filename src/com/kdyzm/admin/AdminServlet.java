package com.kdyzm.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kdyzm.admin.service.AdminService;
import com.kdyzm.domain.Admin;
import com.kdyzm.domain.Menu;
/*
 * ��Ӧ��Admin������
 */
import com.kdyzm.utils.BaseServlet;

public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 4840062418967658553L;
	private AdminService adminService=new AdminService();
	//ʹ��Ĭ�Ϸ������
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return "302:showManageIndex";
	}
	public String showMenus(HttpServletRequest request,HttpServletResponse response)
	{
		//������ݹ���Ա�Ĳ�ͬ������ʾ��ͬ�Ĺ���Ŀ¼,��ִ�и÷����ͱ�ʾ����Աһ���Ѿ���½�ˡ�
		Admin admin=(Admin) request.getSession().getAttribute("admin");
		List<Menu>list=adminService.getMenusByAdmin(admin);
		request.setAttribute("menus", list);
		return "showMenus";
	}
	//��֤��¼�ķ���
	public String login(HttpServletRequest request,HttpServletResponse response)
	{
		Admin admin=new Admin();
		String name=request.getParameter("name");
		try {
			name=new String(name.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String password=request.getParameter("password");
		admin.setName(name);
		admin.setPassword(password);
		admin=adminService.checkAdminLogin(admin);
		if(admin==null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		//������Ա��¼��Ϣ���浽session��
		HttpSession session=request.getSession();
		session.setAttribute("admin", admin);
		return null;
	}
	//��֤��֤���Ƿ�ƥ��ķ���
	public String checkCheckCode(HttpServletRequest request,HttpServletResponse response)
	{
		String checkcode=request.getParameter("checkcode");
		String ck=(String) request.getSession().getAttribute("admincheckcode");
		if(!checkcode.equals(ck))
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return null;
	}
}
