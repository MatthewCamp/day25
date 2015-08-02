package com.kdyzm.role.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.kdyzm.domain.Role;
import com.kdyzm.utils.DataSourceUtils_c3p0;

public class RoleDao {
	/*
	 * ��ȡ���е�Role�б�
	 */
	public List<Role> getRoleList() {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select * from role";
		List<Role>list=null;
		try {
			list=run.query(sql, new BeanListHandler<Role>(Role.class));
		} catch (SQLException e) {
			throw new RuntimeException("�������н�ɫʧ�ܣ�");
		}
		return list;
	}
	/*
	 * ���½�ɫ��Ϣ
	 * */
	public Role updateRoleInfo(Role role) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="update role set name=?,descc=? where id=?";
		int result=0;
		try {
			result=run.update(sql, role.getName(),role.getDescc(),role.getId());
		} catch (SQLException e) {
			throw new RuntimeException("����role��Ϣʱ������δ֪��sql�쳣��");
		}
		if(result==0)
			return null;
		else
			return role;
	}
	public Role addNewRole(Role role) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="insert into role(id,name,descc) values(?,?,?)";
		int result=0;
		try {
			result=run.update(sql, role.getId(),role.getName(),role.getDescc());
		} catch (SQLException e) {
			throw new RuntimeException("�����µ�role��Ϣʱ������δ֪��sql�쳣��");
		}
		if(result==0)
			return null;
		else
			return role;
	}
	//����idɾ��ָ����ɫ�ķ���
	public boolean deleteRoleById(String id) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="delete from role where id=?";
		int result=0;
		try {
			result=run.update(sql, id);
		} catch (SQLException e) {
			throw new RuntimeException("ɾ��role��Ϣʱ������δ֪��sql�쳣��");
		}
		if(result==0)
			return false;
		else
			return true;
	}
}
