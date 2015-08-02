package com.kdyzm.admin.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.kdyzm.domain.Admin;
import com.kdyzm.domain.Menu;
import com.kdyzm.utils.DataSourceUtils_c3p0;

public class AdminDao {
	/*
	 * ��֤�û���½�ķ���
	 */
	public Admin checkAdminLogin(Admin admin) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,sex,email from admin where name=? and password=?";
		Admin result=null;
		
		try {
			result=run.query(sql, new BeanHandler<Admin>(Admin.class),admin.getName(),admin.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException("��֤����Ա��½ʧ�ܣ�");
		}
		return result;
		
	}
	//���ݹ���Ա����������uri�ж��Ƿ���Ȩ�޷��ʸ���Դ��
	public boolean checkAdminHasAuth(Admin admin, String uri) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="SELECT COUNT(1) FROM admin INNER JOIN adminrole ON admin.id=adminrole.adminid"+
			" INNER JOIN role ON adminrole.roleid=role.id "+
			" INNER JOIN rolemenu ON rolemenu.roleid=role.id "+
			" INNER JOIN menu ON menu.id=rolemenu.menuid "+
			" WHERE admin.id=? AND menu.url=?;";
		int result=0;
		try {
			Object obj=run.query(sql, new ScalarHandler<Object>(),admin.getId(),uri);
			result=Integer.parseInt(obj.toString());
		} catch (SQLException e) {
			throw new RuntimeException("���й���ԱȨ����֤ʱ����δ֪��sql�쳣��");
		}
		System.out.println("Ȩ����֤dao����֤����ǣ�"+result);
		if(result==0)//û��Ȩ�޽��з���
			return false;
		else//��Ȩ�޽��з���
			return true;
	}
	//���ݲ�ͬ����Ա�ĵȼ��õ��ù���Ա�ܹ����ʵ�Ŀ¼��
	public List<Menu> getMenusByAdmin(Admin admin) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="SELECT DISTINCT menu.* FROM admin INNER JOIN adminrole ON admin.id=adminrole.adminid "+
			" INNER JOIN role ON adminrole.roleid=role.id "+
			" INNER JOIN rolemenu ON rolemenu.roleid=role.id "+
			" INNER JOIN menu ON menu.id=rolemenu.menuid "+
			" WHERE admin.id=? ORDER BY menu.id;";
		List<Menu>list=null;
		try {
			list=run.query(sql, new BeanListHandler<Menu>(Menu.class),admin.getId());
		} catch (SQLException e) {
			throw new RuntimeException("���ҹ���Ա����Ŀ¼�б��ʱ�����δ֪��sql�쳣��");
		}
		return list;
	}
	
}
