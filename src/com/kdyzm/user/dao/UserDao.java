package com.kdyzm.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.kdyzm.domain.User;
import com.kdyzm.utils.DataSourceUtils_c3p0;

public class UserDao {
	/*
	 * 注册用户的方法，保存到数据库。
	 */
	public User register(User user) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="insert into user(id,name,password,email) values(?,?,?,?)";
		try {
			run.update(sql,user.getId(),user.getName(),user.getPassword(),user.getEmail());
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 验证用户名是否已经存在
	 */
	public User checkUserHasExists(String username) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where name=?";
		User user=null;
		try {
			user=run.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			throw new RuntimeException("查找重复的用户名失败！");
		}
		return user;
	}
	/*
	 * 验证用户名验证用户是否已经登陆成功的方法
	 */
	public User checkUserByName(User user) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where name=? and password=?";
		User result=null;
		try {
			result=run.query(sql, new BeanHandler<User>(User.class),user.getName(),user.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException("根据用户名验证用户失败！");
		}
		return result;
	}
	/*
	 * 根据email验证用户是否成功登陆的方法
	 */
	public User checkUserByEmail(User user) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where email=? and password=?";
		User result=null;
		try {
			result=run.query(sql, new BeanHandler<User>(User.class),user.getEmail(),user.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException("根据用户邮箱验证用户失败！");
		}
		return result;
	}
	/*
	 * 验证用户邮箱是否存在
	 */
	public User checkEmailHasExists(String email) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where email=?";
		User user=null;
		try {
			user=run.query(sql, new BeanHandler<User>(User.class),email);
		} catch (SQLException e) {
			throw new RuntimeException("查找重复的用户名失败！");
		}
		return user;
	}

}
