package com.kdyzm.book.service;

import java.util.List;

import com.kdyzm.domain.Book;
import com.kdyzm.myannotation.Transactionflag;
/*
 * 用户开启事务的接口定义了应该开启事务的方法。
 */
public interface BookServiceInterface {
	@Transactionflag//该注解用于标识该方法将会开启事务
	public abstract Book addNewBook(Book book, List<String> booktypelist);

	public abstract int getAllBooksColumns(Object object);

	public abstract List<Book> getBookBySplitPage(int startIndex, int pageSize,
			String object);
	@Transactionflag
	public abstract Book deleteBookById(String bookid);

	public abstract Book getOneBookById(String bookid);
	@Transactionflag
	public abstract Book updateOneBook(Book book, List<String> booktypelist);
}