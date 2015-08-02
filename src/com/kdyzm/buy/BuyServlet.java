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
	//默认方法是加入购物车
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("即将把"+request.getParameter("bookid")+"加入购物车！");
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
		car.setAmount(1);//初始加入购物车的时候默认都是1
		car.setBook(book);
		carlist.put(bookid, car);//放入购物车
//		System.out.println(carlist.size());
		return null;
	}
	
	//显示购物车中的内容，这里的工作就是将map转换成list<map>，并进行转发。
	public String showCar(HttpServletRequest request,HttpServletResponse response)
	{
		@SuppressWarnings("unchecked")
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//这里必须进行判断购物车是否为空,如果为空的话则重定向到一个提示页面进行提示是否要进行下一步操作比如购物。
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
			book.put("price", result+"");//计算打折之后的真正单价。
			
			book.put("amount", car.getAmount()+"");//得到买了多少本书。
			
			list.add(book);
			
		}
		request.setAttribute("shopping", list);
		return "showCar";//进行页面的转发
	}
	/*
	 * 清空购物车的方法。
	 */
	public String clearCar(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session =request.getSession();
		Map<String,Car>map=(Map<String, Car>) session.getAttribute("carlist");
		map.clear();
		return null;
	}
	/*
	 * 如果购物车中为空，生成推荐的列表,并进行重定向
	 * 选择五本推荐的图书
	 */
	public String emptyCar(HttpServletRequest request,HttpServletResponse response)
	{
		//获取五本推荐图书
		List<Book>list=bookService.getBookLimitFive();
		request.setAttribute("recognizedBooks", list);
		return "emptytip";
	}
	
	//根据id增加一本书的购书量
	public String addOneBookByBookid(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//遍历Map进行查找指定的id
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
	//根据id减少一本书的购书量
	public String decreaseOneBookByBookid(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//遍历Map进行查找指定的id
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
	 * 根据指定的书籍id删除指定的购物车中的图书。
	 */
	public String deleteBookByIdFromCar(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Map<String,Car>map=(Map<String, Car>) request.getSession().getAttribute("carlist");
		//遍历Map进行查找指定的id
		Set<Map.Entry<String, Car>>set=map.entrySet();
		Iterator<Map.Entry<String, Car>>it=set.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Car>entry=it.next();
			if(entry.getKey().equals(bookid))
			{
//				System.out.println("删除之前："+map.size());
				map.remove(bookid);
//				System.out.println("删除之后："+map.size());
				break;
			}
		}
		return null;
	}
}
