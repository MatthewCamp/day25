package com.kdyzm.book.service;

import java.util.List;

import com.kdyzm.book.dao.BookDao;
import com.kdyzm.domain.Book;
import com.kdyzm.utils.UUIDUtils;

/*
 * ���鼮��Ϣ������ɾ��ĵķ���㡣
 */
public class BookService implements BookServiceInterface {
	private BookDao bookDao=new BookDao();
	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}
	public int getAllBooksColumns(String id) {
		return bookDao.getAllBooksColumns(id);
	}
	public List<Book> getBookBySplitPage(int startIndex, int pageSize, String id) {
		return bookDao.getBookBySplitPage(startIndex,pageSize,id);
	}
	public int getAllBooksAmount() {
		return bookDao.getAllBooksAmount();
	}
	public Book getOneBookById(String bookid) {
		return bookDao.getOneBookById(bookid);
	}
	//��ȡ�屾�Ƽ���ͼ��
	public List<Book> getBookLimitFive() {
		return bookDao.getBookLimitFive();
	}
	/* (non-Javadoc)
	 * @see com.kdyzm.book.service.BookServiceInterface#addNewBook(com.kdyzm.domain.Book)
	 */
	@Override
	public Book addNewBook(Book book,List<String>booktypes) {
		book.setId(UUIDUtils.getUUIDString());
		return bookDao.addNewBook(book,booktypes);
	}
	public Book deleteBookById(String bookid) {
		return bookDao.deleteBookById(bookid);
	}
	@Override
	public int getAllBooksColumns(Object object) {
		return 0;
	}
	//ͨ��id��ȡͼƬurl�ķ�������ֹ�û��޸ĵ�ʱ��û���ϴ�ͼƬ��
	public String getImgByBookid(String id) {
		return bookDao.getImgByBookid(id);
	}
	//����ͼ����Ϣ�ķ���
	@Override
	public Book updateOneBook(Book book, List<String> booktypelist) {
		return bookDao.updateOneBook(book,booktypelist);
	}
	
}
