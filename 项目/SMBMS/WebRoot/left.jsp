<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>超市账单管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript">
	function logout() {
		top.location = "logout.do";
	}
</script>
</head>
<body class="frame-bd">
	<ul class="left-menu">
		<li><a href="/SMBMS/bill?m=1" target="mainFrame"><img
				src="images/btn_bill.gif" /> </a></li>
		<!--  当用户为管理员时显示 -->
		<c:if test="${sessionScope.user.userType eq 1 }">
			<li><a href="/SMBMS/sup?m=7"
				target="mainFrame"><img src="images/btn_suppliers.gif" /> </a></li>
			<li><a href="/SMBMS/user?m=1" target="mainFrame"><img
					src="images/btn_users.gif" /> </a></li>
		</c:if>
		<li><a href="/SMBMS/user/updatePwd.jsp" target="mainFrame"><img
				src="images/btn_password.gif" /> </a></li>
		<li><a href="#" onClick="logout();"><img
				src="images/btn_exit.gif" /> </a></li>
		<li ><a href="/SMBMS/data/list.jsp" target="mainFrame"  style="font-size: 25px; font-color:red; text-decoration: none;"><img
				src="images/sum.jpg" style="width:60px;" />统计数据</a></li>	
	</ul>
</body>
</html>
