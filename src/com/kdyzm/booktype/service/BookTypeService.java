package com.kdyzm.booktype.service;

import java.util.List;

import com.kdyzm.booktype.dao.BookTypeDao;
import com.kdyzm.domain.BookType;
/*
 * 处理对书籍种类的增删查改操作的服务层。
 */
public class BookTypeService {
	private BookTypeDao bookDao=new BookTypeDao();
	public List<BookType> queryAll() {
		return bookDao.queryAll();
	}
	public String getBookTypeByBookTypeid(String id) {
		return bookDao.getBookTypeByBookTypeid(id);
	}
	public List<BookType> getBookTypeByBookid(String bookid) {
		return bookDao.getBookTypeByBookid(bookid);
	}
}
