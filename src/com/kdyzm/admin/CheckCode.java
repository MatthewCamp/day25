package com.kdyzm.admin;
/*
 * ����ר�����admin����֤�����ɷ�ʽ
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
		
		//����130*42�ߴ��С����֤�롣
		response.setContentType("image/jpeg");//�����������Ҫ���������������ͼƬ������֮ǰ��text/html�ı����͡�
		ServletOutputStream sos=response.getOutputStream();
		PrintWriter pw=new PrintWriter(sos,true);
		
		//ȷ����������֤��ĳ��Ϳ�
		int width=130;
		int height=42;
		//���ȴ���һ��ͼƬ������
		BufferedImage bi=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//����ͼ��
		Graphics g=bi.getGraphics();
		//ȷ��ͼ�㱳��ɫ
		g.setColor(Color.white);
		//������
		g.fillRect(0, 0, width, height);//����ͼ��Ϊ��ɫ
		
		//���������ͼ�����
		g.setColor(Color.WHITE);
		//������
		g.fillRect(1, 1, width-2, height-2);//չʾ�������ɫΪ��ɫ
		
		Random random=new Random();
		//��������20��
		for(int i=0;i<10;i++)
		{
			g.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
			g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
		}
		//��ʼѡȡ4�������,����4�������浽session�С�
		StringBuilder sb=new StringBuilder();
		String data="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int space=30;
		for(int i=0;i<4;i++)
		{
			int index=random.nextInt(data.length());
			String aim=data.substring(index, index+1);
			sb.append(aim);
			g.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
			Font font=new Font("����",Font.BOLD,40);
			g.setFont(font);
			g.drawString(aim, i*space+2, height-3);
		}
		HttpSession hs=request.getSession();
		hs.setAttribute("admincheckcode", sb.toString());
		//��ͼƬ���͸������,�������һ��
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
