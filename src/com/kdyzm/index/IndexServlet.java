package com.kdyzm.index;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdyzm.book.service.BookService;
import com.kdyzm.booktype.service.BookTypeService;
import com.kdyzm.domain.BookType;
import com.kdyzm.utils.BaseServlet;

public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = -2140713105671335394L;
	BookTypeService bts=new BookTypeService();
	BookService bookService=new BookService();
	/*
		默认方法是显示主界面。这里只需要一个Service对象就可以了。
		因为默认的iframe会自动发起请求。
	 */
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookies[]=request.getCookies();
		if(cookies!=null)
		for(Cookie cookie:cookies)
		{
			cookie.setMaxAge(3600);
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);
		}
		List<BookType>list=bts.queryAll();
		int booksamount=bookService.getAllBooksAmount();
//		System.out.println("一共有"+booksamount+"种图书！");
		request.setAttribute("allbookstypes", list);
		request.setAttribute("booksamount", booksamount);
		return "showIndex";
	}
}
