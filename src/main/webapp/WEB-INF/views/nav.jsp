<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<script type="text/javascript">
	function logoutClick(){
		if (confirm('系统提示，您确定要退出本次登录吗?')) {
			location.href = 'logout';
		}
	};
</script>
<div class="header">
	<nav id="nav">
		<div class="nav_hd">
			<h2>大学生心理健康测评系统</h2>
		</div>
		<div class="nav_bd">
			<ul>
				<li><a href="main">首页</a></li>
				<sec:authorize access="hasRole('USER')">
					<li><a href="test">心理测评</a></li>
					<li><a href="u_result">测评结果</a></li>
					<li><a href="u_consult">在线咨询</a></li>
				</sec:authorize>
				<sec:authorize access="hasRole('ADMIN')">
					<li><a href="t_result">结果统计</a></li>
					<li><a href="search">结果查询</a></li>
					<li><a href="t_consult">私信消息</a></li>
				</sec:authorize>
				<li><a href="information">其它</a></li>
			</ul>
		</div>
		<div class="nav_fd">
			<ul class="first_ul">
				<c:if test="${iconType == 1 }">
					<li class="li1"><img alt="" src="image/${user.number }.jpg"></li>
				</c:if>
				<c:if test="${iconType == 2 }">
					<li class="li1"><img alt="" src="image/${user.number }.png"></li>
				</c:if>
				<c:if test="${iconType == 0 }">
					<li class="li1"><img alt="" src="image/default_icon.jpg"></li>
				</c:if>
				<li class="li2">${user.name }</li>
				<c:url value="/logout" var="logoutUrl"/>
				<li class="li3"><a id="logout" href="#" onclick="logoutClick()">注销</a></li>
			</ul>
		</div>
		<input type="hidden" id="Thread" name="Thread" value="${user.number}"/>
	</nav>
</div>