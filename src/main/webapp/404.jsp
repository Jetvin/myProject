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

<title>404 not found</title>

<link href="css/bootstrap.css" rel="stylesheet">
<script src="js/jquery-1.12.4.js"></script>
<style >
    #pageNotFound{
        text-align: center;
        font-family: "FZShuTi","STLiti";
        font-size: 30px;
    }
    h3{
    	font-size:30px;
    }
    body{
    	background-color:#d2deca;
    }
</style>
</head>

<body>
	<section class="container" id="main">
		<div class="col-sm-3"></div>
		<div class="col-sm-6" id="pageNotFound">
            <br/>
			<img style="width: 300px;" src="${ctx }/image/exception/404.jpg" class="img-responsive  center-block"/>
			<br/>
			<p>~~这个页面随风飘走了~~</p>
			<p>~~<a href="main">点我回首页</a>~~</p>
			<!-- <br/>
			<span>Sorry~ this page was the alien took ~</span> -->
		</div>
        <div class="col-sm-3"></div>
	</section>
</body>
</html>