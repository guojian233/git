<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
</head>
<body>
<div class="main">
	<div class="optitle clearfix">
		<div class="title">账单管理&gt;&gt;</div>
	</div>
		<div class="content">
			<table class="box">
			  <tr>
					<td class="field">账单编号：</td>
					<td>${bill.billId}<input  type="hidden" id="billId" value="${bill.billId}"/></td>
				</tr>
			   <tr>
					<td class="field">商品名称：</td>
					<td>${bill.productName}</td>
				</tr>
				 <tr>
					<td class="field">商品单位：</td>
					<td>${bill.productUnit}</td>
				</tr>
				<tr>
					<td class="field">商品数量：</td>
					<td>${bill.productCount}</td>
				</tr>
				<tr>
					<td class="field">总额：</td>
					<td>${bill.billMoney}</td>
				</tr>
			   <tr>
					<td class="field">供应商：</td>
					<td>${bill.proName}</td>
				</tr>
				 <tr>
					<td class="field">是否付款：</td>
						<td><c:choose>
							<c:when test="${bill.payed==1}">是</c:when>
							<c:otherwise> 否</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</div>
		<div class="buttons">
			<input type="button" name="update" id="update" value="修改" class="input-button" />
			<input type="button" name="del" id="del" value="删除" class="input-button" />
			<input type="button" name="button" id="button" onclick="history.back(-1)" value="返回" class="input-button"/> 
		</div>

</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bill/view.js"></script>
</body>
</html>
