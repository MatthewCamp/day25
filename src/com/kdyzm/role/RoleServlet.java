package com.kdyzm.role;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/*
 * ��Servlet�����Role�����������
 */
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdyzm.domain.Role;
import com.kdyzm.role.service.RoleService;
import com.kdyzm.utils.BaseServlet;

public class RoleServlet extends BaseServlet {
	private static final long serialVersionUID = -5023937512057470252L;
	RoleService roleService=new RoleService();
	//Ĭ�Ϸ�����ʾ����role����
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Role>list=roleService.getRoleList();
		request.setAttribute("roles", list);
		return "rolesManage";
	}
	//�޸Ľ�ɫ�ķ���
	public String modifyRole(HttpServletRequest request,HttpServletResponse response)
	{
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String descc=request.getParameter("descc");
		try {
			name=new String(name.getBytes("iso-8859-1"),"utf-8");
			descc=new String(descc.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Role role=new Role();
		role.setId(id);
		role.setName(name);
		role.setDescc(descc);
		role=roleService.updateRoleInfo(role);
		if(role==null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return null;
	}
	public String addNewRole(HttpServletRequest request,HttpServletResponse response)
	{
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String descc=request.getParameter("descc");
		try {
			name=new String(name.getBytes("iso-8859-1"),"utf-8");
			descc=new String(descc.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Role role=new Role();
		role.setId(id);
		role.setName(name);
		role.setDescc(descc);
		role=roleService.addNewRole(role);
		if(role==null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return null;
	}
	
	/*
	 * ɾ����ɫ�ķ���
	 */
	public String deleteRole(HttpServletRequest request,HttpServletResponse response)
	{
		String id=request.getParameter("id");
		boolean result=roleService.deleteRoleById(id);
		if(result==false)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return null;
	}
}
