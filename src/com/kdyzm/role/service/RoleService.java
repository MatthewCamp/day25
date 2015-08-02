package com.kdyzm.role.service;

import java.util.List;

import com.kdyzm.domain.Role;
import com.kdyzm.role.dao.RoleDao;

public class RoleService {
	RoleDao roleDao=new RoleDao();
	public List<Role> getRoleList() {
		return roleDao.getRoleList();
	}
	public Role updateRoleInfo(Role role) {
		return roleDao.updateRoleInfo(role);
	}
	public Role addNewRole(Role role) {
		return roleDao.addNewRole(role);
	}
	public boolean deleteRoleById(String id) {
		return roleDao.deleteRoleById(id);
	}

}
