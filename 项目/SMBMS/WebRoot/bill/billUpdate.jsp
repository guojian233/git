<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath }/css/style.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
</head>
<body>
	<div class="main">
		<div class="optitle clearfix">
			<div class="title">账单管理&gt;&gt;</div>
		</div>
		<form id="form1" name="form1" method="post" action="/SMBMS/bill?m=5">
			<input type="hidden" name="method" value="update"> <input
				type="hidden" name="id" id="id" value="${bill.id}" />
			<div class="content">
				<table class="box">
					<tr>
						<td class="field">商品名称：</td>
						<td><input type="text" name="tradeName" class="text"
							id="tradeName" value="${bill.tradeName}" /> <font color="red">*</font><font
							color="red" id="productName_span"></font></td>
					</tr>
					<tr>
						<td class="field">商品单位：</td>
						<td><input type="text" name="unit" class="text" id="unit"
							value="${bill.unit}" /> <font color="red">*</font><font
							color="red" id="productUnit_span"></font></td>
					</tr>
					<tr>
						<td class="field">商品数量：</td>
						<td><input type="text" name="num" class="text" id="num"
							value="${bill.num}" /> <font color="red">*</font><font
							color="red" id="productCount_span"></font></td>
					</tr>
					<tr>
						<td class="field">总额：</td>
						<td><input name="amount" id="amount" class="text"
							value="${bill.amount}" /><font color="red">*</font><font
							color="red" id="billMoney_span"></font></td>
					</tr>
					${suplist}
					<tr>
						<td class="field">供应商：</td>
						<td><select name="supplier" id="supplier">
								<c:forEach var="provider" items="${suplist}">
									<!--问题1？ -->
									<!-- 如果点击商品名对应的id和列表对应的值一样就选中 -->
									<option value="${provider}"
										<c:if test="${provider eq bill.supplier}">selected="selected"
										</c:if>>${provider}</option>
								</c:forEach>
						</select> <font color="red">*</font><font color="red" id="proId_span"></font>
						</td>
					</tr>
					<tr>
						<td class="field">是否付款：</td>
						<td><input type="radio" name="payment"
							<c:if test="${bill.payment==1}">checked="checked"</c:if>
							value="1" />否 <input type="radio" name="payment"
							<c:if test="${bill.payment==0}">checked="checked"</c:if>
							value="0" />是</td>
					</tr>
				</table>
			</div>
			<script type="text/javascript">
				function updatePro() {
					if (confirm("确认是否保存?")) {
						$(document.getElementById("form1")).submit();//表单提交
					}
				}
				//删除用户时的跳转判断
				function del() {
					var r = confirm("是否删除~~");
					if (r == true) {
						location.href = "bill?m=7&id=${bill.id}";
					}
				}
			</script>
			<div class="buttons">
			 <input type="button" name="update" id="update" value="保存" class="input-button"="updatePro()" /> 
			 <input type="button" name="delect" id="delect" value="删除" onclick="del()" class="input-button">
			 <input type="button" name="button" id="button" onclick="history.back(-1)" value="返回" class="input-button" />
			</div>
		</form>
	</div>
	<%-- <script type="text/javascript"
		src="${pageContext.request.contextPath }/js/bill/update.js"></script> --%>
</body>
</html>
