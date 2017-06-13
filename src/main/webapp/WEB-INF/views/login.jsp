<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户登录</title>
	
	<link rel="stylesheet" href="css/bootstrap.css" />
	<link rel="stylesheet" href="css/login.css" />
	<script type="text/javascript" src="js/bootstrap.js"></script>
</head>
<body>
	<div class="mymain">
		<c:url var="loginUrl" value="/login" />
		<form action="${loginUrl}" method="post" class="login_form">
			<div class="col-lg-12 text-center text-info">
				<h2>大学生心理健康测评系统</h2>
			</div>
            <div class="col-lg-12"></div>
			<div class="col-lg-12">
				<div class="input-group">
					<span class="input-group-addon">用户</span> <input type="text"
						class="form-control" name="username" placeholder="请输入账户名" required
						autofocus />
				</div>
			</div>
			<div class="col-lg-12"></div>
			<div class="col-lg-12">
				<div class="input-group">
					<span class="input-group-addon">密码</span> <input type="password"
						class="form-control" name="password" placeholder="请输入密码" required
						autofocus />
				</div>
			</div>
			<div class="col-lg-12"></div>
            
			<input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}" />
			<div class="col-lg-12">
				<center>${message}</center>
			</div>
			<div class="col-lg-12">
				<button type="submit" class="btn btn-success col-lg-12" style="width: 200px;margin-left: 130px;">登录</button>
			</div>
		</form>
	</div>
</body>
</html>