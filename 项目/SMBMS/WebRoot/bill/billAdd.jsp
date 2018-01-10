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
	<form id="form1" name="form1" method="post" action="/SMBMS/bill?m=3" >
		<input type="hidden" name="method" value="add">
		<div class="content">
			<table class="box">
			   <tr>
					<td class="field">商品名称：</td>
					<td><input type="text" name="tradeName" class="text" id="tradeName"  value="${bill.tradeName}"/> <font color="red">*</font><font color="red" id="productName_span"></font></td>
				</tr>
				 <tr>
					<td class="field">商品单位：</td>
					<td><input type="text" name="Unit" class="text" id="Unit"  value="${bill.unit}"/> <font color="red">*</font><font color="red" id="productUnit_span"></font></td>
				</tr>
				<tr>
					<td class="field">商品数量：</td>
					<td><input type="text" name="num" class="text" id="count" value="${bill.num}"/> <font color="red">*</font><font color="red" id="productCount_span"></font></td>
				</tr>
				<tr>
					<td class="field">总额：</td>
					<td><input name="amount" id="amount" class="text" value="${bill.amount}"/><font color="red">*</font><font color="red" id="billMoney_span"></font></td>
				</tr>
			   <tr>
					<td class="field">供应商：</td>
					<td>
						<select name="supplier" id="supplier">
							<c:forEach var="sup" items="${requestScope.suplist}">
								<option value="${sup}">${sup}</option>
							</c:forEach>
						</select>
						<font color="red">*</font><font color="red" id="proId_span"></font>
					</td>
				</tr>
				 <tr>
					<td class="field">是否付款：</td>
					<td><input type="radio" name="payment"  value="0" />是<input type="radio" name="payment"  checked value="1" />否</td>
				</tr>
			</table>
		</div>
		<div class="buttons">
			<input type="submit" name="add" id="add" value="保存" class="input-button" />
			<input type="button" name="button" id="button" onclick="history.back(-1)" value="返回" class="input-button"/> 
		</div>

	</form>
</div>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/bill/add.js"></script> --%>
</body>
</html>
