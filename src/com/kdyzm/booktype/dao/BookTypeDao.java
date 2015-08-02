package com.kdyzm.booktype.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.kdyzm.domain.BookType;
import com.kdyzm.utils.DataSourceUtils_c3p0;

/*
 * 处理对书籍种类的增删查改操作。
 */
public class BookTypeDao {
	public List<BookType> queryAll(){
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,descc,amount from booktype";
		List<BookType>list=null;
		try {
			list=run.query(sql, new BeanListHandler<BookType>(BookType.class));
		} catch (SQLException e) {
			throw new RuntimeException("查询所有图书分类信息失败！");
		}
		return list;
	}
	//根据图书类型的id得到该id所属的类别名称
	public String getBookTypeByBookTypeid(String id) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select * from booktype where id=?";
		BookType bt=null;
		try {
			bt=run.query(sql, new BeanHandler<BookType>(BookType.class),id);
		} catch (SQLException e) {
			throw new RuntimeException("没有找到指定的图书类型名称！");
		}
		return bt.getName();
	}
	public List<BookType> getBookTypeByBookid(String bookid) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select booktype.* from book inner join bt on book.id=bt.bookid"
				+ " inner join booktype on bt.booktypeid=booktype.id where book.id=?";
		List<BookType>list=null;
		try {
			list=run.query(sql, new BeanListHandler<BookType>(BookType.class),bookid);
		} catch (SQLException e) {
			throw new RuntimeException("根据图书id查找图书类型发生未知的sql异常！");
		}
		return list;
	}
}
