package com.kdyzm.book.service;

import java.util.List;

import com.kdyzm.book.dao.BookDao;
import com.kdyzm.domain.Book;
import com.kdyzm.utils.UUIDUtils;

/*
 * 对书籍信息进行增删查改的服务层。
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
	//获取五本推荐的图书
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
	//通过id获取图片url的方法，防止用户修改的时候没有上传图片。
	public String getImgByBookid(String id) {
		return bookDao.getImgByBookid(id);
	}
	//更新图书信息的方法
	@Override
	public Book updateOneBook(Book book, List<String> booktypelist) {
		return bookDao.updateOneBook(book,booktypelist);
	}
	
}
