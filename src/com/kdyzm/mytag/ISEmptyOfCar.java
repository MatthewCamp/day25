package com.kdyzm.mytag;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kdyzm.buy.car.Car;

/*
 * 判断购物车是否为空
 */
public class ISEmptyOfCar {
	public static boolean isEmptyOfCar(HttpSession session)
	{
		Map<String,Car>map=(Map<String, Car>) session.getAttribute("carlist");
		if(map.size()==0)
			return true;
		else
			return false;
	}
}
