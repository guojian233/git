<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>超市账单管理系统-错误页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>
错误原因：
<%=exception.getMessage() %>
<br>
详细错误描述：
<%=exception.toString() %>
</body>
</html>
