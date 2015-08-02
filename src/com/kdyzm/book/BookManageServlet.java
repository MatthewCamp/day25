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
/*
 * ʵ�й���Ա��ͼ�����ɾ��ĵ�����
 */
public class BookManageServlet extends BaseServlet {
	private static final long serialVersionUID = 8260966681434648722L;
	BookTypeService bts=new BookTypeService();
	//һ�������ɴ���
//	BookServiceInterface bookService=(BookServiceInterface) ProxyForTransactionService.factory(new BookService());
	BookService bookService=new BookService();
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//����Ҫ���д������ò��С�
		String id=request.getParameter("id");
		int lines=0;
		if(id==null||"".equals(id))
		{
			lines=bookService.getAllBooksColumns(null);
			request.getSession().setAttribute("booktypename", "ȫ��ͼ��");
			request.getSession().setAttribute("booktypeid","");
		}
		else
		{
			lines=bookService.getAllBooksColumns(id);
//			System.out.println("�ҵ�"+lines+"�����ݣ�");
			request.setAttribute("booktypeid", id);//����jspҳ��
			String booktype=bts.getBookTypeByBookTypeid(id);
			request.getSession().setAttribute("booktype", booktype);
			request.getSession().setAttribute("booktypeid", id);
		}
//		System.out.println("���ҳ�"+lines+"����¼��");
		int pageSize=15;//ÿһҳ��ʾ���ٱ��顣
		int elementLength=10;//ÿһ���б���ʾ���ٸ�Ԫ�ء�
		int pageCount=(lines/pageSize)+(lines%pageSize==0?0:1);//һ���ж���ҳ��
//		System.out.println("һ����"+pageCount+"ҳ��");
		int currentPage=0;
		String page=request.getParameter("page");
		if(page==null||"".equals(page))
		{
//			System.out.println("��page����");
			currentPage=1;
		}
		else
		{
			currentPage=Integer.parseInt(page);
//			System.out.println("��������"+currentPage);
			if(currentPage<=0)
			{
				currentPage=1;
			}
			if(currentPage>pageCount)
			{
				currentPage=pageCount;
			}
		}
		int start=0;
		int end=0;
		if(currentPage>=1&&currentPage<=(elementLength/2+1))
		{
			start=1;
			if(pageCount<elementLength)
				end=pageCount;
			else
				end=elementLength;
		}
		else
		{
			if(currentPage<=pageCount&&currentPage>=(pageCount-elementLength/2))/*���������*/
			{
				start=pageCount-elementLength+1;
				if(start<=0)//������ȷ��ҳ��һ������Ԫ�س��ȡ�
					start=1;
				end=pageCount;
			}
			else
			{
				start=currentPage-elementLength/2+1;
				end=currentPage+elementLength/2;
			}
		}
//		System.out.print("��ʼҳ��"+start);
//		System.out.print("   ���һҳ��"+end);
//		System.out.println("   ��ǰҳ��"+currentPage);
		
		
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		
		//�����ƶ��ĵ�ǰҳ�����ƶ���Ŀ��
		int startIndex=(currentPage-1)*pageSize;
		
		List<Book>list=null;
		if(id==null||"".equals(id))
			list=bookService.getBookBySplitPage(startIndex,pageSize,null);
		else
		{
			list=bookService.getBookBySplitPage(startIndex,pageSize,id);
		}
		request.setAttribute("booklist", list);
		return "showBooksForAdmin";
	}
	//����ͼ��idɾ��ͼ��ķ���
	public String deleteBook(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Book book=bookService.getOneBookById(bookid);//��������������ᱨ��ָ���쳣
		Book boo=bookServiceTr.deleteBookById(bookid);
		if(boo==null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		//������ɾ���ɹ����ٽ�����ɾ��
		deleteOldImg(book.getImg());
		System.out.println("����ɾ���ɹ���");
		return null;
	}
	//����ͼ��id�����������Ϣ�������������Ϣת����ָ��ҳ��ķ���
	public String modifyOneBook(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Book book=bookService.getOneBookById(bookid);
		String img=book.getImg();
		img=this.getServletContext().getRealPath("/imgs/"+img);
		book.setImg(img);
		List<BookType>booktype=bts.getBookTypeByBookid(bookid);
		List<BookType>list=bts.queryAll();
		request.setAttribute("book", book);
		request.setAttribute("booktype", booktype);
		request.setAttribute("booktypes", list);
		return "modifyOnebook";
	}
	
	//TODO ����޸�һ���鹦�ܵķ�����
	BookServiceInterface bookServiceTr=(BookServiceInterface) ProxyForTransactionService.factory(new BookService());
	//�޸�һ����
	public String modifyOneBookAction(HttpServletRequest request,HttpServletResponse response) throws IOException
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
						System.out.println("�ֶ���Ϊ��"+fileName);
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
						book.setId(map.get("id")[0]);
						String value=getUploadImageName(file,book);
						map.put(fileName,new String[]{value});
					}
				}
				BeanUtils.populate(book, map);
				System.out.println(book);
				System.out.println(booktypelist);
				//TODO ������Ҫ���������������Ĺ���
				Book boo=bookServiceTr.updateOneBook(book,booktypelist);
				if(boo==null)
				{
					System.out.println("�޸�ͼ����Ϣʧ�ܣ�");
					return null;
				}
				request.setAttribute("book", book);
				List<BookType>btls=bts.getBookTypeByBookid(book.getId());
				request.setAttribute("booktypes", btls);
				//����޸ĳɹ�������ת����ʾͼ����ϸ��Ϣҳ�档
				return "showOneBookInfo";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getUploadImageName(FileItem file,Book book) throws IOException
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
		sfu.setSizeMax(1024*1024*10);//�����ϴ����ܴ�СΪ10MB
		String fileName=file.getName();
		System.out.println("�ļ���Ϊ��"+fileName);
		String oldimg=bookService.getImgByBookid(book.getId());
		if("".equals(fileName))
		{
			System.out.println("δѡ���ļ���");
			return oldimg;
		}
		//�����ʹ�����µķ���ͼƬ����ԭ���ķ���ͼƬɾ������
		deleteOldImg(oldimg);
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
	//ɾ�����ɵķ���
	private void deleteOldImg(String oldimg) {
		String path=this.getServletContext().getRealPath("imgs/"+oldimg);
		File file=new File(path);
		file.delete();
		System.out.println(path+" ɾ���ɹ���");
	}
}
