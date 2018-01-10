<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>超市账单管理系统-账单管理</title>
<link type="text/css" rel="stylesheet" href="/SMBMS/css/style.css">
</head>
<!-- 点击后变色 -->
<style>
	a:HOVER{
		color:red !important;
	}
</style>
<body>
	<div class="menu">
		<table>
			<tbody>
				<tr>
					<td><form method="post" action="/SMBMS/bill?m=2">
					<!-- value值为用户输入的值 -->
							<input name="method" value="query" class="input-text"
								type="hidden"> 商品名称：<input name="productName"
								class="input-text" type="text" value='${requestScope.bill.tradeName}'>
							供应商名称：<input name="proName" class="input-text" type="text"
								value='${requestScope.bill.supplier}'> 是否付款：<input type="radio"
								name="payed" value="0" checked="checked" />是<input type="radio"
								name="payed" value="1" />否 <input value="查 询" type="submit">
						</form>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="main">
		<div class="optitle clearfix">
			<em><input value="添加账单" class="input-button"
				onclick="window.location='/SMBMS/sup?m=0'" type="button">
			</em>
			<div class="title">账单管理&gt;&gt;</div>
		</div>
			<!-- 显示提示语 -->
		<c:if test="${requestScope.bill ne null }">
			<div style="font-size: 21px;">
				搜索条件为:
				<c:if test="${requestScope.bill.tradeName ne '' }">
					商品名 :<strong style="color:blue">${requestScope.bill.tradeName}</strong>
				</c:if>
				<c:if test="${requestScope.bill.supplier ne '' }">
					供应商 :<strong style="color:blue">${requestScope.bill.supplier}</strong>
				</c:if>
				是否付款:
				<c:if test="${requestScope.bill.payment eq 0 }">
						付款
					   </c:if>
				<c:if test="${requestScope.bill.payment eq 1 }">
						未付款
					   </c:if>
					   <a href="/SMBMS/bill?m=1" style="text-decoration: none;font-size: 25px;">x</a>
			</div>
		</c:if>
		<div class="content">
			<table class="list">
				<tbody>
					<tr>
						<td width="70" height="29"><div class="STYLE1" align="center">编号</div>
						</td>
						<td width="80"><div class="STYLE1" align="center">商品名称</div>
						</td>
						<td width="80"><div class="STYLE1" align="center">供应商</div></td>
						<td width="100"><div class="STYLE1" align="center">账单金额</div>
						</td>
						<td width="100"><div class="STYLE1" align="center">是否付款</div>
						</td>
						<td width="100"><div class="STYLE1" align="center">操作时间</div>
						</td>
					</tr>
					<c:forEach var="bill" items="${requestScope.list}">
						<tr>
							<td width="70" height="29"><div class="STYLE1"
									align="center">${bill.id}</div></td>
							<td width="80"><div class="STYLE1" align="center"><a href="/SMBMS/bill?m=4&id=${bill.id}" style="text-decoration:none;color: black;">${bill.tradeName}</a></div>
							</td>
							<td width="80"><div class="STYLE1" align="center">${bill.supplier}</div>
							</td>
							<td width="100"><div class="STYLE1" align="center">${bill.amount}</div>
							</td>
							<td width="100"><div class="STYLE1" align="center">
									<!-- 0:付款 1:未付款 -->
									<c:if test="${bill.payment eq 1 }">
								未付款
							</c:if>
									<c:if test="${bill.payment eq 0 }">
								付款
							</c:if>
								</div>
							</td>
							<td width="100"><div class="STYLE1" align="center">${bill.createDate}</div>
							</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
	</div>
	<!--当没有输入条件时的分页 -->
	<c:if test="${requestScope.bill eq null}">
	<div  style="text-decoration:none; font-size: 20px">
		总:${requestScope.total}页
		<c:if test="${requestScope.page ne 1}">
			<a href="/SMBMS/bill?m=1&page=1" style="text-decoration:none;">首页</a>
			<a href="/SMBMS/bill?m=1&page=${requestScope.page-1}"style="text-decoration:none;">上一页</a>
		</c:if>
		<c:if test="${requestScope.page ne total }">
		<a href="/SMBMS/bill?m=1&page=${requestScope.page+1}"style="text-decoration:none;">下一页</a>
		<a href="/SMBMS/bill?m=1&page=${requestScope.total}"style="text-decoration:none;">尾页</a>
		</c:if>
		<span style="color:red;">当前页:第${requestScope.page}页</span>
	</div>
	</c:if>
		<!--当输入条件时的分页 -->
	<c:if  test="${requestScope.bill ne null }">
		<div style="text-decoration:none; font-size: 20px">
		总:${requestScope.total}页
		<c:if test="${requestScope.page ne 1}">
			<a href="/SMBMS/bill?m=2&page=1&productName=${requestScope.bill.tradeName}&proName=${requestScope.bill.supplier}&payed=${requestScope.bill.payment}"style="text-decoration:none;">首页</a>
			<a href="/SMBMS/bill?m=2&page=${requestScope.page-1}&productName=${requestScope.bill.tradeName}&proName=${requestScope.bill.supplier}&payed=${requestScope.bill.payment}"style="text-decoration:none;">上一页</a>
		</c:if>
		<c:if test="${requestScope.page ne total }">
		<a href="/SMBMS/bill?m=2&page=${requestScope.page+1}&productName=${requestScope.bill.tradeName}&proName=${requestScope.bill.supplier}&payed=${requestScope.bill.payment}"style="text-decoration:none;">下一页</a>
		<a href="/SMBMS/bill?m=2&page=${requestScope.total}&productName=${requestScope.bill.tradeName}&proName=${requestScope.bill.supplier}&payed=${requestScope.bill.payment}"style="text-decoration:none;">尾页</a>
		</c:if>
		<span style="color:red;">当前页:${requestScope.page}页</span>
	</div>
	</c:if>
</body>
</html>