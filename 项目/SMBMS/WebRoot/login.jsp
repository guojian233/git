<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>系统登录 - 超市账单管理系统</title>
<link type="text/css" rel="stylesheet" href="/SMBMS/css/style.css" />
<script type="text/javascript" src="/SMBMS/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function validate() {
		var result = true;
		var loginName = document.getElementById("loginName").value;
		var password = document.getElementById("password").value;
		var loginNameSpan = document.getElementById("loginNameSpan");
		var passwordSpan = document.getElementById("passwordSpan");
		if (loginName) {
			loginNameSpan.innerText = "";
		} else {
			loginNameSpan.innerText = "请输入登陆名";
			result = false;
		}
		if (password) {
			passwordSpan.innerText = "";
		} else {
			passwordSpan.innerText = "请输入登陆密码";
			result = false;
		}
		if (result) {
			document.actionForm.submit();
		}
	}
</script>
<script type="text/javascript">
	$(function() {
		$('#kaptchaImage').click(function() {
			$(this).attr('src', 'kaptcha.jpg?' +

			Math.floor(Math.random() * 100));
		});
	});
</script>
</head>
<body class="blue-style">
	<div id="login">
		<div class="icon"></div>
		<div class="login-box">
			<form action="/SMBMS/loginServlet.do" name="actionForm"
				id="actionForm" method="post">
				<dl>
					<dt>用户名：</dt>
					<dd>
						<input type="text" name="loginName" id="loginName"
							class="input-text" /> <span id="loginNameSpan"></span>
					</dd>
					<dt>密 码：</dt>
					<dd>
						<input type="password" name="password" id="password"
							class="input-text" /><span id="passwordSpan"></span>
					</dd>
					</dd>
					<dt>验证码:</dt>
					<dd>
						<input type="text" name="kaptcha" style="height:25px; "> <img
							id="kaptchaImage" src="kaptcha.jpg"
							style="width: 100px;height:28px;" />
					</dd>
				</dl>
				<div class="buttons">
					${error} <input type="button" value="登录系统" class="input-button"
						onclick="validate();" /> <input type="reset" value="重　　填"
						class="input-button" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>
