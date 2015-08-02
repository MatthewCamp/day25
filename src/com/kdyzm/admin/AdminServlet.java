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
 * 响应对Admin的请求。
 */
import com.kdyzm.utils.BaseServlet;

public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 4840062418967658553L;
	private AdminService adminService=new AdminService();
	//使用默认方法获得
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return "302:showManageIndex";
	}
	public String showMenus(HttpServletRequest request,HttpServletResponse response)
	{
		//必须根据管理员的不同级别显示不同的管理目录,能执行该方法就表示管理员一定已经登陆了。
		Admin admin=(Admin) request.getSession().getAttribute("admin");
		List<Menu>list=adminService.getMenusByAdmin(admin);
		request.setAttribute("menus", list);
		return "showMenus";
	}
	//验证登录的方法
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
		//将管理员登录信息保存到session中
		HttpSession session=request.getSession();
		session.setAttribute("admin", admin);
		return null;
	}
	//验证验证码是否匹配的方法
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
