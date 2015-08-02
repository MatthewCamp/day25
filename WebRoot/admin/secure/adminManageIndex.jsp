<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 显示所有的可管理目录 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
  </head>
  <frameset rows="100px,*" border="1px">
  	<frame scrolling="auto" noresize="noresize" src="<c:url value='/admin/secure/top.jsp'/>">
  	<frameset cols="200px,*">
  		<frame src="<c:url value='/admin/adminServlet?cmd=showMenus'/>" scrolling="auto" noresize="noresize">
  		<frame src="<c:url value='/admin/secure/roleManageServlet'/>" scrolling="auto" noresize="noresize" name="right">
  	</frameset>
  </frameset>
</html>
