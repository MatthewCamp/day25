<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.kdyzm.com/jsp/jstl/bookstore/myfn" prefix="myfn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
     	#head
     	{
     		font-size:15px;
     		margin-bottom: 15px;
     		/* border:1px solid black; */
     	}
     	#body
     	{
     		margin-top: 35px;
     	}
     	#img
     	{
     		display: inline-block;
     		width:181px;
     		height:243px;
     		float:left;
     	/* 	border:1px solid black; */
     		margin-left: 120px;
     	}
     	#bookinfo
     	{
     		height:243px;
     		display: inline-block;
     		float:left;
     	/* 	border:1px solid black;	 */
     		margin-left: 30px;
     		margin-top: 0px;
     	}
     	.img{
    		-moz-box-shadow: 6px 6px 10px #BCBAB9;
    		-webkit-box-shadow: 6px 6px 10px #BCBAB9;
    		box-shadow: 6px 6px 6px #BCBAB9;
    		/* border:1px solid black; */
    	}
     </style>
     <script type="text/javascript">
     function _add(bookid)
 	{
 		var request;
 		if(window.XMLHttpRequest){
 			request= new XMLHttpRequest();
 		}else{
 			request= new ActiveXObject("Microsoft.XMLHttp");
 		}
 		var url="<c:url value='/buyServlet?bookid='/>"+bookid;
 		/* alert(url); */
 		request.open("post", url, true);//异步请求处理
 		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
 		request.send();
 		request.onreadystatechange=function()
 		{
 			if(request.readyState==4)
				{
					if(request.status==200)
					{
						/* var a=document.getElementById("castlogo"+bookid);
						a.innerHTML="已经加入购物车";
						a.style.width="130";
						a.style.backgroundColor="red";
						a.onclick="";
						var v=a.parentNode;
						var parent=v.parentNode;
						parent.replaceChild(a, v); */
						window.location.href="<c:url value='/car/afterAddToCar.jsp'/>";
					}
				}
 		};
 	}
     </script>
  </head>
  
  <body>
  	<div id="head">当前位置：
	  	<a href="<c:url value='/bookServlet'/>" class="changecolor">首页</a>
	  	>
		<c:choose>
			<c:when test="${empty sessionScope.booktype}">
			  	<a href="<c:url value='/bookServlet'/>" class="changecolor">全部图书</a>
			</c:when>
			<c:otherwise>
			  	<a href="<c:url value='/bookServlet'/>" class="changecolor">全部图书</a>>
			  	<a href="<c:url value='/bookServlet?id=${sessionScope.booktypeid}'/>" class="changecolor">${sessionScope.booktype}</a>
			</c:otherwise>
		</c:choose>
		>
	  	<span style="color:#A9A4A1">${requestScope.book.name}</span>
	  	<c:remove var="booktype" scope="session"/>
	  	<c:remove var="booktypid" scope="session"/>
  	</div>
  	<div id="body">
  		<div id="body-head" style="display: block;/* border:1px solid blue; */height:245px;">
	  		<div id="img">
	  			<img class="img" alt="${book.name}" src="<c:url value='/imgs/${book.img}'/>" width="181px" height=243px;>
	  		</div>
	  		<div id="bookinfo">
	  			<div>
		  			<span style="color: #2E3638;font-weight: bold;font-size: 20px;display: block;line-height: 45px;">${book.name}</span>
		  			<span style="font-size:14px;line-height: 20px;">
			  			作者：${book.auth}<br/>
			  			出版设：${book.publisher}<br/>
			  			出版时间：${book.publishdate }<br/>
		  			</span>
	  			</div>
	  			<div style="margin-left: 5px;margin-top: 47px;">
		  			<c:choose>
						<c:when test="${book.rebate==1}">
							<span style="font-size: 7px;font-weight: bold;">￥${book.price}</span>
						</c:when>
						<c:otherwise>
							<span style="font-size: 7px;font-weight: bold;">￥<fmt:formatNumber value="${book.price*book.rebate}" pattern="#.00"></fmt:formatNumber></span>
							&nbsp;|&nbsp;
							<span style="text-decoration: line-through;color:#999999;font-size: 5px;">￥${book.price}</span>
							&nbsp;<span style="color:#999999;font-size: 5px;">打${book.rebate*10}折</span>
						</c:otherwise>
					</c:choose>
					<!-- 加入购物车的按钮！--------------------------------------------------- -->
					<c:choose>
						<c:when test="${! myfn:isContainsGoods(pageContext.session,book.id)}">
							<div onmouseover="this.style.cursor='hand'" style="width:100px">
								<a id="castlogo${book.id}" onclick="_add('${book.id}')" class="circleborder" style="margin-top:10px; line-height:32px;width:100px;background-color: #4C88DE;display: block;text-align: center;text-decoration: none;color: white;">加入购物车</a>
							</div>
						</c:when>
						<c:otherwise><!-- 已经加入购物车的话 -->
							<div style="width:100px">
								<a id="castlogo${book.id}" class="circleborder" style="margin-top:10px; line-height:32px;width:130px;background-color: red;display: block;text-align: center;text-decoration: none;color: white;">已经加入购物车</a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
	  		</div>
  		</div>
  		<div id="body-foot" style="display: block;/* border:1px solid black; */">
  			<div style="margin: 15px;">简介</div>
  			<hr/>
  			<div style="line-height: 24px;font-size: 15px;text-indent: 2em;margin-left: 20px;margin-right: 20px;margin-top: 10px;">
  				${book.content}
  			</div>
  		</div>
  	</div>
  </body>
</html>
