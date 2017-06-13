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

<title>500</title>

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
    	background-color:#f2f2f2;
    }
</style>
</head>

<body>
	<!-- 使用动态include指令导入头部 -->
	<section class="container" id="main">
		<!-- <div class="col-sm-3"></div> -->
		<div class="col-sm-12" id="pageNotFound">
            <br/>
			<img style="width: 900px;" src="image/500.jpg" class="img-responsive  center-block"/>
			<p>服务器内部错误！</p>
		</div>
        <div class="col-sm-3"></div>
	</section>
</body>
</html>