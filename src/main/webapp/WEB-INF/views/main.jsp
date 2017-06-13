<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>大学生心理健康测评系统</title>
    <script type="text/javascript">

	function startWebSocket_(){
		var Thread_ = document.getElementById('Thread').value;
		var echo_websocket_;
		var wsUri_ = "ws://localhost:8080/PAsystem/websocket/"+Thread_;
		//1.建立websocket对象
		echo_websocket_ = new WebSocket(wsUri_);
		//2.建立连接
		echo_websocket_.onopen = function(evt){
			console.log("echo_websocket.onopen_");
		}
	}
	window.onload=function(){ 
    	startWebSocket_();
	};
</script>
    <!-- 引入样式表 -->
    <link rel="stylesheet" type="text/css" href="css/nav.css">
    <style type="text/css">
        .img_div img{
            width: 1366px;
            height: 515px;
        }
    </style>
</head>
<body>
    <!-- 导航栏 -->
    <%@include file="nav.jsp" %>

    <div class="img_div">
        <img src="image/indexbg.jpg">
    </div>
</body>
</html>
