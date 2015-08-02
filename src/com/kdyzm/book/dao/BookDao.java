package com.kdyzm.book.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.kdyzm.domain.Book;
import com.kdyzm.utils.DataSourceUtils_c3p0;

/*
 * ������鼮��Ϣ����ɾ��ġ�
 */
public class BookDao {
	public List<Book> getAllBooks() {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,price,auth,img,rebate,amount,publisher,publishdate,pages,size,printtimes,"
				+ "versions,brief,content,onlinetime from book";
		List<Book>list=null;
		try {
			list=run.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException("��������ͼ��ʧ�ܣ�");
		}
		return list;
	}

	public int getAllBooksColumns(String id) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql=null;
		if(id==null)
		sql="select count(1) from book";
		else
		{
			sql="select sum(amount) from booktype where id=?";
		}
		int columns=0;
		try {
			Object obj;
			if(id==null)
			obj=run.query(sql, new ScalarHandler<Object>());
			else
				obj=run.query(sql, new ScalarHandler<Object>(),id);
			columns=Integer.parseInt(obj.toString());
		} catch (SQLException e) {
			throw new RuntimeException("���������������ʧ�ܣ�");
		}
		return columns;
	}

	public List<Book> getBookBySplitPage(int startIndex, int pageSize, String id) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql=null;
		if(id==null)
			sql="select id,name,price,auth,img,rebate,amount,publisher,publishdate,pages,size,printtimes,"
				+ "versions,brief,content,onlinetime from book limit ?,?";
		else
		{
			sql="select book.id id,book.name name,price,auth,img,rebate,book.amount amount,publisher,publishdate,pages,size,printtimes,"
					+ "versions,brief,content,onlinetime from book inner join bt "
					+ "on book.id=bt.bookid inner join booktype "
					+ "on booktype.id=bt.booktypeid where booktypeid=? limit ?,?";
		}
		List<Book>list=null;
		try {
			if(id==null)
				list=run.query(sql, new BeanListHandler<Book>(Book.class),startIndex,pageSize);
			else
			{
				list=run.query(sql, new BeanListHandler<Book>(Book.class),id,startIndex,pageSize);
			}
		} catch (SQLException e) {
			throw new RuntimeException("����ͼ��ʧ�ܣ�");
		}
		return list;
	}

	public int getAllBooksAmount() {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="SELECT count(1) FROM book";
		int amount=0;
		try {
			Object obj=run.query(sql, new ScalarHandler<Object>());
			amount=Integer.parseInt(obj.toString());
		} catch (SQLException e) {
			throw new RuntimeException("��ѯ����ͼ�������Ϣʧ�ܣ�");
		}
		return amount;
	}

	public Book getOneBookById(String bookid) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,price,auth,img,rebate,amount,publisher,publishdate,pages,size,printtimes,"
				+ "versions,brief,content,onlinetime from book where id=?";
		Book book=null;
		try {
			book=run.query(sql, new BeanHandler<Book>(Book.class),bookid);
		} catch (SQLException e) {
			throw new RuntimeException("����id����ͼ��ʧ�ܣ�");
		}
		return book;
	}
	//��ȡ�屾�Ƽ���ͼ��
	public List<Book> getBookLimitFive() {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,price,auth,img,rebate,amount,publisher,publishdate,pages,size,printtimes,"
				+ "versions,brief,content,onlinetime from book limit 5";
		List<Book>list=null;
		try {
			list=run.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException("�����Ƽ���5��ͼ��ʧ�ܣ�");
		}
		return list;
	}

	//�÷����������������Բ���ʹ��DataSource�ķ�ʽ��Ӧ��ʹ��getConnettion�ķ�ʽ����
	public Book addNewBook(Book book, List<String> booktypes) {
		QueryRunner run=new QueryRunner();
		String sql="insert into book values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			run.update(DataSourceUtils_c3p0.getConnection(), 
					sql,
					book.getId(),
					book.getName(),
					book.getPrice(),
					book.getAuth(),
					book.getImg(),
					book.getRebate(),
					book.getAmount(),
					book.getPublisher(),
					book.getPublishdate(),
					book.getPages(),
					book.getSize(),
					book.getPrinttimes(),
					book.getVersions(),
					book.getBrief(),
					book.getContent(),
					book.getOnlinetime()
					);
		} catch (SQLException e) {
			throw new RuntimeException("������������ʧ�ܣ�");
		}
		sql="insert into bt values(?,?)";
		for(String booktypeid:booktypes)
		{
			try {
				run.update(DataSourceUtils_c3p0.getConnection(),sql,booktypeid,book.getId());
			} catch (SQLException e) {
				throw new RuntimeException("�����������͵�book-booktype��ʧ�ܣ�");
			}
		}
		return book;
	}
	//����idɾ��ָ����ͼ��������Ϣ��
	public Book deleteBookById(String bookid) {
		QueryRunner run=new QueryRunner();
		String sql="UPDATE book INNER JOIN bt ON book.id=bt.bookid "+
				"   INNER JOIN booktype ON booktype.id=bt.booktypeid "+
				"  SET booktype.amount=booktype.amount-1 "+
				" WHERE book.id=?";
		try {
			run.update(DataSourceUtils_c3p0.getConnection(),sql, bookid);
		} catch (SQLException e) {
			throw new RuntimeException("booktype��count�ֶβ���ʧ�ܣ�");
		}
		
		sql="delete from book where id=?";
		Book book=null;
		try {
			run.update(DataSourceUtils_c3p0.getConnection(),sql,bookid);
			book=new Book();
		} catch (SQLException e) {
			throw new RuntimeException("����ָ��ͼ��idɾ��ָ��ͼ��ʧ�ܣ�");
		}
		return book;
	}
	//����id�����ƶ�ͼƬ��
	public String getImgByBookid(String id) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select * from book where id=?";
		Book book=null;
		try {
			book=run.query(sql, new BeanHandler<Book>(Book.class),id);
		} catch (SQLException e) {
			throw new RuntimeException("����bookid�����ƶ�ͼƬʧ�ܣ�");
		}
		if(book!=null)
		return book.getImg();
		return null;
	}
	//����ͼ����Ϣ�ķ���
	public Book updateOneBook(Book book, List<String> booktypes) {
		QueryRunner run=new QueryRunner();
		String sql="update book set name=?,price=?,auth=?,img=?,rebate=?,amount=?,publisher=?,publishdate=?,pages=?,size=?,printtimes=?,versions=?,brief=?,content=?,onlinetime=? where id=?";
		try {
			run.update(DataSourceUtils_c3p0.getConnection(), 
					sql,
					book.getName(),
					book.getPrice(),
					book.getAuth(),
					book.getImg(),
					book.getRebate(),
					book.getAmount(),
					book.getPublisher(),
					book.getPublishdate(),
					book.getPages(),
					book.getSize(),
					book.getPrinttimes(),
					book.getVersions(),
					book.getBrief(),
					book.getContent(),
					book.getOnlinetime(),
					book.getId()
					);
		} catch (SQLException e) {
			throw new RuntimeException("����ͼ������ʧ�ܣ�");
		}
		sql="delete from bt where bookid=?";
		try {
			run.update(DataSourceUtils_c3p0.getConnection(),sql,book.getId());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		sql="insert into bt values(?,?)";
		for(String booktypeid:booktypes)
		{
			try {
				run.update(DataSourceUtils_c3p0.getConnection(),sql,booktypeid,book.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return book;
	}
}
