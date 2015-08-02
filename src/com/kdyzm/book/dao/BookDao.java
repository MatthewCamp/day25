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
 * 处理对书籍信息的增删查改。
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
			throw new RuntimeException("查找所有图书失败！");
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
			throw new RuntimeException("查找书的总种类数失败！");
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
			throw new RuntimeException("查找图书失败！");
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
			throw new RuntimeException("查询所有图书分类信息失败！");
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
			throw new RuntimeException("根据id查找图书失败！");
		}
		return book;
	}
	//获取五本推荐的图书
	public List<Book> getBookLimitFive() {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select id,name,price,auth,img,rebate,amount,publisher,publishdate,pages,size,printtimes,"
				+ "versions,brief,content,onlinetime from book limit 5";
		List<Book>list=null;
		try {
			list=run.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException("查找推荐的5本图书失败！");
		}
		return list;
	}

	//该方法开启了事务，所以不能使用DataSource的方式，应该使用getConnettion的方式控制
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
			throw new RuntimeException("插入新书数据失败！");
		}
		sql="insert into bt values(?,?)";
		for(String booktypeid:booktypes)
		{
			try {
				run.update(DataSourceUtils_c3p0.getConnection(),sql,booktypeid,book.getId());
			} catch (SQLException e) {
				throw new RuntimeException("插入新书类型到book-booktype表失败！");
			}
		}
		return book;
	}
	//根据id删除指定的图书和相关信息。
	public Book deleteBookById(String bookid) {
		QueryRunner run=new QueryRunner();
		String sql="UPDATE book INNER JOIN bt ON book.id=bt.bookid "+
				"   INNER JOIN booktype ON booktype.id=bt.booktypeid "+
				"  SET booktype.amount=booktype.amount-1 "+
				" WHERE book.id=?";
		try {
			run.update(DataSourceUtils_c3p0.getConnection(),sql, bookid);
		} catch (SQLException e) {
			throw new RuntimeException("booktype表count字段操作失败！");
		}
		
		sql="delete from book where id=?";
		Book book=null;
		try {
			run.update(DataSourceUtils_c3p0.getConnection(),sql,bookid);
			book=new Book();
		} catch (SQLException e) {
			throw new RuntimeException("根据指定图书id删除指定图书失败！");
		}
		return book;
	}
	//根据id查找制定图片。
	public String getImgByBookid(String id) {
		QueryRunner run=new QueryRunner(DataSourceUtils_c3p0.getDataSource());
		String sql="select * from book where id=?";
		Book book=null;
		try {
			book=run.query(sql, new BeanHandler<Book>(Book.class),id);
		} catch (SQLException e) {
			throw new RuntimeException("根据bookid查找制定图片失败！");
		}
		if(book!=null)
		return book.getImg();
		return null;
	}
	//更新图书信息的方法
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
			throw new RuntimeException("更新图书数据失败！");
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
