package com.kdyzm.admin;
/*
 * 这是专门针对admin的验证码生成方式
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckCode extends HttpServlet {
	private static final long serialVersionUID = -1580592405926268805L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//生成130*42尺寸大小的验证码。
		response.setContentType("image/jpeg");//告诉浏览器将要传输的数据类型是图片而不是之前的text/html文本类型。
		ServletOutputStream sos=response.getOutputStream();
		PrintWriter pw=new PrintWriter(sos,true);
		
		//确定创建的验证码的长和宽
		int width=130;
		int height=42;
		//首先创建一个图片缓冲区
		BufferedImage bi=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//创建图层
		Graphics g=bi.getGraphics();
		//确定图层背景色
		g.setColor(Color.white);
		//填充矩形
		g.fillRect(0, 0, width, height);//背景图层为黑色
		
		//创建上面的图层面板
		g.setColor(Color.WHITE);
		//填充矩形
		g.fillRect(1, 1, width-2, height-2);//展示的面板颜色为白色
		
		Random random=new Random();
		//画干扰线20条
		for(int i=0;i<10;i++)
		{
			g.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
			g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
		}
		//开始选取4个随机数,并将4个数保存到session中。
		StringBuilder sb=new StringBuilder();
		String data="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int space=30;
		for(int i=0;i<4;i++)
		{
			int index=random.nextInt(data.length());
			String aim=data.substring(index, index+1);
			sb.append(aim);
			g.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
			Font font=new Font("宋体",Font.BOLD,40);
			g.setFont(font);
			g.drawString(aim, i*space+2, height-3);
		}
		HttpSession hs=request.getSession();
		hs.setAttribute("admincheckcode", sb.toString());
		//将图片发送给浏览器,这是最后一步
		/**
		 * <mime-mapping>
        	<extension>jpg</extension>
        	<mime-type>image/jpeg</mime-type>
    		</mime-mapping>
		 */
		ImageIO.write(bi, "jpg", sos);
		pw.close();
	}

}
