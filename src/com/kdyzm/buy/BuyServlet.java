package com.kdyzm.buy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kdyzm.book.service.BookService;
import com.kdyzm.buy.car.Car;
import com.kdyzm.domain.Book;
import com.kdyzm.utils.BaseServlet;

public class BuyServlet extends BaseServlet {
	private static final long serialVersionUID = -8557311797371631323L;
	BookService bookService=new BookService();
	//Ĭ�Ϸ����Ǽ��빺�ﳵ
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("������"+request.getParameter("bookid")+"���빺�ﳵ��");
		String bookid=request.getParameter("bookid");
		@SuppressWarnings("unchecked")
		Map<String,Car>carlist=(Map<String,Car>)request.getSession().getAttribute("carlist");
		if(carlist==null)
		{
			carlist=new HashMap<String,Car>();
			request.getSession().setAttribute("carlist", carlist);
		}
		Car car=new Car();
		Book book=bookService.getOneBookById(bookid);
		car.setAmount(1);//��ʼ���빺�ﳵ��ʱ��Ĭ�϶���1
		car.setBook(book);
		carlist.put(bookid, car);//���빺�ﳵ
//		System.out.println(carlist.size());
		return null;
	}
	
	//��ʾ���ﳵ�е����ݣ�����Ĺ������ǽ�mapת����list<map>��������ת����
	public String showCar(HttpServletRequest request,HttpServletResponse response)
	{
		@SuppressWarnings("unchecked")
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//�����������жϹ��ﳵ�Ƿ�Ϊ��,���Ϊ�յĻ����ض���һ����ʾҳ�������ʾ�Ƿ�Ҫ������һ���������繺�
		if(map==null)
		{
			return "302:emptyCar";
		}
		List<Map<String,String>>list=new ArrayList<Map<String,String>>();
		Set<Map.Entry<String, Car>>set=map.entrySet();
		Iterator<Map.Entry<String, Car>>it=set.iterator();
		while(it.hasNext())
		{
			Map<String,String> book=new HashMap<String,String>();
			Map.Entry<String, Car> entry=it.next();
			Car car=entry.getValue();
			book.put("id", car.getBook().getId());
			book.put("name", car.getBook().getName());
			book.put("img", car.getBook().getImg());
			book.put("auth", car.getBook().getAuth());
			
			double price=car.getBook().getPrice();
			double rebate=car.getBook().getRebate();
			double result=price*rebate;
			book.put("price", result+"");//�������֮����������ۡ�
			
			book.put("amount", car.getAmount()+"");//�õ����˶��ٱ��顣
			
			list.add(book);
			
		}
		request.setAttribute("shopping", list);
		return "showCar";//����ҳ���ת��
	}
	/*
	 * ��չ��ﳵ�ķ�����
	 */
	public String clearCar(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session =request.getSession();
		Map<String,Car>map=(Map<String, Car>) session.getAttribute("carlist");
		map.clear();
		return null;
	}
	/*
	 * ������ﳵ��Ϊ�գ������Ƽ����б�,�������ض���
	 * ѡ���屾�Ƽ���ͼ��
	 */
	public String emptyCar(HttpServletRequest request,HttpServletResponse response)
	{
		//��ȡ�屾�Ƽ�ͼ��
		List<Book>list=bookService.getBookLimitFive();
		request.setAttribute("recognizedBooks", list);
		return "emptytip";
	}
	
	//����id����һ����Ĺ�����
	public String addOneBookByBookid(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//����Map���в���ָ����id
		Set<Map.Entry<String, Car>>set=map.entrySet();
		Iterator<Map.Entry<String, Car>>it=set.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Car>entry=it.next();
			if(entry.getKey().equals(bookid))
			{
				Car car=entry.getValue();
				car.setAmount(car.getAmount()+1);
				return null;
			}
		}
		return null;
	}
	//����id����һ����Ĺ�����
	public String decreaseOneBookByBookid(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//����Map���в���ָ����id
		Set<Map.Entry<String, Car>>set=map.entrySet();
		Iterator<Map.Entry<String, Car>>it=set.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Car>entry=it.next();
			if(entry.getKey().equals(bookid))
			{
				Car car=entry.getValue();
				car.setAmount(car.getAmount()-1);
				return null;
			}
		}
		return null;
	}
	
	/*
	 * ����ָ�����鼮idɾ��ָ���Ĺ��ﳵ�е�ͼ�顣
	 */
	public String deleteBookByIdFromCar(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//����Map���в���ָ����id
		Set<Map.Entry<String, Car>>set=map.entrySet();
		Iterator<Map.Entry<String, Car>>it=set.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Car>entry=it.next();
			if(entry.getKey().equals(bookid))
			{
//				System.out.println("ɾ��֮ǰ��"+map.size());
				map.remove(bookid);
//				System.out.println("ɾ��֮��"+map.size());
				break;
			}
		}
		return null;
	}
}
