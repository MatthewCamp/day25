package com.kdyzm.mytag;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.kdyzm.buy.car.Car;
/*
 * @author kdyzm
 *判断商品是否存在，根据书籍id信息。
 *如果返回false表名没有这个商品信息，即还没有加入购物车
 *反之，如果返回值是true，则表示已经加入购物车。
 */
public class ISGoodsExists {
	public static boolean isContainsGoods(HttpSession session,String bookid)
	{
		if(session==null)
		{
			System.out.println("session为空！");
		}
		Object list=session.getAttribute("carlist");
		if(list==null)
		{
			return false;
		}
		Map<String,Car>map=(Map<String, Car>) list;
		Set<Map.Entry<String, Car>> set=map.entrySet();
		Iterator<Map.Entry<String, Car>> it=set.iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Car>entry=it.next();
			String key=entry.getKey();
			if(key.equals(bookid))
			{
				return true;
			}
		}
		return false;
	}
}
