package com.kdyzm.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kdyzm.utils.BaseServlet;

public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 7936709211836790475L;
	//Ĭ�Ϸ�������ʾ����ҳ��
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		return "showOrder";
	}
	
	
}
