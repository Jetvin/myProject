<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<sec:csrfMetaTags />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>大学生心理健康测评系统</title>
<!-- 引入样式表 -->
<link rel="stylesheet" type="text/css" href="css/nav.css">
<link rel="stylesheet" type="text/css" href="css/u_result.css">

<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/no-data-to-display.js"></script>
<script type="text/javascript" src="js/u_result.js"></script>

</head>
<body>
	<!-- 导航栏 -->
	<%@include file="nav.jsp"%>
	<% int i = 1;
	   int n = 1;
	%>
	<div class="west">
		<ul>
			<c:forEach var="paper" items="${papers }">
				<li><a href="#tab_0<%=i %>_" id="tab_0<%=i %>" class="a" name="${paper.paperNumber }">${paper.paperName }</a></li>
				<% i++; %>
			</c:forEach>
		</ul>
	</div>
	<div class="center">
		<c:forEach var="paper" items="${papers }">
			<div id="tab_0<%=n %>_" class="tab">
				<ul>
					<li><a href="">${paper.paperName }</a></li>
				</ul>
				<div>
					<div id="tab_0<%=n %>_container" class="container"></div>
					<div class="remark">
						<h3>评语：</h3>
						<p></p>
						<!-- 您对时间的把握比较谨慎，热爱运动，生活作息规律，较为休闲养生，做事方式严谨，与人相处和睦，热爱生活，要自信，为人幽默，会关心注意自己的身体状况，对美好生活憧憬。 -->
					</div>
				</div>
			</div>
			<% n++; %>
		</c:forEach>
	</div>
</body>
</html>