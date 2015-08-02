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
	//��ʾ���������ϸ��Ϣ
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
	 * Ĭ�Ϸ����ǲ�������ͼ����Ϣ����ͼ����Ϣת����ҳ������ʾ
	 * */
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) {
		//����Ҫ���д������ò��С�
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
			System.out.println("�ҵ�"+lines+"�����ݣ�");
			request.setAttribute("booktypeid", id);//����jspҳ��
			String booktype=bts.getBookTypeByBookTypeid(id);
			request.getSession().setAttribute("booktype", booktype);
			request.getSession().setAttribute("booktypeid", id);
		}
//		System.out.println("���ҳ�"+lines+"����¼��");
		int pageSize=6;//ÿһҳ��ʾ���ٱ��顣
		int elementLength=7;//ÿһ���б���ʾ���ٸ�Ԫ�ء�
		int pageCount=(lines/pageSize)+(lines%pageSize==0?0:1);//һ���ж���ҳ��
//		System.out.println("һ����"+pageCount+"ҳ��");
		int currentPage=0;
		String page=request.getParameter("page");
		if(page==null||"".equals(page))
		{
//			System.out.println("��page����");
			currentPage=1;
		}
		else
		{
			currentPage=Integer.parseInt(page);
//			System.out.println("��������"+currentPage);
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
			if(currentPage<=pageCount&&currentPage>=(pageCount-elementLength/2))/*���������*/
			{
				start=pageCount-elementLength+1;
				if(start<=0)//������ȷ��ҳ��һ������Ԫ�س��ȡ�
					start=1;
				end=pageCount;
			}
			else
			{
				start=currentPage-elementLength/2+1;
				end=currentPage+elementLength/2;
			}
		}
		System.out.print("��ʼҳ��"+start);
		System.out.print("   ���һҳ��"+end);
		System.out.println("   ��ǰҳ��"+currentPage);
		
		
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		
		//�����ƶ��ĵ�ǰҳ�����ƶ���Ŀ��
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

