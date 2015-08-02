package com.kdyzm.utils;

import java.util.UUID;

/*
 * 专门生成UUID信息的
 */
public class UUIDUtils {
	public static String getUUIDString()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
