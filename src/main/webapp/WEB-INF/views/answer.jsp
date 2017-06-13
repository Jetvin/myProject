<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<sec:csrfMetaTags />
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>大学生心理健康测评系统</title>
	<!-- 引入样式表 -->
	<link rel="stylesheet" type="text/css" href="css/ui.jqgrid.css">
	<link rel="stylesheet" type="text/css" href="css/cupertino/jquery-ui-1.8.16.custom.css">
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/nav.css">
    <link rel="stylesheet" type="text/css" href="css/answer.css">
    
    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");  //没用到
			var csrfToken = $("meta[name='_csrf']").attr("content");
			var csrf_url = csrfParameter+"="+csrfToken;
			$(document).ajaxSend(function(e, xhr, options) {  
			    xhr.setRequestHeader(csrfHeader, csrfToken);
			    
			}); 
			var count = 0;
			var answers = "";
			var jsonLength;
			
			/** 消息提示框 **/
			$("#messageDialog").dialog({
				autoOpen : false,
				modal: true,
				width : 250,   //弹出框宽度
				height : 150,   //弹出框高度
				title : "消息提示",  //弹出框标题
				show: {
				    effect: "explode",
				    duration: 500
				},
				hide: {
				    effect: "explode",
				    duration: 500
				},
				buttons:{
					'确定':function(){
						window.location = "test";
						$(this).dialog("close");
					}
				}
			});
			/** 消息提示框 **/
			
    		$("input").click(function(){
    			count++;
    			answers +=$(this).attr("value")+" ";
    			$(this).attr("checked",false);
    			$.ajax({
    				   async: false,
    		           url: "readTestQuestion",
    		           dataType:"json",
    		           success: function(data){
    		              //请求正确之后的操作
    		        	  if(count < data.length){
    		        		  $("#question").text(data[count].question);
    						  $("#A").text(data[count].answerA);
    						  $("#B").text(data[count].answerB);
    						  $("#C").text(data[count].answerC);
    						  $("#D").text(data[count].answerD);
    						  
    		        	  }
    		        	  else if(count == data.length){
    		        		  $.ajax({
    		        			  	async: false,
    		    					url:"saveAnswer?answers=" + answers +"&count="+count,
    		    					success: function(){
    		    						$("#messageDialog h5").text("答题完毕");
    		    						$("#messageDialog").dialog('open');
    		    						console.log(count);
    		    					}
    		    			  }).responeText;
    		        		  
    		        	  }
    		              
    		           },
    		           error: function(result){
    		              	//请求失败之后的操作
    		              	$("#messageDialog h5").text("读取题目失败");
    		    			$("#messageDialog").dialog('open');
    		           }
    			}).responseText;
    			
    		});
    	})
    </script>
    
</head>
<body>
	<!-- 导航栏 -->
    <%@include file="nav.jsp" %>

	<div class="answer_div">
		<div class="answer">
			<div class="answer_header">
				<div class="title">${title}</div>
			</div>
			<div class="answer_body">
				<c:forEach var="list" items="${json}" begin="0" end="0" step="1">
					<div id="question">${list.question}</div>
					<div>
						<input type="radio" name="answer" value="A"/> <label for="A" id="A">${list.answerA}</label>
					</div>
					<div>
						<input type="radio" name="answer" value="B"/> <label for="B" id="B">${list.answerB}</label>
					</div>
					<div>
						<input type="radio" name="answer" value="C"/> <label for="C" id="C">${list.answerC}</label>
					</div>
					<div>
						<input type="radio" name="answer" value="D"/> <label for="D" id="D">${list.answerD}</label>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<div id="messageDialog" style="display: none;">
		<h5></h5>
	</div>
</body>
</html>