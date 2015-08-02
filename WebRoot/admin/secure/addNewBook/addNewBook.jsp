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
    	.one
    	{
    		border:1px solid black;
    		text-align: center;
    		height:30px;
    		font-size:14px;
    		line-height: 30px;
    		width:300px;
    	}
    	.two
    	{
    		width:600px;
    		height:30px;
    	}
    	.three
    	{
    		width:600px;
    		height:30px;
    	}
    	.four
    	{
    		display: block;
    		margin-left: 20px;
    	}
    	textarea
    	{
    		width:600px;
    		height:200px;
    	}
    </style>
    <script type="text/javascript" src="<c:url value='/js/pub/DatePicker.js'/>"></script>
  </head>
  
  <body>
	<form enctype="multipart/form-data" action="<c:url value='/admin/secure/addNewBookServlet?cmd=addOneBook'/>" method="post">
		<table>
			<tr>
				<td class="one">书名</td>
				<td class="two"><input class="three" type="text" name="name"></td>
			</tr>
			<tr>
				<td class="one">价格</td>
				<td class="two"><input class="three" type="text" name="price"></td>
			</tr>
			<tr>
				<td class="one">作者</td>
				<td class="two"><input class="three" type="text" name="auth"></td>
			</tr>
			<tr>
				<td class="one">封面</td>
				<td class="two"><input class="four" value="单击此处上传" type="file" name="img"></td>
			</tr>
			<tr>
				<td class="one">折扣</td>
				<td class="two"><input class="three" type="text" name="rebate"></td>
			</tr>
			<tr>
				<td class="one">库存量</td>
				<td class="two"><input class="three" type="text" name="amount"></td>
			</tr>
			<tr>
				<td class="one">出版社</td>
				<td class="two"><input class="three" type="text" name="publisher"></td>
			</tr>
			<tr>
				<td class="one">出版时间</td>
				<td class="two">
					<input type="text" onfocus="setday(this,'yyyy-MM-dd','2010-01-01','2010-12-30',1)" readonly="readonly"  class="three" style="width:150px;" type="text" name="publishdate">
				</td>
			</tr>
			<tr>
				<td class="one">页数</td>
				<td class="two"><input class="three" type="text" name="pages"></td>
			</tr>
			<tr>
				<td class="one">纸张大小</td>
				<td class="two"><input class="three" type="text" name="size"></td>
			</tr>
			<tr>
				<td class="one">印次</td>
				<td class="two"><input class="three" type="text" name="printtimes"></td>
			</tr>
			<tr>
				<td class="one">版次</td>
				<td class="two"><input type="text" name="versions" class="three"></td>
			</tr>
			<tr>
				<td class="one">上线时间</td>
				<td class="two"><input style="width:150px;" type="text" onfocus="setday(this,'yyyy-MM-dd','2010-01-01','2010-12-30',1)" readonly="readonly"  name="onlinetime" class="three"></td>
			</tr>
			<tr>
				<td class="one">图书分类</td>
				<td class="two" style="border-top: 1px solid black;font-size:15px;">
					<c:forEach items="${requestScope.booktypes}" var="booktype">
						${booktype.name}<input type="checkbox" name="booktype" value="${booktype.id}">&nbsp;
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td class="one">简介</td>
				<td><textarea rows="" cols="" name="brief"></textarea></td>
			</tr>
			<tr>
				<td class="one">详细介绍</td>
				<td><textarea rows="" cols="" name="content"></textarea></td>
			</tr>
			<tr>
				<td class="one" colspan="2">
					<input type="submit" value="单击此处提交">
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>
