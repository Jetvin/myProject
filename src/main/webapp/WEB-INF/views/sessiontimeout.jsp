<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Session Timeout</title>

<link
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- <link href="css/base.css" rel="stylesheet"> -->

<style >
    #pageNotFound{
        text-align: center;
        font-family: "FZShuTi","STLiti";
        font-size: 30px;
    }
    h3{
    	font-size:30px;
    }
</style>
</head>

<body>
	<section class="container" id="main">
		<div class="col-sm-3"></div>
		<div class="col-sm-6" id="pageNotFound">
            <br/>
			<img style="width: 300px;" src="image/sad.png" class="img-responsive  center-block"/>
			<br/>
			<c:url var="loginUrl" value="/login" />
			<h3><a href="${loginUrl }">点我登录...</a></h3>
			<br/>
			<p>~ 会话超时 ~</p>
			<br/>
			<span>~ The Session Is Timeout ~</span>
		</div>
        <div class="col-sm-3"></div>
	</section>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>