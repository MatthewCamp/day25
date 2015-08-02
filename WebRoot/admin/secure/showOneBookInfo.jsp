<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 添加一本新书 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <style type="text/css">
    	*{
    		margin: 0;
    		padding: 0;
    		font-family: 微软雅黑;
    	}
    	table
    	{
    		border:1px solid black;
    		width:800px;
    		border-collapse: collapse;
    		margin: 0 auto;
    		margin-top: 20px;
    		margin-bottom: 20px;
    	}
    	tr
    	{
    		border:1px solid black;
			padding:2px;    		
    	}
    	.one
    	{
    		border:1px solid black;
    		text-align: center;
    		height:30px;
    		font-size:14px;
    		line-height: 30px;
    		width:100px;
    	}
    	.two
    	{
    		width:600px;
    		height:30px;
    		font-size:14px;
    	}
    </style>
    <script type="text/javascript">
    	function confirm()
    	{
    		window.location.href="<c:url value='/admin/secure/roleManageServlet'/>";
    	}
    </script>
    <script type="text/javascript" src="<c:url value='/js/pub/DatePicker.js'/>"></script>
  </head>
  
  <body>
		<table>
			<tr>
				<td class="one">图书索引号</td>
				<td class="two">${book.id}</td>
			</tr>
			<tr>
				<td class="one">书名</td>
				<td class="two">${book.name}</td>
			</tr>
			<tr>
				<td class="one">价格</td>
				<td class="two">${book.price}</td>
			</tr>
			<tr>
				<td class="one">作者</td>
				<td class="two">${book.auth}</td>
			</tr>
			<tr>
				<td class="one">封面</td>
				<td class="two">${book.img}</td>
			</tr>
			<tr>
				<td class="one">折扣</td>
				<td class="two">${book.rebate}</td>
			</tr>
			<tr>
				<td class="one">库存量</td>
				<td class="two">${book.amount}</td>
			</tr>
			<tr>
				<td class="one">出版社</td>
				<td class="two">${book.publisher}</td>
			</tr>
			<tr>
				<td class="one">出版时间</td>
				<td class="two">
					${book.publishdate}
				</td>
			</tr>
			<tr>
				<td class="one">页数</td>
				<td class="two">${book.pages}</td>
			</tr>
			<tr>
				<td class="one">纸张大小</td>
				<td class="two">${book.size}</td>
			</tr>
			<tr>
				<td class="one">印次</td>
				<td class="two">${book.printtimes}</td>
			</tr>
			<tr>
				<td class="one">版次</td>
				<td class="two">${book.versions}</td>
			</tr>
			<tr>
				<td class="one">上线时间</td>
				<td class="two">${book.onlinetime}</td>
			</tr>
			<tr>
				<td class="one">图书分类</td>
				<td class="two">
					<c:forEach items="${requestScope.booktypes}" var="booktype">
						${booktype.name}&nbsp;
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td class="one">简介</td>
				<td>${book.brief}</td>
			</tr>
			<tr>
				<td class="one">详细介绍</td>
				<td>${book.content}</td>
			</tr>
			<tr>
				<td class="one" colspan="2">
					<input type="button" value="确认" onclick="confirm()">
				</td>
			</tr>
		</table>
  </body>
</html>
