package com.kdyzm.user.service;

import com.kdyzm.domain.User;
import com.kdyzm.user.dao.UserDao;
import com.kdyzm.utils.UUIDUtils;

public class UserService {
	private UserDao userDao=new UserDao();
	public User register(User user) {
		user.setId(UUIDUtils.getUUIDString());
		return userDao.register(user);
	}
	/*
	 * ��֤�û����Ƿ��Ѿ�����
	 */
	public User checkUserHasExists(String username) {
		return userDao.checkUserHasExists(username);
	}
	/*
	 * �����û�����֤�û��Ƿ��½�ɹ��ķ���
	 */
	public User checkUserByName(User user) {
		return userDao.checkUserByName(user);
	}
	/*
	 * ����Email��֤�û��Ƿ��½�ɹ��ķ���
	 */
	public User checkUserByEmail(User user) {
		return userDao.checkUserByEmail(user);
	}
	/*
	 * �����Ƿ�����ظ����û���
	 */
	public User checkEmailHasExists(String email) {
		return userDao.checkEmailHasExists(email);
	}
}
