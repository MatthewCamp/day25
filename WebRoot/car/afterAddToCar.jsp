<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 加入购物车之后的提示页面 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <link type="text/css" href="<c:url value='/css/pub/pub.css'/>" rel="stylesheet">
    <style type="text/css">
    	*
    	{
    		font-family: 微软雅黑;
    	}
    	#div
    	{
    		width:635px;
    		height:44px;
    		margin:0 auto;
    		margin-top: 100px;
    	}
    	#div1
    	{
    		display: inline-block;
    		color:#7ABD54;
    		font-size: 23px;
    		/* font-weight: bold; */
    		height:35px;
    	}
    	#div2
    	{
    		display: inline-block;
    		width:140px;
    		height:35px;
    		font-size: 15px;
    		font-weight:bold;
    		color:white;
    		background-color:#EF4E51;
    		line-height: 35px;
    		text-align: center;
    		text-decoration: none;
    	}
    	#div3
    	{
    		display: inline-block;
    		font-size:14px;
    		color:#666666;
    		height:35px;
    		
    	}
    	#div4
    	{
    		display: inline-block;
    		font-size:14px;
    		color:#005EA7;
    		text-decoration: none;
    		height:35px;
    	}
    	#div4:hover
    	{
    		color:#FA7A20;
    	}
    </style>
  </head>
  
  <body>
  	<div id="div">
		<div id="div1"><img src="<c:url value='/otherimages/duihao.png'/>">
		商品已经成功加入购物车！</div>
		<a class="circleborder" id="div2" href="<c:url value='/buyServlet?cmd=showCar'/>">去购物车结算</a>
		<div id="div3">您还可以</div>
		<a id="div4" href="<c:url value='/bookServlet'/>">继续购物</a>
	</div>
  </body>
</html>
