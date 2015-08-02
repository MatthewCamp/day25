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
	 * 验证用户名是否已经存在
	 */
	public User checkUserHasExists(String username) {
		return userDao.checkUserHasExists(username);
	}
	/*
	 * 根据用户名验证用户是否登陆成功的方法
	 */
	public User checkUserByName(User user) {
		return userDao.checkUserByName(user);
	}
	/*
	 * 根据Email验证用户是否登陆成功的方法
	 */
	public User checkUserByEmail(User user) {
		return userDao.checkUserByEmail(user);
	}
	/*
	 * 查找是否存在重复的用户名
	 */
	public User checkEmailHasExists(String email) {
		return userDao.checkEmailHasExists(email);
	}
}
