<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--管理员登陆页面 --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <script type="text/javascript" src="<c:url value='/js/pub/pub.js'/>"></script>
    <script type="text/javascript">
    	function login(username,password)
    	{
    		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
     		var url="<c:url value='/admin/adminServlet?cmd=login&name="+username+"&password="+password+"'/>";
     		request.open("POST", url, true);//异步请求处理
     		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
     		request.send();
     		request.onreadystatechange=function()
     		{
     			//alert(request.readyState);
     			if(request.readyState==4)
     			{
      				//alert(request.status);
     				if(request.status==200)
  					{
     					changeCheckCode();//防止使用浏览器的回退功能。
     		    		alert("登录成功！");//登陆成功之后跳转到管理界面。
     		    		//跳转到的管理页面必须展现出该管理员应该具备的所有权限，体现出来的就是目录
     		    		window.location.href="<c:url value='/admin/adminServlet'/>";//使用默认方法显示主界面
  					}
     				else
     				{
     					changeCheckCode();
     					alert("用户名或者密码错误！");
     					return;
     				}
     			}
     		};   
    	}
    	function tijiao()
    	{
    		var username=document.getElementsByName("name")[0].value.trim();
    		var password=document.getElementsByName("password")[0].value.trim();
    		if(username==''||password=='')
   			{
   				alert("用户名或者密码不能为空！");
   				return;
   			}
    		var checkcode=document.getElementsByName("admincheckcode")[0].value.trim();
    		if(checkcode=='')
   			{
   				alert("验证码不能为空！");
   				return;
   			}
    		//验证验证码是否匹配
    		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
     		var url="<c:url value='/admin/adminServlet?cmd=checkCheckCode&checkcode="+checkcode+"'/>";
     		request.open("POST", url, true);//异步请求处理
     		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
     		request.send();
     		request.onreadystatechange=function()
     		{
     			//alert(request.readyState);
     			if(request.readyState==4)
     			{
      				//alert(request.status);
     				if(request.status==200)
  					{
     					changeCheckCode();
     					//去除多余的空格
     		    		login(username,password);//如果连验证码都没有问题了，就尝试登陆。
  					}
     				else//验证码错误的情况
     				{
     					login(username, password);
     					/* changeCheckCode();
     					alert("验证码错误！");//暂且先注释掉，发布项目之后再改回来
     					return; */
     				}
     			}
     		};    		
    	}
    	/*更换验证码的方法*/
    	function changeCheckCode()
    	{
    		var checkcode=document.getElementById("checkcode");
    		checkcode.src="<c:url value='/admincheckcode'/>"+"?"+Math.random();
    	}
    </script>
    <style type="text/css">
    	table
    	{
    		border:1px solid black;
    		width:360px;
    		height:100px;
    		border-collapse: collapse;
    		margin: 0 auto;
    		margin-top: 100px;
    	}
    	td
    	{
    		border:1px solid black;
    		text-align: center;
    		height:30px;
    	}
    	input
    	{
    		display: inline-block;
    		float: left;
    		height:30px;
    		width:200px;
    	}
    	
    </style>
  </head>
  
  <body>
	<form method="post" action="<c:url value='/admin/secure/adminServlet?cmd=login'/>">
		<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>验证码：</td>
					<td>
						<input type="text" name="admincheckcode">
						<img onclick="changeCheckCode()" id="checkcode" alt="单击此处进行切换" src="<c:url value='/admincheckcode'/>" width="80" height="30">
					</td>
				</tr> 
				<tr>
					<td colspan="2">
						<input onclick="tijiao()" type="button" value="登陆" style="float:none;width:80px;height:25px;">
					</td>
				</tr>
		</table>
	</form>
  </body>
</html>
