package com.kdyzm.booktype.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.kdyzm.domain.BookType;
import com.kdyzm.utils.DataSourceUtils_c3p0;

/*
 * ������鼮�������ɾ��Ĳ�����
 */
public class BookTypeDao {
	public List<BookType> queryAll(){
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,descc,amount from booktype";
		List<BookType>list=null;
		try {
			list=run.query(sql, new BeanListHandler<BookType>(BookType.class));
		} catch (SQLException e) {
			throw new RuntimeException("��ѯ����ͼ�������Ϣʧ�ܣ�");
		}
		return list;
	}
	//����ͼ�����͵�id�õ���id�������������
	public String getBookTypeByBookTypeid(String id) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select * from booktype where id=?";
		BookType bt=null;
		try {
			bt=run.query(sql, new BeanHandler<BookType>(BookType.class),id);
		} catch (SQLException e) {
			throw new RuntimeException("û���ҵ�ָ����ͼ���������ƣ�");
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
			throw new RuntimeException("����ͼ��id����ͼ�����ͷ���δ֪��sql�쳣��");
		}
		return list;
	}
}
