<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.ssh.entity.Paper"%>
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
    <link rel="stylesheet" type="text/css" href="css/test.css">
     <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");  //没用到
			var csrfToken = $("meta[name='_csrf']").attr("content");
			var csrf_url = csrfParameter+"="+csrfToken;
			$(document).ajaxSend(function(e, xhr, options) {  
			    xhr.setRequestHeader(csrfHeader, csrfToken);
			    
			}); 
    		
    		$(".answer").on("click",function(event){
    		    event.preventDefault();//使a自带的方法失效
    		    var paperNumber = $(this).attr("name");
    		    console.log(paperNumber);
    		    $.ajax({
    		    	   async: false,
    		           type: "POST",
    		           url: "answer",
    		           data: {paperNumber:paperNumber},//参数列表
    		           dataType:"text",
    		           success: function(result){
    		              //请求正确之后的操作
    		        	   window.location.href = "answer";
    		           },
    		           error: function(result){
    		              //请求失败之后的操作
    		        	   alert("Error");
    		           }
    		    }).responseText;
    		});
    		
    	})
    </script>

</head>
<body>
     <!-- 导航栏 -->
    <%@include file="nav.jsp" %>
    <% Map<String,Boolean> flags = (Map<String,Boolean>)session.getAttribute("flags"); 
       List<Paper> papers = (List<Paper>)session.getAttribute("papers");
       int i = 0;
    %>
    <div class="content">
    
    	<c:forEach var="paper" items="${papers }">
			<!-- 问卷 -->
			<% if(flags.get(papers.get(i).getPaperNumber()) == false) {%>
				<div class="list">
					<ul>
						<li class="img_li"><img src="image/image<%=papers.get(i).getPaperNumber() %>.jpg"></li>
						<li class="text_li">
							<p>
								<a href="answer" class="answer" name="${paper.paperNumber }">${paper.paperName }</a>
							</p>
							<p>${paper.description }</p>
						</li>
					</ul>
				</div>
			<%}else{ %>
				<div class="list">
					<ul>
						<li class="img_li"><img src="image/image<%=papers.get(i).getPaperNumber() %>.jpg"></li>
						<li class="text_li">
							<p>
								<a href="u_result"><h4>你已将问卷填写完毕，请前往查看结果吧！</h4></a>
							</p>
						</li>
					</ul>
				</div>
			<%} 
				i++;
			%>
		</c:forEach>
    </div>
</body>
</html>