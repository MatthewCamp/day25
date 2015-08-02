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
		Ĭ�Ϸ�������ʾ�����档����ֻ��Ҫһ��Service����Ϳ����ˡ�
		��ΪĬ�ϵ�iframe���Զ���������
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
//		System.out.println("һ����"+booksamount+"��ͼ�飡");
		request.setAttribute("allbookstypes", list);
		request.setAttribute("booksamount", booksamount);
		return "showIndex";
	}
}
