package com.kdyzm.utils;

import java.util.UUID;

/*
 * ר������UUID��Ϣ��
 */
public class UUIDUtils {
	public static String getUUIDString()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
