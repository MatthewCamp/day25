package com.kdyzm.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import com.kdyzm.myannotation.Transactionflag;

/*
 * 对Service类进行代理，拦截特定的方法并进行修改，实现InvocationHandler接口是经典的做法。
 * 该类可以放在工具类中。
 */
public class ProxyForTransactionService implements InvocationHandler{
	private Object src;
	private ProxyForTransactionService(Object src)
	{
		this.src=src;
	}
	public static Object factory(Object src)
	{
		Object aim=Proxy.newProxyInstance(ProxyForTransactionService.class.getClassLoader(),
				src.getClass().getInterfaces(), 
				new ProxyForTransactionService(src));
		return aim;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Connection connection=null;
		Object returnValue=null;
		if(!method.isAnnotationPresent(Transactionflag.class))
		{
			System.out.println("不需要开启事务的方法："+method.getName());
			return method.invoke(src, args);
		}
		System.out.println("需要开启事务的方法："+method.getName());
		try
		{
			//获取连接
			connection=DataSourceUtils_c3p0.getConnection();
			//设置事务的开始
			connection.setAutoCommit(false);
			System.out.println("已经开启事务！");
			//调用方法
			returnValue=method.invoke(src, args);
			connection.commit();
			System.out.println("提交！结束事务！");
		}
		catch(Exception e)
		{
			connection.rollback();
			System.out.println("回滚！结束事务！");
			throw e;//为什么能抛，因为Throwable是Exception的父类。
		}
		finally
		{
			connection.close();
			DataSourceUtils_c3p0.remove();
		}
		return returnValue;
	}
	
}
