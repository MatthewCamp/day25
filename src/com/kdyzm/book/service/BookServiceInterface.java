package com.kdyzm.book.service;

import java.util.List;

import com.kdyzm.domain.Book;
import com.kdyzm.myannotation.Transactionflag;
/*
 * �û���������Ľӿڶ�����Ӧ�ÿ�������ķ�����
 */
public interface BookServiceInterface {
	@Transactionflag//��ע�����ڱ�ʶ�÷������Ὺ������
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