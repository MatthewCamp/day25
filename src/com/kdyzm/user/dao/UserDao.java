package com.kdyzm.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.kdyzm.domain.User;
import com.kdyzm.utils.DataSourceUtils_c3p0;

public class UserDao {
	/*
	 * ע���û��ķ��������浽���ݿ⡣
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
	 * ��֤�û����Ƿ��Ѿ�����
	 */
	public User checkUserHasExists(String username) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where name=?";
		User user=null;
		try {
			user=run.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			throw new RuntimeException("�����ظ����û���ʧ�ܣ�");
		}
		return user;
	}
	/*
	 * ��֤�û�����֤�û��Ƿ��Ѿ���½�ɹ��ķ���
	 */
	public User checkUserByName(User user) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where name=? and password=?";
		User result=null;
		try {
			result=run.query(sql, new BeanHandler<User>(User.class),user.getName(),user.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException("�����û�����֤�û�ʧ�ܣ�");
		}
		return result;
	}
	/*
	 * ����email��֤�û��Ƿ�ɹ���½�ķ���
	 */
	public User checkUserByEmail(User user) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where email=? and password=?";
		User result=null;
		try {
			result=run.query(sql, new BeanHandler<User>(User.class),user.getEmail(),user.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException("�����û�������֤�û�ʧ�ܣ�");
		}
		return result;
	}
	/*
	 * ��֤�û������Ƿ����
	 */
	public User checkEmailHasExists(String email) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,email from user where email=?";
		User user=null;
		try {
			user=run.query(sql, new BeanHandler<User>(User.class),email);
		} catch (SQLException e) {
			throw new RuntimeException("�����ظ����û���ʧ�ܣ�");
		}
		return user;
	}

}
