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
 * 实行管理员对图书的增删查改的请求。
 */
public class BookManageServlet extends BaseServlet {
	private static final long serialVersionUID = 8260966681434648722L;
	BookTypeService bts=new BookTypeService();
	//一句代码完成代理
//	BookServiceInterface bookService=(BookServiceInterface) ProxyForTransactionService.factory(new BookService());
	BookService bookService=new BookService();
	@Override
	public String defaultMethod(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//这里要进行代码重用才行。
		String id=request.getParameter("id");
		int lines=0;
		if(id==null||"".equals(id))
		{
			lines=bookService.getAllBooksColumns(null);
			request.getSession().setAttribute("booktypename", "全部图书");
			request.getSession().setAttribute("booktypeid","");
		}
		else
		{
			lines=bookService.getAllBooksColumns(id);
//			System.out.println("找到"+lines+"行数据！");
			request.setAttribute("booktypeid", id);//传回jsp页面
			String booktype=bts.getBookTypeByBookTypeid(id);
			request.getSession().setAttribute("booktype", booktype);
			request.getSession().setAttribute("booktypeid", id);
		}
//		System.out.println("查找出"+lines+"个记录！");
		int pageSize=15;//每一页显示多少本书。
		int elementLength=10;//每一个列表显示多少个元素。
		int pageCount=(lines/pageSize)+(lines%pageSize==0?0:1);//一共有多少页。
//		System.out.println("一共有"+pageCount+"页！");
		int currentPage=0;
		String page=request.getParameter("page");
		if(page==null||"".equals(page))
		{
//			System.out.println("空page参数");
			currentPage=1;
		}
		else
		{
			currentPage=Integer.parseInt(page);
//			System.out.println("解析出："+currentPage);
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
			if(currentPage<=pageCount&&currentPage>=(pageCount-elementLength/2))/*对吗这里？？*/
			{
				start=pageCount-elementLength+1;
				if(start<=0)//并不能确保页数一定大于元素长度。
					start=1;
				end=pageCount;
			}
			else
			{
				start=currentPage-elementLength/2+1;
				end=currentPage+elementLength/2;
			}
		}
//		System.out.print("起始页："+start);
//		System.out.print("   最后一页："+end);
//		System.out.println("   当前页："+currentPage);
		
		
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		
		//根据制定的当前页查找制定的目标
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
	//根据图书id删除图书的方法
	public String deleteBook(HttpServletRequest request,HttpServletResponse response)
	{
		String bookid=request.getParameter("bookid");
		Book book=bookService.getOneBookById(bookid);//必须放在这里，否则会报空指针异常
		Book boo=bookServiceTr.deleteBookById(bookid);
		if(boo==null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		//如果真的删除成功了再将封面删除
		deleteOldImg(book.getImg());
		System.out.println("封面删除成功！");
		return null;
	}
	//根据图书id，查找相关信息并将方法相关信息转发到指定页面的方法
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
	
	//TODO 完成修改一本书功能的方法。
	BookServiceInterface bookServiceTr=(BookServiceInterface) ProxyForTransactionService.factory(new BookService());
	//修改一本书
	public String modifyOneBookAction(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//1.获得保存上传文件的目录的路径以及缓存文件的目录
		String tempPath=this.getServletContext().getRealPath("temp");
		//2.设置磁盘文件项目工厂类的实例并设置缓存目录以及文件大小设置
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		dfif.setRepository(new File(tempPath));
		dfif.setSizeThreshold(1024*10);
		//3.获得ServletFileUpload的实例
		ServletFileUpload sfu=new ServletFileUpload(dfif);
		sfu.setSizeMax(1024*1024*3);//限制上传的总大小为3MB
//		sfu.setFileSizeMax(1024*1024);//限制每个上传的文件大小最多为1MB
		//4.遍历上传列表
		try {
				List<FileItem>list=sfu.parseRequest(request);
				Iterator<FileItem>it=list.iterator();
				Book book=new Book();
				Map<String,String[]> map=new HashMap<String,String[]>();
				List<String>booktypelist=new ArrayList<String>();
				while(it.hasNext())
				{
					FileItem file=it.next();
					if(file.isFormField())//如果不是文件上传，是普通的文本文件
					{
						String fileName=file.getFieldName();
						System.out.println("字段名为："+fileName);
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
				//TODO 这里需要开启事务进行事务的管理。
				Book boo=bookServiceTr.updateOneBook(book,booktypelist);
				if(boo==null)
				{
					System.out.println("修改图书信息失败！");
					return null;
				}
				request.setAttribute("book", book);
				List<BookType>btls=bts.getBookTypeByBookid(book.getId());
				request.setAttribute("booktypes", btls);
				//如果修改成功，则跳转到显示图书详细信息页面。
				return "showOneBookInfo";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getUploadImageName(FileItem file,Book book) throws IOException
	{
		//1.获得保存上传文件的目录的路径以及缓存文件的目录
		String savaPath=this.getServletContext().getRealPath("imgs");
		String tempPath=this.getServletContext().getRealPath("temp");
		//2.设置磁盘文件项目工厂类的实例并设置缓存目录以及文件大小设置
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		dfif.setRepository(new File(tempPath));
		dfif.setSizeThreshold(1024*10);
		//3.获得ServletFileUpload的实例
		ServletFileUpload sfu=new ServletFileUpload(dfif);
		sfu.setSizeMax(1024*1024*10);//限制上传的总大小为10MB
		String fileName=file.getName();
		System.out.println("文件名为："+fileName);
		String oldimg=bookService.getImgByBookid(book.getId());
		if("".equals(fileName))
		{
			System.out.println("未选择文件！");
			return oldimg;
		}
		//如果是使用了新的封面图片，则将原来的封面图片删除掉。
		deleteOldImg(oldimg);
		fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
//				String fileType=file.getContentType();
		InputStream is=file.getInputStream();
		
		int hashCode=fileName.hashCode();
		String dir1=Integer.toHexString(hashCode&0XF);//计算第一级目录
		String dir2=Integer.toHexString((hashCode>>4)&0XF);//计算第二级目录
		
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
		file.delete();//清空缓存文件
		return dir1+"/"+dir2+"/"+fileName;
	}
	//删除掉旧的封面
	private void deleteOldImg(String oldimg) {
		String path=this.getServletContext().getRealPath("imgs/"+oldimg);
		File file=new File(path);
		file.delete();
		System.out.println(path+" 删除成功！");
	}
}
