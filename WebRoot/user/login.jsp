<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 普通用户登录页面 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <link type="text/css" href="<c:url value='/css/pub/pub.css'/>" rel="stylesheet">
    <script type="text/javascript" src="<c:url value='/js/pub/pub.js'/>"></script>
    <style type="text/css">
    	*
    	{
    		font-family: 微软雅黑;
    	}
    	#title
    	{
    		font-size:26px;
    		color:#000;
    		line-height: 50px;
    		text-align: center;
    		margin: 0 auto;
    		font-weight: bold;
    	}
    	#secondtitle
    	{
    		color:#8d8d8d;
    		font-size: 14px;
    		line-height: 1.5;
    		text-align: center;
    	}
    	#inputwra
    	{
    		width:324px;
    		height:91px;
    		border:1px solid #E6E6E6;
    		border-radius:10px;
			-webkit-border-radius:10px;
			-moz-border-radius:10px; 
			margin: 0 auto;
			margin-top: 30px;
			background-color: white;
    	}
    	.wra
    	{
    		width:324px;
    		height:44px;
    	}
    	.input
    	{
    		color:#C2C2C2;
    		font-size: 14px;
    		height:44px;
    		width:323px;
    		border:0px;
    		border-color: red;
    	}
    	#login
    	{
    		display:block;
    		width:323px;
    		height:44px;
    		color:white;
    		background-color: #FF7A4D;
    		text-align: center;
    		margin: 0 auto;
    		line-height: 44px;	
    		border-radius:5px;
			-webkit-border-radius:5px;
			-moz-border-radius:5px; 
			margin: 0 auto;
			font-size:17px;
			border:0px;
			margin-top: 15px;
    	}
    	#register
    	{
    		width:323px;
    		height:44px;
    		line-height: 44px;
    		text-align: right;
    		color:gray;
    		margin: 0 auto;
    		font-size: 12px;
    	}
    </style>
    <script type="text/javascript">
    	function tijiao()
    	{
    		var username=document.getElementsByName("username")[0].value.trim();
    		var password=document.getElementsByName("password")[0].value.trim();
    		if(username=='邮箱/用户名'||password=='密码')
   			{
   				alert("用户名或者密码不能为空！");
   				return;
   			}
    		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
     		var url="<c:url value='/userServlet?cmd=loginCheck&username="+username+"&password="+password+"'/>";
     		window.location.href=url;
     		/* request.open("POST", url, true);//异步请求处理
     		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
     		request.send();
     		request.onreadystatechange=function()
     		{
     			//alert(request.readyState);
     			if(request.readyState==4)
     			{
     				if(request.status==200)//如果请求成功，即登录验证成功！
   					{
   						alert("登陆成功！");
   						return;
   					}
     				if(request.status==404)
     				{
     					alert("用户名或者密码错误！");
     					return;
     				}
     			}
     		}; */
    	}
    </script>
  </head>
  
  <body>
	<c:if test="${! empty sessionScope.msg}">
	  	<script type="text/javascript">
	  		alert("${sessionScope.msg}");
	  	</script>
	  	<c:remove var="msg" scope="session"/>
	</c:if>
  	<div id="title">
  		一个账号，玩转所有叮叮服务！
  	</div>
  	<div id="secondtitle">
  		叮叮阅读，叮叮网，叮叮聊天
  	</div>
  	<form action="<c:url value='/userServlet?cmd=loginCheck'/>" method="POST">
	  	<div id="inputwra" class="">
	  		<div class="wra" style="border-bottom: 1px solid #E6E6E6;">
	  			<input name="username" class="input"  value="  邮箱/用户名" type="text" onfocus="this.style.borderColor='#E6E6E6';if(this.value=='  邮箱/用户名') this.value='';" onblur="if(this.value=='') this.value='  邮箱/用户名';">
	  		</div>
	  		<div class="wra">
	  			<input name="password" class="input"  value="  密码" type="text" onfocus="this.style.borderColor='#E6E6E6';this.type='password';if(this.value=='  密码') this.value='';" onblur="if(this.value==''){ this.value='  密码';this.type='text';}else{this.type='password';}">
	  		</div>
	  	</div>
  		<input onclick="tijiao()" onmouseover="this.style.cursor='hand'" id="login" value="立即登录" type="button">
  	</form>
  	<div id="register">
  		没有账号？&nbsp;&nbsp;<a class="changecolor" href="<c:url value='/user/register.jsp'/>">立即注册</a>
  	</div>
  	
  </body>
</html>
