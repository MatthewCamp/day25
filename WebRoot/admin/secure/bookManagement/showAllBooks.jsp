<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- 对管理员显示出所有的图书 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <style type="text/css">
    	*
    	{
    		margin: 0;
    		padding:0;
    		font-family: 微软雅黑;
    	}
    	table
    	{
    		border:1px solid black;
    		border-collapse: collapse;
    		margin: 0 auto;
    		margin-top: 20px;
    	}
    	td
    	{
    		border:1px solid black;
    		padding:2px;
    		text-align: center;
    	}
    	#index
    	{
    		text-align: center;
    		margin-top: 20px;
    	}
    	#index a
    	{
    		display: inline-block;
    		text-align: center;
    		line-height: 25px;
    		height:25px;
    	/* 	float:right; */
    		/* border: 1px solid black; */
    		margin-right:2px;
    		padding-top:0px;
    		padding-bottom:0px;
    		padding-left:10px;
    		padding-right:10px;
    		color:#000;
    		text-decoration: none;
    		font-size: 12px;
    		font-weight:bold;
    		background-color: #ccc;
    	}
    </style>
    <script type="text/javascript">
    	//修改图书信息，跳转到修改界面
    	function _modify(book)
    	{
    		//var td=book.parentNode;
    		//var tr=td.parentNode;
    		//var bookid=tr.getElementsByTagName("TD")[0].innerHTML;
    		var bookid=document.getElementById("bookid").value;
			window.location.href="<c:url value='/admin/secure/bookManageServlet?cmd=modifyOneBook&bookid="+bookid+"'/>";
    	}
    	//删除图书信息，请求删除之后进行删除
    	function _delete(book)
    	{
    		var bookid=document.getElementById("bookid").value;
    		var request;
    		if(window.XMLHttpRequest){
    			request= new XMLHttpRequest();
    		}else{
    			request= new ActiveXObject("Microsoft.XMLHttp");
    		}
    		var url="<c:url value='/admin/secure/bookManageServlet?cmd=deleteBook&bookid='/>"+bookid;
    		/* alert(url); */
    		request.open("post", url, true);//异步请求处理
    		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    		request.send();
    		request.onreadystatechange=function()
    		{
    			if(request.readyState==4)
   				{
   					if(request.status==200)
					{
   						//如果删除成功的话
   						window.location.reload(true);
					}
   					else
					{
						alert("删除图书"+bookid+"失败！");
					}
   				}
    		};
    	}
    </script>
    
  </head>
  
  <body>
  	<table>
  		<thead>
  			<tr>
  				<td>图书编号</td>
  				<td>书名</td>
  				<td>作者</td>
  				<td>价格</td>
  				<td>折扣</td>
  				<td>数量</td>
  				<td>出版社</td>
  				<td>页数</td>
  				<td>版次</td>
  				<td>上线时间</td>
  				<td>修改</td>
  				<td>删除</td>
  			</tr>
  		</thead>
  		<c:forEach items="${requestScope.booklist}" var="book">
			<tr>
				<input type="hidden" id="bookid" value="${book.id}">
				<td>
					<c:choose>
						<c:when test="${fn:length(book.id)>4}">${fn:substring(book.id,0,9)}...</c:when>
						<c:otherwise>${book.id}</c:otherwise>
					</c:choose>
				</td>
				<td>${book.name}</td>
				<td>${book.auth}</td>
				<td>${book.price}</td>
				<td>${book.rebate}</td>
				<td>${book.amount}</td>
				<td>${book.publisher}</td>
				<td>${book.pages}</td>
				<td>${book.versions}</td>
				<td>${book.onlinetime}</td>
				<td><input type="button" value="修改" onclick="_modify(this)"></td>
				<td><input type="button" value="删除" onclick="_delete(this)"></td>
			</tr>  		
  		</c:forEach>
  	</table>
  	<div id="index">
  			<a href='<c:url value="/admin/secure/bookManageServlet?page=1"/>'>
				首页
			</a>
			<c:choose>
				<c:when test="${currentPage!=1}">
					<c:choose>
						<c:when test="${empty booktypeid}">
							<a href='<c:url value="/admin/secure/bookManageServlet?page=${currentPage-1}"/>'>上一页</a>
						</c:when>
						<c:otherwise>
							<a href='<c:url value="/admin/secure/bookManageServlet?id=${booktypeid}&page=${currentPage-1}"/>'>上一页</a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<a>上一页</a>
				</c:otherwise>
			</c:choose>
			<c:forEach begin="${requestScope.start}" end="${requestScope.end}" var="index">
				<c:choose>
					<c:when test="${currentPage==index}">
						<a id="currentPage" style="color: red;background-color: #ccc;">${index}</a>
					</c:when>
					<c:otherwise>
						<c:if test="${index>=1&&index<=pageCount}">
							<c:choose>
								<c:when test="${empty booktypeid}">
									<a href='<c:url value="/admin/secure/bookManageServlet?page=${index}"/>'>${index}</a>
								</c:when>							
								<c:otherwise>
									<a href='<c:url value="/admin/secure/bookManageServlet?id=${booktypeid}&page=${index}"/>'>${index}</a>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${currentPage!=pageCount}">
					<c:choose>
						<c:when test="${empty booktypeid}">
							<a href='<c:url value="/admin/secure/bookManageServlet?page=${currentPage+1}"/>'>下一页</a>
						</c:when>
						<c:otherwise>
							<a href='<c:url value="/admin/secure/bookManageServlet?id=${booktypeid}&page=${currentPage+1}"/>'>下一页</a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<a>下一页</a>
				</c:otherwise>
			</c:choose>
			<a href='<c:url value="/admin/secure/bookManageServlet?page=${pageCount}"/>'>
				尾页
			</a>
		</div>
  </body>
</html>
