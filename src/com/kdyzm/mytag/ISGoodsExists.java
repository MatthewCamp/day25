package com.kdyzm.mytag;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.kdyzm.buy.car.Car;
/*
 * @author kdyzm
 *�ж���Ʒ�Ƿ���ڣ������鼮id��Ϣ��
 *�������false����û�������Ʒ��Ϣ������û�м��빺�ﳵ
 *��֮���������ֵ��true�����ʾ�Ѿ����빺�ﳵ��
 */
public class ISGoodsExists {
	public static boolean isContainsGoods(HttpSession session,String bookid)
	{
		if(session==null)
		{
			System.out.println("sessionΪ�գ�");
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
