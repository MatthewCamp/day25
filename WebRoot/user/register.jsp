<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    	#head
    	{
    		width:400px;
    		height:80px;
    		line-height: 50px;
    		text-align: left;
    		font-size: 20px;
    		font-weight: bold;
    		color:#FF6F3D;
    	}
    	#body
    	{
    		background-color: white;
    		border:1px solid #E6E6E6;
    		border-radius:10px;
			-webkit-border-radius:10px;
			-moz-border-radius:10px; 
    	}
    	#body-head
    	{
    		height:100px;
    		width:800px;
    		margin: 0 auto;
    		text-align: center;
    		line-height: 100px;
    		font-size: 20px;
    		font-weight: bold;
    		color:#000;
    		border-bottom: 1px solid #E6E6E6;
    	}
    	#body-body
    	{
    		width:800px;
    		height:350px;
    		margin: 0 auto;
    	}
    	.inputdiv
    	{
    		width:500px;
    		height:42px;
    		margin-left: 228px;
    		margin-top: 10px;
    		margin-bottom: 10px;
    	}
    	.input
    	{
    		display:inline-block;
    		/* margin: 0 auto; */
    		/* margin-left:228px; */
    		float:left;
    		width:332px;
    		height:42px;
    		/* margin-top: 10px;
    		margin-bottom: 10px; */
    		border:1px solid #E6E6E6;
    		text-align: left;
    		border-radius:5px;
			-webkit-border-radius:5px;
			-moz-border-radius:5px; 
			color:#9D9D9D;
    	}
    	#checkcode
    	{
    		display: block;
    		/* margin: 0 auto; */
    		margin-left:228px;
    		width:450px;
    		height:42px;
    		margin-top: 10px;
    		margin-bottom: 10px;
    		text-align: left;
    	}
    	#checkcode-text
    	{
    		width:190px;
    		height:42px;
    		display: inline-block;
    		text-align: left;
    		border:1px solid #E6E6E6;
    		float:left;
    		border-radius:5px;
			-webkit-border-radius:5px;
			-moz-border-radius:5px; 
			color:#9D9D9D;
    	}
    	#checkcode-img
    	{
    		width:130px;
    		height:42px;
    		display: inline-block;
    		/* border:1px solid #E6E6E6; */
    		float:left;
    	}
    	
    	#register
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
			/* margin: 0 auto; */
			margin-left:228px;
			font-size:17px;
			border:0px;
			margin-top: 15px;
    	}
    </style>
    <script type="text/javascript">
    	var userNameError=true;
    	var emailError=true;
    	var checkcodeError=true;
    	var passwordError=true;
    	function changeCheckCode(img)
    	{
    		/* alert(img); */
    		img.src="<c:url value='/checkCode?'/>"+Math.random();
    	}
    	function removeError(name)
    	{
    		var node=document.getElementById(name);
    		var aim=node.getElementsByTagName("span")[0];
    		if(aim!=null)
			aim.parentNode.removeChild(aim);
    	}
    	function checkUserHasExists(aim)
    	{
    		if(aim.value.trim()=='')
   			{
   				aim.value='  请输入用户名';
   				removeError("name");
   				return;
   			}
    		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
     		var username=document.getElementsByName("name")[0].value;
     		var url="<c:url value='/userServlet?cmd=checkUserHasExists&username='/>"+username;
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
     					userNameError=false;
     					removeError("name");
  						return true;
  					}
     				else
     				{
     					userNameError=false;
     					removeError("name");
     					var info="<span class='error' style='color:red;line-height:42px;display:inline-block;font-size:12px;'>&nbsp;&nbsp;用户名已经存在！</span>";
     					var checkcode=document.getElementById("name").innerHTML;
     					checkcode+=info;
     					document.getElementById("name").innerHTML=checkcode;
     					return false;
     				}
     			}
     		};
    	}
    	//验证两次填写的密码是否匹配
    	function checkPasswordIsMatch(aim)
    	{
    		if(aim.value.trim()=='') 
    		{
    			removeError("repeatpassword");
    			aim.value='  请再次输入密码';
    			return;
    		}
   			var p1=document.getElementsByName("password")[0].value.trim();
   			var p2=document.getElementsByName("repeatpassword")[0].value.trim();
   			if(p1==p2)
			{
				//alert("两次输入的密码匹配");
				passwordError=false;
				removeError("repeatpassword");
				return true;
			}
   			else
			{
   				passwordError=true;
   				removeError("repeatpassword");
   				var info="<span class='error' style='color:red;line-height:42px;display:inline-block;font-size:12px;'>&nbsp;&nbsp;两次输入的密码不一致！</span>";
				var checkcode=document.getElementById("repeatpassword").innerHTML;
				checkcode+=info;
				document.getElementById("repeatpassword").innerHTML=checkcode;
				return false;
			}
    	}
    	function checkEmailHasExists(aim)
    	{
    		if(aim.value.trim()=='') 
    		{
    			removeError("email");
    			aim.value='  请输入邮箱';
    			return;
    		}
    		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
     		var email=document.getElementsByName("email")[0].value;
     		var url="<c:url value='/userServlet?cmd=checkEmailHasExists&email='/>"+email;
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
  						//alert("邮箱名不存在！");
  						emailError=false;
  						removeError("email");
  						return true;
  					}
     				else
     				{
     					emailError=true;
     					removeError("email");
     					var info="<span class='error' style='color:red;line-height:42px;display:inline-block;font-size:12px;'>&nbsp;&nbsp;邮箱名已经存在！</span>";
     					var checkcode=document.getElementById("email").innerHTML;
     					checkcode+=info;
     					document.getElementById("email").innerHTML=checkcode;
     					return false;
     				}
     			}
     		};
    	}
    	//验证验证码是否匹配。
    	function checkCheckCodeIsMatch(aim)
    	{
    		if(aim.value.trim()=='') 
    		{
    			removeError("checkcode");
    			aim.value='  请输入验证码';
    			return;
    		}
    		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
     		var checkcode=document.getElementById("checkcode-text").value;
     		//alert(checkcode);
     		var url="<c:url value='/userServlet?cmd=compareCheckCode&checkcode='/>"+checkcode;
     		//alert(url);
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
     					checkcodeError=false;
     					removeError("checkcode");
  						return true;
  					}
     				else
     				{
     					checkcodeError=true;
     					removeError("checkcode");
     					var info="<span class='error' style='color:red;line-height:42px;display:inline-block;font-size:12px;'>&nbsp;&nbsp;验证码输入错误！</span>";
     					var checkcode=document.getElementById("checkcode").innerHTML;
     					checkcode+=info;
     					document.getElementById("checkcode").innerHTML=checkcode;
     					return false;
     				}
     			}
     		};
    	}
    	//提交之前的校验。
    	function tijiao()
    	{
    		if(!userNameError&&!emailError&&!checkcodeError&&!passwordError)
    		{
    			document.forms[0].submit(); 
    		}
    		else
   			{
   				alert("信息不完整或者信息有误！");
   			}
    	}
    </script>
  </head>
  
  <body>
	<div id="head">
		叮叮账号
	</div>
	
	<div id="body">
		<div id="body-head">
			注册叮叮账号
		</div>
		
		<div id="body-body">
			<form action="<c:url value="/userServlet?cmd=register"/>" method="post">
				<div id="name" class="inputdiv">
					<input name="name" class="input" value="  请输入用户名" type="text" onfocus="if(this.value=='  请输入用户名') this.value='';"  onblur="checkUserHasExists(this)">
				</div>
				<div id="password" class="inputdiv">
					<input name="password" class="input" value="  请输入密码" type="text" onfocus="if(this.value=='  请输入密码') this.value='';" onblur="if(this.value.trim()=='')this.value='  请输入密码';">
				</div>
				<div id="repeatpassword" class="inputdiv">
					<input name="repeatpassword" class="input" value="  请再次输入密码" type="text" onfocus="if(this.value=='  请再次输入密码') this.value='';" onblur="checkPasswordIsMatch(this)">
				</div>
				<div id="email" class="inputdiv">
					<input name="email" class="input" value="  请输入邮箱" type="text" onfocus="if(this.value=='  请输入邮箱') this.value='';" onblur="checkEmailHasExists(this)">
				</div>
				<div id="checkcode">
					<input id="checkcode-text" value="  请输入验证码" type="text" onfocus="this.style.borderColor='#E6E6E6';if(this.value=='  请输入验证码') this.value='';" onblur="checkCheckCodeIsMatch(this)">
					<img onmouseover="this.style.cursor='hand'" onclick="changeCheckCode(this)"  id="checkcode-img" src="<c:url value='/checkCode'/>" width=130px height=42px>
				</div>
				<input onclick="tijiao()" onmouseover="this.style.cursor='hand'"  id="register" value="立即注册" type="button">
			</form>
		</div>	
	</div>
  </body>
</html>
