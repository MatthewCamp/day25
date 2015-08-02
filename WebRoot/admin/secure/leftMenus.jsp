<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 框架中的左侧导航栏，显示该管理员的访问权限 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <style type="text/css">
    	a {
			display: block;
			height:20px;
			width:100px;
			margin: 0 auto;
			font-size: 20px;
			font-family: 微软雅黑;
			text-align: center;
			text-decoration: none;
			margin-bottom: 8px;
		}
    </style>
  </head>
  
  <body>
	<%-- ${requestScope.menus} --%>
	<br/>
	<br/>
	<c:forEach items="${requestScope.menus}" var="menu">
		<a href="<c:url value='${menu.url}'/>" target="right">${menu.name}</a><br/>
	</c:forEach>
  </body>
</html>
