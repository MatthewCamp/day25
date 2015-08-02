package com.kdyzm.interfaces;

import com.kdyzm.myannotation.Transactionflag;

public interface TransactionInterface {
	@Transactionflag//表示该方法需要使用事务
	public void save();//定义save事务解决方法。
	//不加注解表示该方法不需要使用事务。
	public void query();
}
