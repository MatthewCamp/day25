package com.kdyzm.admin.service;

import java.util.List;

import com.kdyzm.admin.dao.AdminDao;
import com.kdyzm.domain.Admin;
import com.kdyzm.domain.Menu;

public class AdminService {
	
	private AdminDao adminDao=new AdminDao();
	/*
	 * 验证用户登陆的方法
	 */
	public Admin checkAdminLogin(Admin admin) {
		return adminDao.checkAdminLogin(admin);
	}
	//根据管理员对象和请求的uri判断是否有权限访问该资源。
	public boolean checkAdminHasAuth(Admin admin, String uri) {
		
		return adminDao.checkAdminHasAuth(admin,uri);
	}
	//根据不同管理员的等级得到当前管理员能够访问的菜单目录。
	public List<Menu> getMenusByAdmin(Admin admin) {
		return adminDao.getMenusByAdmin(admin);
	}

}
