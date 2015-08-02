<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.kdyzm.com/jsp/jstl/bookstore/myfn" prefix="myfn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <link href="<c:url value='/css/pub/pub.css'/>" type="text/css" rel="stylesheet">
    <style type="text/css">
    	#wrapper
    	{
    		background-color: #FBFAF8;
    		width:900px;
    		/* border:1px solid blue; */
    		font-family: 微软雅黑;
    		color:#666666;
    	}
    	.OneBook
    	{
    		display:inline-block;
    		width:420px;
    		height:180px;
    		/* border:1px solid black; *//* 没对齐？这里有问题？ */
    		margin-left: 20px;
    		/* margin-bottom: 0px; */
    	}
    	.bookleft
    	{
    		display:inline-block;
    		width:110px;
    		/* border:1px solid black; */
    	}
    	.img{
    		display:block;
    		-moz-box-shadow: 6px 6px 10px #BCBAB9;
    		-webkit-box-shadow: 6px 6px 10px #BCBAB9;
    		box-shadow: 6px 6px 6px #BCBAB9;
    		/* border:1px solid black; */
    		margin-left: 5px;
    	}
    	.img-bottom
    	{
    		display:block;
    		/* border:1px solid black; */
    		width:115px;
    		text-align: center;
    		margin-top: 5px;
    	}
    	.bookright
    	{
    		display:inline-block;
    		/* border:1px solid red; */
    		width:290px;
    		height: 172px;
    		vertical-align: top;
    		margin-left: 10px;
    		line-height: 28px;
    	}
    	.castlogo
    	{
    		width:38px;
    		height:37px;
    		display: block;
    		vertical-align:bottom;
    		margin-bottom: 10px;
    	}
    	#index
    	{
    		text-align: right;
    	}
    	#index a
    	{
    		display: inline-block;
    		text-align: center;
    		line-height: 25px;
    		height:25px;
    	/* 	float:right; */
    		border: 1px solid #CCC;
    		margin-right:2px;
    		padding-top:0px;
    		padding-bottom:0px;
    		padding-left:10px;
    		padding-right:10px;
    		color:#A6978A;
    		text-decoration: none;
    		font-size: 12px;
    	}
    	#currentPage
    	{
    		background-color: #A6978A;
    	}
    	.bookname
    	{
    		font-size: 15px;
    		color:#000000;
    		text-decoration: none;
    	}
    	.bookname:HOVER
    	{
	    	color:#FA7A20;
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
   						//替换图片，消除a标签
						var img=document.getElementById("castlogo"+bookid);
						img.src="<c:url value='/otherimages/castlogo1.jpg'/>";
						img.title="已经加入购物车";
						var a=img.parentNode;
						var parent=a.parentNode;
						parent.replaceChild(img, a);
						
						//购物车显示数量加1暂且不做
					}
   				}
    		};
    	}
    </script>
  </head>
  <!-- 显示所有图书信息 -->
  <body>
  	<div id="wrapper">
		<c:forEach items="${requestScope.booklist}" var="book">
			<div class="OneBook">
				<div class="bookleft">
					<a href="<c:url value='/bookServlet?cmd=showSingleBook&bookid=${book.id}'/>">
						<img class="img" alt="" src="<c:url value='/imgs/${book.img}'/>" width="100px" height="133px">
					</a>
					<div class="img-bottom">
						<c:choose>
							<c:when test="${book.rebate==1}">
								<span style="font-size: 7px;font-weight: bold;">￥${book.price}</span>
							</c:when>
							<c:otherwise>
								<span style="font-size: 7px;font-weight: bold;">￥<fmt:formatNumber value="${book.price*book.rebate}" pattern="#.00"></fmt:formatNumber></span>
								<span style="text-decoration: line-through;color:#999999;font-size: 5px;">￥${book.price}</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="bookright">
					<a href="<c:url value='/bookServlet?cmd=showSingleBook&bookid=${book.id}'/>" class="bookname">${book.name}</a><br/>
					<span style="font-size: 12px">${book.auth}</span><br/>
					<span style="font-size: 13px;color:#333;line-height: 18px;">${book.brief}</span><br/>
					<br/>
					<!-- 加入购物车按钮 -->
					<c:choose>
						<c:when test="${! myfn:isContainsGoods(pageContext.session,book.id)}">
							<a onmouseover="this.style.cursor='hand'" onclick="_add('${book.id}')"  style="/* border:1px solid black; */width:38px;height:38px;display: block;" >
								<img title="加入购物车" src="<c:url value='/otherimages/castlogo.jpg'/>" class="castlogo" id="castlogo${book.id}">
							</a>
						</c:when>
						<c:otherwise>
							<img title="已经加入购物车" src="<c:url value='/otherimages/castlogo1.jpg'/>" class="castlogo" id="castlogo${book.id}">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
		
		<!-- 页脚分页管理 -->
		<div id="index">
			<a href='<c:url value="/bookServlet?id=${booktypeid}&page=1"/>'>首页</a>
			<c:choose>
				<c:when test="${currentPage!=1}">
					<c:choose>
						<c:when test="${empty booktypeid}">
							<a href='<c:url value="/bookServlet?page=${currentPage-1}"/>'>上一页</a>
						</c:when>
						<c:otherwise>
							<a href='<c:url value="/bookServlet?id=${booktypeid}&page=${currentPage-1}"/>'>上一页</a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<a>上一页</a>
				</c:otherwise>
			</c:choose>
			
			<c:forEach begin="${requestScope.start}" end="${requestScope.end}" var="index">
				<c:choose>
					<c:when test="${currentPage==index}">
						<a id="currentPage" style="color: #FFF;">${index}</a>
					</c:when>
					<c:otherwise>
						<c:if test="${index>=1&&index<=pageCount}">
							<c:choose>
								<c:when test="${empty booktypeid}">
									<a href='<c:url value="/bookServlet?page=${index}"/>'>${index}</a>
								</c:when>							
								<c:otherwise>
									<a href='<c:url value="/bookServlet?id=${booktypeid}&page=${index}"/>'>${index}</a>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			<c:choose>
				<c:when test="${currentPage!=pageCount}">
					<c:choose>
						<c:when test="${empty booktypeid}">
							<a href='<c:url value="/bookServlet?page=${currentPage+1}"/>'>下一页</a>
						</c:when>
						<c:otherwise>
							<a href='<c:url value="/bookServlet?id=${booktypeid}&page=${currentPage+1}"/>'>下一页</a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<a>下一页</a>
				</c:otherwise>
			</c:choose>
			
			<a href='<c:url value="/bookServlet?id=${booktypeid}&page=${pageCount}"/>'>尾页</a>
			<a >第${currentPage}页/共${pageCount}页</a>
		</div>
	</div>
  </body>
</html>
