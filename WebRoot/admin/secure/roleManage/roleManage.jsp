<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 角色管理页面，提供对角色的增删查改操作 -->
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
    	#add
    	{
    		width:800px;
    		height:40px;
    		margin: 0 auto;
    		margin-top: 20px;
    	}
    	#add input
    	{
    		font-size: 20px;
    		display: bloack;
    		float:left;
    		margin-top: 20px;
    	}
    	table
    	{
    		border:1px solid black;
    		width:800px;
    		border-collapse: collapse;
    		margin: 0 auto;
    		margin-top: 20px;
    	}
    	td
    	{
    		border:1px solid black;
    		text-align: center;
    		height:40px;
    		font-size:18px;
    		line-height: 20px;
    	}
    	.button
    	{
    		text-align: center;
    		width:50px;
    		height:30px;
    		font-size: 15px;
    		line-height: 30px;
    	}
    	.text
    	{
    		width:250px;
    		height:30px;
    	}
      </style>
      <script type="text/javascript" src="<c:url value='/js/pub/pub.js'/>"></script>
      <script type="text/javascript">
      	function modifyx(x)
      	{
      		
      		if(x.value.trim()=='修改')
      		{
      			var tr=x.parentNode.parentNode;
          		//var roleid=tr.getElementsByTagName("TD")[0].innerHTML.trim();
          		tr.getElementsByTagName("TD")[1].getElementsByTagName("input")[0].disabled=false;
          		tr.getElementsByTagName("TD")[2].getElementsByTagName("input")[0].disabled=false;
          		x.value="提交";
      		}
      		else
      		{
      			var tr=x.parentNode.parentNode;
      			var request;
         		if(window.XMLHttpRequest){
         			request= new XMLHttpRequest();
         		}else{
         			request= new ActiveXObject("Microsoft.XMLHttp");
         		}
          		var roleid=tr.getElementsByTagName("TD")[0].innerHTML.trim();
          		var rolename=tr.getElementsByTagName("TD")[1].getElementsByTagName("input")[0].value.trim();
          		var roledescc=tr.getElementsByTagName("TD")[2].getElementsByTagName("input")[0].value.trim();
      			var url="<c:url value='/admin/secure/roleManageServlet?cmd=modifyRole&id="+roleid+"&name="+rolename+"&descc="+roledescc+"'/>";
         		request.open("post", url, true);//异步请求处理
         		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
         		request.send();
         		request.onreadystatechange=function()
         		{
         			if(request.readyState==4)
        			{
        				if(request.status==200)//如果响应成功，则跳转到空购物车的页面中
        				{
			          		tr.getElementsByTagName("TD")[1].getElementsByTagName("input")[0].disabled=true;
			          		tr.getElementsByTagName("TD")[2].getElementsByTagName("input")[0].disabled=true;
			      			x.value="修改";	
        				}
        				else
       					{
       						alert("更新失败！");
       						tr.getElementsByTagName("TD")[1].getElementsByTagName("input")[0].disabled=true;
			          		tr.getElementsByTagName("TD")[2].getElementsByTagName("input")[0].disabled=true;
			      			x.value="修改";
       					}
        			}
         		};
      		}
     		
      	}
      	function deletex(x)
      	{
      		var td=x.parentNode;
      		var tr=td.parentNode;
      		var table=tr.parentNode;
      		if(tr.getElementsByTagName("TD")[0].getElementsByTagName("input")[0]!=null)
   			{
      			table.removeChild(tr);
      			return;
   			}
      		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
      		var roleid=tr.getElementsByTagName("TD")[0].innerHTML.trim();
      		var url="<c:url value='/admin/secure/roleManageServlet?cmd=deleteRole&id="+roleid+"'/>";
      		request.open("post", url, true);//异步请求处理
     		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
     		request.send();
     		request.onreadystatechange=function()
     		{
     			if(request.readyState==4)
    			{
    				if(request.status==200)
    				{
    					table.removeChild(tr);
    				}
    				else
   					{
   						alert("删除失败！");
   					}
    			}
     		};
      	}
      	function _add()
      	{
      		var table=document.getElementsByTagName("table")[0];
      		var newrole="<tr>"+
    			"<td><input class=\"text\" type=\"text\" style=\"width: 50px;\"></td>"+
    			"<td><input class=\"text\" type=\"text\"></td>"+
    			"<td><input class=\"text\" type=\"text\"></td>"+
    			"<td><input class=\"button\" type=\"button\" value=\"增加\" onclick=\"_addOne(this)\"></td>"+
    			"<td><input class=\"button\" type=\"button\" value=\"删除\" onclick=\"deletex(this)\"></td>"+
    		"</tr>";
    		var y=table.innerHTML;
    		table.innerHTML=y+newrole;
      	}
      	function _addOne(x)
      	{
      		var td=x.parentNode;
      		var tr=x.parentNode.parentNode;
  			var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
      		var roleid=tr.getElementsByTagName("TD")[0].getElementsByTagName("input")[0].value.trim();
      		var rolename=tr.getElementsByTagName("TD")[1].getElementsByTagName("input")[0].value.trim();
      		var roledescc=tr.getElementsByTagName("TD")[2].getElementsByTagName("input")[0].value.trim();
  			var url="<c:url value='/admin/secure/roleManageServlet?cmd=addNewRole&id="+roleid+"&name="+rolename+"&descc="+roledescc+"'/>";
     		request.open("post", url, true);//异步请求处理
     		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
     		request.send();
     		request.onreadystatechange=function()
     		{
     			if(request.readyState==4)
    			{
    				if(request.status==200)//如果响应成功，则跳转到空购物车的页面中
    				{
    					alert("添加新角色成功！");
    					var newhtml="<input class='button' type='button' value='修改' onclick='modifyx(this)'>";
    		      		td.innerHTML=newhtml;
    		      		tr.getElementsByTagName("TD")[0].innerHTML=roleid;
    		      		tr.getElementsByTagName("TD")[1].getElementsByTagName("input")[0].disabled=true;
    		      		tr.getElementsByTagName("TD")[2].getElementsByTagName("input")[0].disabled=true;
    				}
    				else
   					{
   						alert("添加新角色失败！");
   					}
    			}
     		};      		
      	}
      </script>
  </head>
  
  <body>
  	<div id="add">
  		<input name="add" type="button" onclick="_add()" value="增加一个新角色">
  	</div>
	<table>
		<thead>
			<tr style="font-weight: bold;">
				<td>角色Id</td>
				<td>角色名称</td>
				<td>角色描述</td>
				<td>修改</td>
				<td>删除</td>
			</tr>
		</thead>
		<c:forEach items="${requestScope.roles}" var="role">
			<tr>
				<td>${role.id}</td>
				<td><input disabled="disabled" class="text" type="text" value="${role.name}"></td>
				<td><input disabled="disabled" class="text" type="text" value="${role.descc}"></td>
				<td><input class="button" type="button" value="修改" onclick="modifyx(this)"></td>
				<td><input class="button" type="button" value="删除" onclick="deletex(this)"></td>
			</tr>
		</c:forEach>
			<!-- <tr>
				<td><input class="text" type="text" style="width: 50px;"></td>
    			<td><input class="text" type="text"></td>
    			<td><input class="text" type="text"></td>
    			<td><input class="button" type="button" value="增加" onclick="_addOne(this)"></td>
    			<td><input class="button" type="button" value="删除" onclick="deletex(this)"></td>
    		</tr> -->
	</table>
  </body>
</html>
