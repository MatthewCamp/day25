package com.kdyzm.book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kdyzm.book.service.BookService;
import com.kdyzm.book.service.BookServiceInterface;
import com.kdyzm.booktype.service.BookTypeService;
import com.kdyzm.domain.Book;
import com.kdyzm.domain.BookType;
import com.kdyzm.utils.BaseServlet;
import com.kdyzm.utils.ProxyForTransactionService;

public class AddNewBookServlet extends BaseServlet {
	private static final long serialVersionUID = -2171143423085279301L;
	BookTypeService bts=new BookTypeService();
	//һ�������ɴ���
	BookServiceInterface bookService=(BookServiceInterface) ProxyForTransactionService.factory(new BookService());
	//Ĭ�Ϸ�������ʾ��������ҳ��
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("�����������");
		//���Ȼ�����е�ͼ����ࡣ
		List<BookType>list=bts.queryAll();
		request.setAttribute("booktypes", list);
		return "addNewBook";
	}
	public String addOneBook(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//1.��ñ����ϴ��ļ���Ŀ¼��·���Լ������ļ���Ŀ¼
		String tempPath=this.getServletContext().getRealPath("temp");
		//2.���ô����ļ���Ŀ�������ʵ�������û���Ŀ¼�Լ��ļ���С����
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		dfif.setRepository(new File(tempPath));
		dfif.setSizeThreshold(1024*10);
		//3.���ServletFileUpload��ʵ��
		ServletFileUpload sfu=new ServletFileUpload(dfif);
		sfu.setSizeMax(1024*1024*3);//�����ϴ����ܴ�СΪ3MB
//		sfu.setFileSizeMax(1024*1024);//����ÿ���ϴ����ļ���С���Ϊ1MB
		//4.�����ϴ��б�
		try {
				List<FileItem>list=sfu.parseRequest(request);
				Iterator<FileItem>it=list.iterator();
				Book book=new Book();
				Map<String,String[]> map=new HashMap<String,String[]>();
				List<String>booktypelist=new ArrayList<String>();
				while(it.hasNext())
				{
					FileItem file=it.next();
					if(file.isFormField())//��������ļ��ϴ�������ͨ���ı��ļ�
					{
						String fileName=file.getFieldName();
						if(fileName.equals("booktype"))
						{
							booktypelist.add(file.getString());
							continue;
						}
						String value=file.getString();
						value=new String(value.getBytes("iso-8859-1"),"utf-8");
						map.put(fileName,new String[]{value});
					}
					else
					{
						String fileName=file.getFieldName();
						String value=getUploadImageName(file);
						map.put(fileName,new String[]{value});
					}
				}
				BeanUtils.populate(book, map);
				System.out.println(book);
				//������Ҫ���������������Ĺ���
				Book boo=bookService.addNewBook(book,booktypelist);
				if(boo==null)//������ͼ��ʧ��
				{
					System.out.println("�����ͼ��ʧ�ܣ�");
					return null;
				}
				else//������ͼ��ɹ�������ת����ϸ��Ϣҳ�������ʾ
				{
					request.setAttribute("book", book);
					List<BookType>btls=bts.getBookTypeByBookid(book.getId());
					request.setAttribute("booktypes", btls);
					return "showOneBookInfo";
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getUploadImageName(FileItem file) throws IOException
	{
		//1.��ñ����ϴ��ļ���Ŀ¼��·���Լ������ļ���Ŀ¼
		String savaPath=this.getServletContext().getRealPath("imgs");
		String tempPath=this.getServletContext().getRealPath("temp");
		//2.���ô����ļ���Ŀ�������ʵ�������û���Ŀ¼�Լ��ļ���С����
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		dfif.setRepository(new File(tempPath));
		dfif.setSizeThreshold(1024*10);
		//3.���ServletFileUpload��ʵ��
		ServletFileUpload sfu=new ServletFileUpload(dfif);
		sfu.setSizeMax(1024*1024*3);//�����ϴ����ܴ�СΪ3MB
		String fileName=file.getName();
		fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
//				String fileType=file.getContentType();
		InputStream is=file.getInputStream();
		
		int hashCode=fileName.hashCode();
		String dir1=Integer.toHexString(hashCode&0XF);//�����һ��Ŀ¼
		String dir2=Integer.toHexString((hashCode>>4)&0XF);//����ڶ���Ŀ¼
		
		String aimDir=savaPath+"/"+dir1;
		if(!new File(aimDir).exists())
		{
			new File(aimDir).mkdir();
		}
		aimDir=aimDir+"/"+dir2;
		if(!new File(aimDir).exists())
		{
			new File(aimDir).mkdir();
		}
//		String extName=fileName.substring(fileName.lastIndexOf("."));
		String savaFileName=aimDir+"/";
		String extName=fileName.substring(fileName.lastIndexOf("."));
		fileName=UUID.randomUUID().toString().replaceAll("-", "")+extName;
		savaFileName=savaFileName+fileName;
		FileOutputStream fos=new FileOutputStream(new File(savaFileName));
//				long fileSize=is.available();
		int length=-1;
		byte []buff=new byte[1024*1024];
		while((length=is.read(buff))!=-1){
			fos.write(buff, 0, length);
		}
		fos.close();
		is.close();
		file.delete();//��ջ����ļ�
		return dir1+"/"+dir2+"/"+fileName;
	}
}
