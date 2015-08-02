<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <link type="text/css" href="<c:url value='/css/pub/pub.css'/>" rel="stylesheet">
    <script type="text/javascript">
    </script>
    <style type="text/css">
    	*{
			font-family: 微软雅黑;
    	} 
    	body{
    		background-color: #FBFAF8;
    	}
    	
    	/*头部css*/
		#wrapper
		{
			width:1200px;
			height:1366px;
			margin: 0 auto;
			/* border:1px solid black; */
		}
		#header
		{
			width:1200px;
			height: 55px;
			color:#555555;
			/* border: 1px solid black; */
		}
		.title
		{
			line-height:44px;
			text-align:center;
			margin: 0 auto;	
			font-size:20px;
			color:white;
		}
		.top
		{
			font-size:10px;
			display:inline;
			line-height: 55px; 
			text-align: right;   	
		}
    	/*下面是主体部分*/
    	#body
    	{
    		margin-top: 40px;
    	}
    	#nav
    	{
    		width:240px;
    		border-right: 1px solid;
    		border-right-color: #E2DCD7;
    		display: inline-block;
    		float:left;
    	}
    	#books
    	{
    		float:left;
    		margin-left:30px;
    		display: inline-block;
    		width:900px;
    	}
    	.classes
    	{
    		list-style-type: none;
    		display: block;
    	}
    	.classes li
    	{
    		display: block;
    		/* border:1px solid black; */
    		line-height: 35px;
    		border-bottom-width: 1px;
    		border-bottom-style:dashed;
    		border-bottom-color: #E2DCD7;
    		margin-right:30px;
    	}
    	.nav-left
    	{
    		display: inline-block;
    		margin-left: 40px;
    		font-size:15px;
    		width:153px;
    	}
    	.nav-right
    	{
    		display:inline-block;
    		float:right;
    		color:#A99A8F;
    		font-size:10px;
/*     		width:80px;
 */    	}
    	.nav-left:HOVER
    	{
    		color:#FA7A20;
    	}
    </style>
  </head>
  
  <body>
  	<div id="wrapper">
  		<div id="header">
  			<span style="display:inline-block;float: left;line-height: 55px;font-size: 25px;font-weight: bold;margin-left: 15px;">叮叮阅读</span>
  			<div style="float: right;height:55px;margin-right: 15px;">
  				<c:choose>
					<c:when test="${empty sessionScope.user}">
		  				<span class="top">
			  				<a href="<c:url value='/user/login.jsp'/>" target="books" class="changecolor">登陆</a>
			  				&nbsp;/&nbsp;
			  				<a target="books" href="<c:url value='/user/register.jsp'/>" class="changecolor">注册</a>
			  				&nbsp;&nbsp;|&nbsp;
		  				</span>
  					</c:when>
  					<c:otherwise>
		  				<span class="top">欢迎你，${sessionScope.user.name}&nbsp;|&nbsp;
		  				<a href="#" class="changecolor">管理订单</a>&nbsp;|&nbsp;</span>
  					</c:otherwise>
  				</c:choose>
	  			<span class="top">
	  			<a target="books" href="<c:url value='/buyServlet?cmd=showCar'/>" class="changecolor">购物车</a></span>
	  			<span class="top">|
	  			<a href="<c:url value='/userServlet?cmd=exit'/>" class="changecolor">&nbsp;退出</a></span>
  			</div>
  		</div>
  		<hr/>
		<!-- 主体部分 -->
		<div id="body">
			<!-- 左侧导航栏 -->
			<div id="nav">
				<ul class="classes">
						<a href="<c:url value='/bookServlet'/>" target="books">
							<li>
								<div class="nav-left" style="margin-left: 20px;">全部图书</div>
								<div class="nav-right">${booksamount}</div>
							</li>
						</a>
					<c:forEach items="${allbookstypes}" var="booktype">
						<a href="<c:url value='/bookServlet?id=${booktype.id}'/>" target="books">
							<li>
								<div class="nav-left">${booktype.name}</div>
								<div class="nav-right">${booktype.amount}</div>
							</li>
						</a>
					</c:forEach>
				</ul>
			</div>
			<!-- 右侧图书列表 -->
			<div id="books">
				<c:choose>
					<c:when test="${empty sessionScope.normalrequesturi}">
						<iframe scrolling="no" frameborder="0" name="books" src='<c:url value="/bookServlet"/>' height="100%" width="100%" />
					</c:when>
					<c:otherwise>
						<iframe scrolling="no" frameborder="0" name="books" src="http://localhost:8080${sessionScope.normalrequesturi}" height="100%" width="100%" />
						<c:remove var="normalrequesturi" scope="session"/><!-- 使用完之后立即删除 -->
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
  </body>
</html>
