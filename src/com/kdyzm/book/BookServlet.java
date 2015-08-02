package com.kdyzm.book;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdyzm.book.service.BookService;
import com.kdyzm.booktype.service.BookTypeService;
import com.kdyzm.domain.Book;
import com.kdyzm.utils.BaseServlet;

public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = -1675807012462630109L;
	BookService bookService=new BookService();
	BookTypeService bts=new BookTypeService();
	//显示单本书的详细信息
	public String showSingleBook(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Book book=bookService.getOneBookById(bookid);
		book.setContent("<p>"+book.getContent());
		book.setContent(book.getContent().replaceAll("\r\n", "</p><p>"));
		book.setContent(book.getContent()+"</p>");
		request.setAttribute("book", book);
		return "showOneBookDetail";
	}
	/*
	 * 默认方法是查找所有图书信息，将图书信息转发到页面上显示
	 * */
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) {
		//这里要进行代码重用才行。
		String id=request.getParameter("id");
		int lines=0;
		if(id==null||"".equals(id))
		{
			lines=bookService.getAllBooksColumns(null);
			request.getSession().setAttribute("booktype", null);
			request.getSession().setAttribute("booktypeid","");
		}
		else
		{
			lines=bookService.getAllBooksColumns(id);
			System.out.println("找到"+lines+"行数据！");
			request.setAttribute("booktypeid", id);//传回jsp页面
			String booktype=bts.getBookTypeByBookTypeid(id);
			request.getSession().setAttribute("booktype", booktype);
			request.getSession().setAttribute("booktypeid", id);
		}
//		System.out.println("查找出"+lines+"个记录！");
		int pageSize=6;//每一页显示多少本书。
		int elementLength=7;//每一个列表显示多少个元素。
		int pageCount=(lines/pageSize)+(lines%pageSize==0?0:1);//一共有多少页。
//		System.out.println("一共有"+pageCount+"页！");
		int currentPage=0;
		String page=request.getParameter("page");
		if(page==null||"".equals(page))
		{
//			System.out.println("空page参数");
			currentPage=1;
		}
		else
		{
			currentPage=Integer.parseInt(page);
//			System.out.println("解析出："+currentPage);
			if(currentPage<=0)
			{
				currentPage=1;
			}
			if(currentPage>pageCount)
			{
				currentPage=pageCount;
			}
		}
		int start=0;
		int end=0;
		if(currentPage>=1&&currentPage<=(elementLength/2+1))
		{
			start=1;
			if(pageCount<elementLength)
				end=pageCount;
			else
				end=elementLength;
		}
		else
		{
			if(currentPage<=pageCount&&currentPage>=(pageCount-elementLength/2))/*对吗这里？？*/
			{
				start=pageCount-elementLength+1;
				if(start<=0)//并不能确保页数一定大于元素长度。
					start=1;
				end=pageCount;
			}
			else
			{
				start=currentPage-elementLength/2+1;
				end=currentPage+elementLength/2;
			}
		}
		System.out.print("起始页："+start);
		System.out.print("   最后一页："+end);
		System.out.println("   当前页："+currentPage);
		
		
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		
		//根据制定的当前页查找制定的目标
		int startIndex=(currentPage-1)*pageSize;
		
		List<Book>list=null;
		if(id==null||"".equals(id))
			list=bookService.getBookBySplitPage(startIndex,pageSize,null);
		else
		{
			list=bookService.getBookBySplitPage(startIndex,pageSize,id);
		}
		request.setAttribute("booklist", list);
		return "showBooks";
	}
		
}

