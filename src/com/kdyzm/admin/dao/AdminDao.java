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
	 * 验证用户登陆的方法
	 */
	public Admin checkAdminLogin(Admin admin) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,password,sex,email from admin where name=? and password=?";
		Admin result=null;
		
		try {
			result=run.query(sql, new BeanHandler<Admin>(Admin.class),admin.getName(),admin.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException("验证管理员登陆失败！");
		}
		return result;
		
	}
	//根据管理员对象和请求的uri判断是否有权限访问该资源。
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
			throw new RuntimeException("进行管理员权限认证时发生未知的sql异常！");
		}
		System.out.println("权限认证dao的认证结果是："+result);
		if(result==0)//没有权限进行访问
			return false;
		else//有权限进行访问
			return true;
	}
	//根据不同管理员的等级得到该管理员能够访问的目录。
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
			throw new RuntimeException("查找管理员访问目录列表的时候产生未知的sql异常！");
		}
		return list;
	}
	
}
