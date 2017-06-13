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

<title>401 Authentication Failure</title>

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
</style>
</head>

<body>
	<section class="container" id="main">
		<div class="col-sm-3"></div>
		<div class="col-sm-6" id="pageNotFound">
            <br/>
			<img style="width: 300px;" src="image/sad.png" class="img-responsive  center-block"/>
			<br/>
			<h3>401</h3>
			<br/>
			<p>~ 授权失败 ~</p>
			<br/>
			<span>~ Authentication Failure ~</span>
		</div>
        <div class="col-sm-3"></div>
	</section>
</body>
</html>