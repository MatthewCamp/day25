package com.kdyzm.admin.service;

import java.util.List;

import com.kdyzm.admin.dao.AdminDao;
import com.kdyzm.domain.Admin;
import com.kdyzm.domain.Menu;

public class AdminService {
	
	private AdminDao adminDao=new AdminDao();
	/*
	 * ��֤�û���½�ķ���
	 */
	public Admin checkAdminLogin(Admin admin) {
		return adminDao.checkAdminLogin(admin);
	}
	//���ݹ���Ա����������uri�ж��Ƿ���Ȩ�޷��ʸ���Դ��
	public boolean checkAdminHasAuth(Admin admin, String uri) {
		
		return adminDao.checkAdminHasAuth(admin,uri);
	}
	//���ݲ�ͬ����Ա�ĵȼ��õ���ǰ����Ա�ܹ����ʵĲ˵�Ŀ¼��
	public List<Menu> getMenusByAdmin(Admin admin) {
		return adminDao.getMenusByAdmin(admin);
	}

}
