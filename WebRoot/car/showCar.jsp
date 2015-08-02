<%@ page language="java" import="java.util.*" pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.kdyzm.com/jsp/jstl/bookstore/myfn" prefix="myfn" %>
<!-- 显示购物车中的所有商品 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Insert title here!</title>
    <link type="text/css" href="<c:url value='/css/pub/pub.css'/>" rel="stylesheet">
    <script type="text/javascript">
    function deleteSelected()
    {
    	var cb=document.getElementsByName("deleteAll")[0];
    	if(cb.checked)//如果选中了选择所有，则删除所有购物车中的货物。
    	{
    		var request;
     		if(window.XMLHttpRequest){
     			request= new XMLHttpRequest();
     		}else{
     			request= new ActiveXObject("Microsoft.XMLHttp");
     		}
     		var url="<c:url value='/buyServlet?cmd=clearCar'/>";
     		request.open("post", url, true);//异步请求处理
     		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
     		request.send();
     		request.onreadystatechange=function()
     		{
     			if(request.readyState==4)
    			{
    				if(request.status==200)//如果响应成功，则跳转到空购物车的页面中
    				{
    					window.location.href="<c:url value='/buyServlet?cmd=emptyCar'/>";
    				}
    			}
     		};
    	}
    	else//如果没有选择全部，则检查是不是选择了一部分，删除一部分
    	{
     		var books=document.getElementsByName("del");
     		for(var i=0;i<books.length;i++)
   			{
     			if(books[i].checked)
   				{
     				var request;
     	     		if(window.XMLHttpRequest){
     	     			request= new XMLHttpRequest();
     	     		}else{
     	     			request= new ActiveXObject("Microsoft.XMLHttp");
     	     		}
     				var bookid=books[i].value;
         			var url="<c:url value='/buyServlet?cmd=deleteBookByIdFromCar&bookid="+bookid+"'/>";
             		request.open("POST", url, true);//异步请求处理
             		request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
             		request.send();
   				}
   			}
     		window.location.href="<c:url value='/buyServlet?cmd=showCar'/>";
    	}
    }
    
    function selectAll()
    {
    	var eles=document.getElementsByName("del");
    	for(var i=0;i<eles.length;i++)
   		{
   			if(!eles[i].checked)
			{
				for(var j=0;j<eles.length;j++)
				{
					eles[j].checked=true;	
				}
				calculate();
				return;
			}
   		}
    	for(var i=0;i<eles.length;i++)
   		{
   			eles[i].checked=false;
   		}
    	calculate();
    }
    
    function changeDeleteAll(aim)
    {
    	//如果没有被选中，表示是不选的动作。
    	if(!aim.checked)
    	{
    		var cb=document.getElementsByName("deleteAll")[0];
    		cb.checked=false;
    		calculate();
    		return;
    	}
    	var eles=document.getElementsByName("del");
    	for(var i=0;i<eles.length;i++)
   		{
   			if(!eles[i].checked)
			{
   				calculate();
				return;
			}
   		}
    	var cb=document.getElementsByName("deleteAll")[0];
		cb.checked=true;
		calculate();
    }
    //重新计算所有相关的数值。
    function calculate()
    {
    	var eles=document.getElementsByName("del");	
  		var amount=0;
  		var sum=0;
  		for(var i=0;i<eles.length;i++)
   		{
   			var bookid=eles[i].value;
   			/* alert(bookid); */
   			var bookprice=document.getElementById(bookid+"bookprice").innerHTML;
   			/* alert(bookprice); */
   			var bookamount=document.getElementById(bookid+"bookamount").innerHTML;
   			/* alert(bookamount); */
   			var bookmoney=document.getElementById(bookid+"bookmoney");
   			var bookMoney=bookprice*bookamount;
   			bookmoney.innerHTML=bookMoney;
   			if(eles[i].checked)//如果选中了再进行统计
			{
   				amount+=bookamount*1;
	   			sum+=bookMoney;
			}
   		}
  		document.getElementById("amount").innerHTML=amount;
  		document.getElementById("amountMoney").innerHTML=sum;
    }
    //增加一本书
    function _add(bookid)
    {
    	var request;
 		if(window.XMLHttpRequest){
 			request= new XMLHttpRequest();
 		}else{
 			request= new ActiveXObject("Microsoft.XMLHttp");
 		}
 		var url="<c:url value='/buyServlet?cmd=addOneBookByBookid&bookid="+bookid+"'/>";
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
						var bookamount=bookid+"bookamount";
				    	var ba=document.getElementById(bookamount).innerHTML;
				    	ba++;
				    	document.getElementById(bookamount).innerHTML=ba;
				    	calculate();
					}
				}
 		};
    }
    //减少一本书
    function _des(bookid)
    {
    	var request;
 		if(window.XMLHttpRequest){
 			request= new XMLHttpRequest();
 		}else{
 			request= new ActiveXObject("Microsoft.XMLHttp");
 		}
 		var url="<c:url value='/buyServlet?cmd=decreaseOneBookByBookid&bookid="+bookid+"'/>";
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
						var bookamount=bookid+"bookamount";
				    	var ba=document.getElementById(bookamount).innerHTML;
				    	ba--;
				    	document.getElementById(bookamount).innerHTML=ba;
				    	calculate();
					}
				}
 		};
    }
    /*生成订单的方法，实际上是对购物车中的每本书进行标记，表明即将生成订单*/
    function createOrder()
    {
    	window.location.href="<c:url value='/secure/orderServlet'/>";
    }
    </script>
    <style type="text/css">
    	*{
    		font-family: 微软雅黑;
    	}
    	.img
    	{
    		-moz-box-shadow: 6px 6px 10px #BCBAB9;
			-webkit-box-shadow: 6px 6px 10px #BCBAB9;
			box-shadow: 6px 6px 6px #BCBAB9;
    	}
    	#head
     	{
     		font-size:15px;
     		margin-bottom: 15px;
     		/* border:1px solid black; */
     	}
	     	#body
	     	{
	     		width:800px;
	     		margin: 0 auto;
	     	}
     	#body-head
     	{
     		width:800px;
     		height:40px;
     		/* border:1px solid black; */
     		background-color: #F7F5F1;
     	}
     	.perbook
     	{
     		width:800px;
     		height:86px;
     		border:1px solid black;
     	}
     	table
     	{
     		border:1px solid #E0DAD5;
     		width:800px;
     		border-collapse: collapse;
     	}
     	table tr
     	{
     		height:88px;
     	}
     	table td
     	{
     		border:1px solid #E0DAD5;
     		text-align: center;
     	}
     	.first
     	{
     		width:319px;
     		text-align: left;
     	}
    </style>
  </head>
  
  <body>
  <c:if test="${myfn:isEmptyOfCar(pageContext.session) }">
  	<c:redirect url="/buyServlet?cmd=emptyCar"></c:redirect>
  </c:if>
	<%-- ${requestScope.shopping} --%>
	<div id="head">当前位置：
	  	<a href="<c:url value='/bookServlet'/>" class="changecolor">首页</a>
	  	>
	  	<span style="color:#A9A4A1">购物车</span>
  	</div>
  	<div id="body">
  		<div style="width:800px;height:50px;/* border:1px solid black; */line-height:50px;">
  			<div  style="display: inline-block;float:left;font-size: 15px;/* font-weight: bold; */color:#333333;">购物车状态</div>
  			<div style="margin-left:455px;display: inline-block;font-size: 8px;">选中<span id="amount" style="display: inline-block;width:20px;text-align: center;"></span>本，应付金额
  				<!-- 总金额  -->
  				<span style="color:#ED6C00;font-size:15px;font-weight: bold;text-align: center;">￥</span>
  				<span id="amountMoney" style="color:#ED6C00;font-size:15px;font-weight: bold;display: inline-block;width:30px;text-align: center;"></span>
  			</div>
  			<!-- 跳转到结算的页面，但是需要先经过Servlet进行处理 -->
  			<a onmouseover="this.style.cursor='hand'" onclick="createOrder()" class="circleborder" style="line-height:30px;display: inline-block;margin-left:5px;height:30px;width:74px;font-size: 17px;text-decoration: none;color: white;background-color: #E04A34;text-align: center;">
  				去结算
  			</a>
  		</div>
  		<div id="body-head">
  			<div style="text-align: left;line-height: 40px;margin-left: 15px;display: inline-block;">
  				<!-- 选中所有的图书-------------------------------------------------------重点------------------ -->
  				<input type="checkbox" name="deleteAll" value="all" onclick="selectAll()">全选&nbsp;&nbsp;&nbsp;
  				<!-- 删除选中的图书--------------------------------------------------------------------------重点 -->
  				<a onmouseover="this.style.cursor='hand'" class="changecolor" style="font-size: 15px;" onclick="deleteSelected()">删除选中图书</a>
  			</div>
  			<div style="line-height: 40px;margin-right: 10px;display: inline-block;height:40px;float:right;">
  				图书信息
  			</div>
  		</div>
	  		<table>
	  			<thead>
	  				<tr style="height:30px;">
	  					<td>书籍</td>
	  					<td>单价</td>
	  					<td>数量</td>
	  					<td>操作</td>
	  					<td>金额</td>
	  				</tr>
	  			</thead>
		  		<c:forEach items="${requestScope.shopping}" var="book">
		  			<tr style="background-color: white;">
		  				<td class="first">
	  						<input id="${book.id}" onclick="changeDeleteAll(this)" style="display: inline-block;height:60px;float:left;margin-left: 10px;" type="checkbox" value="${book.id}" name="del">
	  						<img style="display:inline-block;float:left;width:53px;margin-left: 10px;" class="img" width="53px" alt="${book.name}" src="<c:url value='/imgs/${book.img}'/>">
	  						<div style="display: inline-block;/* border:1px solid black; */width:200px;margin-left: 15px;margin-top: 12px;">
	  							<span style="font-size: 10px;text-align: left;display: inline-block;">${book.name}</span><br/>
	  							<span style="color:#A299A2;font-size: 10px;text-align: left;display: inline-block;margin-top: 5px;">${book.auth}</span>
	  						</div>
		  				</td>
		  				<td class="others">
		  					<span style="font-size: 13px;">￥</span>
		  					<span id="${book.id}bookprice" style="font-size: 13px;">
		  						<fmt:formatNumber value="${book.price}" pattern="#.00"></fmt:formatNumber>
		  					</span>
		  				</td>
		  				<td id="${book.id}bookamount">${book.amount}</td>
		  				<td class="others">
		  					<a onclick="_add('${book.id}')" class="changecolor" onmouseover="this.style.cursor='hand'" style="display: inline-block;width:15px;height: 15px;text-align: center;line-height: 15px;">增</a>
		  					/
		  					<a onclick="_des('${book.id}')" class="changecolor" onmouseover="this.style.cursor='hand'" style="display: inline-block;width: 15px;height: 15px;text-align: center;line-height: 15px;">减</a>
		  				</td>
		  				<!-- 每一种书应该付的总金额 -->
		  				<td class="others"><!-- 显示金额的 -->
		  					<span style="font-size:13px;">￥</span>
		  					<span id="${book.id}bookmoney" style="font-size:13px;"></span>
		  				</td>
		  			</tr>
		  		</c:forEach>
	  		</table>
  	</div>
  	<script type="text/javascript">
  		var cb=document.getElementsByName("deleteAll")[0];
		cb.checked=true;
		var eles=document.getElementsByName("del");	
		for(var i=0;i<eles.length;i++)
		{
			eles[i].checked=true;
		}
		//先选中再进行计算
  		calculate();
  	</script>
  </body>
</html>
