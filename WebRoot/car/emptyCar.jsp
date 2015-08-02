<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- 空购物车的页面提示 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <link type="text/css" href="<c:url value='/css/pub/pub.css'/>" rel="stylesheet">
    <style type="text/css">
    	*{
    		font-family: 微软雅黑;
    	}
    	.img
    	{
    		-moz-box-shadow: 6px 6px 10px #BCBAB9;
			-webkit-box-shadow: 6px 6px 10px #BCBAB9;
			box-shadow: 6px 6px 6px #BCBAB9;
			display: block;
			width: 98px;
			height:128px;
			/* border:1px solid black; */
    	}
    	#head
     	{
     		font-size:15px;
     		margin-bottom: 15px;
     		/* border:1px solid black; */
     	}
     	#body
     	{
     		width:800px;
     		height:440px;
     		margin: 0 auto;
     	}
     	#body-top
     	{
     		height:100px;
     		width:800px;
     	}
     	#body-head
     	{
     		height:40px;
     		width:800px;
     		border:1px solid #E0DBD7;
     		background-color: #F6F5F1;
     	}
     	#body-body
     	{
     		height:300px;
     		width:800px;
     		border-bottom:1px solid #E0DBD7;
     		border-left:1px solid #E0DBD7;
     		border-right:1px solid #E0DBD7;
     		background-color: white;
     	}
     	.shadowx
     	{
     		-moz-box-shadow: 6px 6px 10px #BCBAB9;
			-webkit-box-shadow: 6px 6px 10px #BCBAB9;
			box-shadow: 6px 6px 6px #BCBAB9;
     	}
     	a:HOVER
     	{
     		color:#FA7A20;
     	}
     	.onebook
     	{
     		display: inline-block;
     		width:98px;
     		margin: 0 auto;
     		margin-right:20px;
     		margin-left: 20px;
     		margin-top: 30px;
     	}
     	#books
     	{
     		width:711px;
     		margin: 0 auto;
     	}
    </style>
  </head>
  
  <body>
	<%-- ${requestScope.recognizedBooks} --%>
	<div id="head">当前位置：
	  	<a href="<c:url value='/bookServlet'/>" class="changecolor">首页</a>
	  	>
	  	<span style="color:#A9A4A1">购物车</span>
  	</div>
  	<div id="body">
  		<div id=body-top>
  			<div style="line-height: 100px;text-align: left;font-weight: bold;color:#35322D;font-size: 30px;display: inline-block;">您购物车中没有图书</div>
  			<a style="text-decoration:none;font-size:14px;line-height:33px;display: inline-block;width:105px;height:33px;border:1px solid #BFB8A8;text-align: center;margin-left: 400px;" class="circleborder" class="shadowx" class="changecolorx" href="<c:url value='/bookServlet'/>">去书城逛逛</a>
  		</div>
  		<div id="body-head">
  			<span style="text-align: left;line-height: 40px;color:#7F7F7F;display: inline-block;margin-left: 15px;font-size: 15px;">您可能对这些书感兴趣</span>
  			<a style="text-align: right;display: inline-block;line-height: 40px;float:right;margin-right: 5px;font-size: 15px;text-decoration: none;" href="<c:url value='/bookServlet'/>">更多>></a>
  		</div>
  		<div id="body-body">
  			<div id="books">
  			<c:forEach items="${requestScope.recognizedBooks}" var="book">
  				<div class="onebook"><!-- height128px width98px -->
  					<a href="<c:url value='/bookServlet?cmd=showSingleBook&bookid=${book.id}'/>">
  						<img class="img" alt="${book.name}" src="<c:url value='/imgs/${book.img}'/>">
  					</a>
  					<a style="text-decoration: none;" href="<c:url value='/bookServlet?cmd=showSingleBook&bookid=${book.id}'/>">
  						<span style="font-size: 10px;display: block;height:25px;margin-top: 10px;">${book.name}</span><br/>
  					</a>
  					<span style="line-height:10px;/* border:1px solid black; */font-size: 10px;display: block;height:10px;margin-top: 5px;color:#838D8D;">${book.auth}</span><br/>
  					<span style="height:7px;margin-top: 2px;/* border:1px solid black; */line-height: 7px;">
	  					<c:choose>
								<c:when test="${book.rebate==1}">
									<span style="font-size: 7px;font-weight: bold;">￥${book.price}</span>
								</c:when>
								<c:otherwise>
									<span style="font-size: 7px;font-weight: bold;">￥<fmt:formatNumber value="${book.price*book.rebate}" pattern="#.00"></fmt:formatNumber></span>
									<span style="text-decoration: line-through;color:#999999;font-size: 5px;">￥${book.price}</span>
								</c:otherwise>
						</c:choose>
					</span>
  				</div>
  			</c:forEach>
  			</div>
  		</div>
  	</div>
  </body>
</html>
