<%@page import="com.ssh.entity.User"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="com.ssh.entity.ChatRecords"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<sec:csrfMetaTags />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>大学生心理健康测评系统</title>
    
    <%
    	User user = (User) request.getAttribute("user");
    	List<ChatRecords> chatRecords = (List<ChatRecords>) request.getAttribute("chatRecords");
    	List<Map<String,String>> teachers = (List<Map<String,String>>)request.getAttribute("teachers");
    	System.out.println(user == null);
    	System.out.println(chatRecords.size());
    	System.out.println(teachers.size());
    	
    	String number = user.getNumber();
    	String iconPath = session.getServletContext().getRealPath("image");
    	int i = 0;
    	List<Boolean> booleans = new ArrayList();
    	List<String> iconNames = new ArrayList();
    	List<Map<String,String>> lists = new ArrayList();
   		for(Map<String,String> teacher : teachers){
   			Map<String,String> maps = new HashMap();
   			String iconnumber = teacher.get("number");
   			String iconName = iconnumber+".jpg";
   	    	String iconType = "";
   	    	System.out.printf(iconName);
   	    	Boolean iconBoolean;
   	    	File iconFile = new File(iconPath,iconName);
   	    	if(iconFile.exists() == false){
   	    		iconName = number+".png";
   	    		iconFile = new File(iconPath,iconName);
   	    		if(iconFile.exists()){
   	    			/* iconType = "png"; */
   	    			iconBoolean = true;
   	    			booleans.add(iconBoolean);
   	    			iconNames.add(iconName);
   	    			maps.put("iconNumber", iconnumber);
   	    			maps.put("iconName", iconName);
   	    			lists.add(maps);
   	    		}
   	    		else{
   	    			/* iconType = ""; */
   	    			iconBoolean = false;
   	    			iconName = "default_icon.jpg";
   	    			booleans.add(iconBoolean);
   	    			iconNames.add(iconName);
   	    			maps.put("iconNumber", iconnumber);
   	    			maps.put("iconName", iconName);
   	    			lists.add(maps);
   	    			
   	    		}
   	    	}else{
   	    		/* iconType = "jpg"; */
   	    		iconBoolean = true;
   	    		booleans.add(iconBoolean);
   	    		iconNames.add(iconName);
   	    		maps.put("iconNumber", iconnumber);
	    		maps.put("iconName", iconName);
	    		lists.add(maps);
   	    	}
   		}
   		String icons = (String) JSON.toJSONString(lists);
   		
   		String usericon = number+".jpg";
   		File usericonfile = new File(iconPath,usericon);
   		if(usericonfile.exists() == false){
   			usericon = number + ".png";
   			usericonfile = new File(iconPath,usericon);
   			if(usericonfile.exists() == false){
   				usericon = "default_icon.jpg";
   			}
   		}
    %>
    
    <!-- 引入样式表 -->
    <link rel="stylesheet" type="text/css" href="css/ui.jqgrid.css">
	<link rel="stylesheet" type="text/css" href="css/cupertino/jquery-ui-1.8.16.custom.css">
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/nav.css">
    <link rel="stylesheet" type="text/css" href="css/u_consult.css">
    
    
    
    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <script type="text/javascript" src="js/jquery.jqGrid.min.js"></script>
    
    <script type="text/javascript">
    	var number = '<%=number %>';
    	var icons = '<%=icons %>';
    	icons = JSON.parse(icons);
    	var usericon = '<%=usericon%>';
     	var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    	var csrfHeader = $("meta[name='_csrf_header']").attr("content");  //没用到
    	var csrfToken = $("meta[name='_csrf']").attr("content");
    	var csrf_url = csrfParameter+"="+csrfToken;
    	$(document).ajaxSend(function(e, xhr, options) {  
    	    xhr.setRequestHeader(csrfHeader, csrfToken);
    	    
    	});
    	
    </script>
    <script type="text/javascript" src="js/u_consult.js"></script> 
       
</head>
<body>
    <!-- 导航栏 -->
    <%@include file="nav.jsp" %>
    
 	<!-- 消息列表 -->
    <div class="msglist">
    	<div class="msgindex">
    		<span class="msg_span">消息</span>
    		<span>/</span>
    		<span class="pri_span">私信</span>
		</div>
    	<ul>
			<c:forEach var="teacher" items="${teachers }">
				<li class="out_li">
					<div class="out_div">
						<div class="img_div">
							<%if(booleans.get(i) && iconNames.get(i).endsWith("jpg")){ %>
								<img src="image/${teacher.number }.jpg">
							<%}else if(booleans.get(i) && iconNames.get(i).endsWith("png")){ %>
								<img src="image/${teacher.number }.png">
							<%}else{ %>
								<img src="image/default_icon.jpg">
							<%} %>
						</div>
						<div class="name_div">${teacher.status }</div>
					</div>
					
					<ul>
						<li class="name_li">${teacher.name }</li>
						<%ChatRecords chatRecord = chatRecords.get(i);%>
						<%if(chatRecord.getContent() == null){%>
							<li class="content_li"><a href="#" class="to_chat" name="${teacher.number }"><span>暂无聊天记录</span></a></li>
						<%}else{%>
							<li class="content_li">
								<%if(chatRecord.getStatus().equals("未读") && (chatRecord.getFromId().equals(number)==false)){%>
									<a href="#" class="to_chat" name="${teacher.number }">
										<img alt="" src="image/unread.png" name="flag_img"/>
										<span><%=chatRecord.getFromId() %>：</span><%=chatRecord.getContent() %>
									</a>
								<%}else if(chatRecord.getStatus().equals("已读") && (chatRecord.getFromId().equals(number)==false)){ %>
									<a href="#" class="to_chat" name="${teacher.number }">
										<img alt="" src="image/read.png" name="flag_img"/>
										<span><%=chatRecord.getFromId() %>：</span><%=chatRecord.getContent() %>
									</a>
								<%}else{ %>
									<a href="#" class="to_chat" name="${teacher.number }">
										<span><%=chatRecord.getFromId() %>：</span><%=chatRecord.getContent() %>
									</a>
								<%} %>
							</li>
							<li class="time_li"><%=chatRecord.getTime() %></li>
						<%} i++;%>
					</ul>
				</li>
			</c:forEach>
    	</ul>
    </div>
    
    <div class="chat_div">
    	<div class="chat_title">
    		<span><a href="#" class="return">&lt;&lt; 回到私信</a><i></i></span>
    		<span>|</span>
    		<span class="title_span"></span>
    	</div>
    	<div class="chat_content" id="chat_content">
    		
    	</div>
    	<div class="chat_input">
			<div id="input" contenteditable="true"></div>
			<div id="tool">
				<img class="tool_icon" alt="" src="image/tool_icon.jpg">
				<div id="tool_tip">
					<ul>
						<li class="Onion">洋葱头</li>
						<li class="Tieba">贴吧</li>
						<li class="Weibo">微博</li>
					</ul>
					<table id="onion_table" class="table">
						<tr>
							<td class="onion_td"><img src="image/onion/onion (1).gif"></td>
							<td class="onion_td"><img src="image/onion/onion (2).gif"></td>
							<td class="onion_td"><img src="image/onion/onion (3).gif"></td>
							<td class="onion_td"><img src="image/onion/onion (4).gif"></td>
							<td class="onion_td"><img src="image/onion/onion (5).gif"></td>
							<td class="onion_td"><img src="image/onion/onion (6).gif"></td>
							<td class="onion_td"><img src="image/onion/onion (7).gif"></td>
							<td class="onion_td"><img src="image/onion/onion (8).gif"></td>
						</tr>
						<tr>
							<td><img src="image/onion/onion (9).gif"></td>
							<td><img src="image/onion/onion (10).gif"></td>
							<td><img src="image/onion/onion (11).gif"></td>
							<td><img src="image/onion/onion (12).gif"></td>
							<td><img src="image/onion/onion (13).gif"></td>
							<td><img src="image/onion/onion (14).gif"></td>
							<td><img src="image/onion/onion (15).gif"></td>
							<td><img src="image/onion/onion (16).gif"></td>
						</tr>
						<tr>
							<td><img src="image/onion/onion (17).gif"></td>
							<td><img src="image/onion/onion (18).gif"></td>
							<td><img src="image/onion/onion (19).gif"></td>
							<td><img src="image/onion/onion (20).gif"></td>
							<td><img src="image/onion/onion (21).gif"></td>
							<td><img src="image/onion/onion (22).gif"></td>
							<td><img src="image/onion/onion (23).gif"></td>
							<td><img src="image/onion/onion (24).gif"></td>
						</tr>
						<tr>
							<td><img src="image/onion/onion (25).gif"></td>
							<td><img src="image/onion/onion (26).gif"></td>
							<td><img src="image/onion/onion (27).gif"></td>
							<td><img src="image/onion/onion (28).gif"></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table id="tieba_table" class="table">
						<tr>
							<td><img src="image/tieba/tb (1).png"></td>
							<td><img src="image/tieba/tb (2).png"></td>
							<td><img src="image/tieba/tb (3).png"></td>
							<td><img src="image/tieba/tb (4).png"></td>
							<td><img src="image/tieba/tb (5).png"></td>
							<td><img src="image/tieba/tb (6).png"></td>
							<td><img src="image/tieba/tb (7).png"></td>
							<td><img src="image/tieba/tb (8).png"></td>
						</tr>
						<tr>
							<td><img src="image/tieba/tb (9).png"></td>
							<td><img src="image/tieba/tb (10).png"></td>
							<td><img src="image/tieba/tb (11).png"></td>
							<td><img src="image/tieba/tb (12).png"></td>
							<td><img src="image/tieba/tb (13).png"></td>
							<td><img src="image/tieba/tb (14).png"></td>
							<td><img src="image/tieba/tb (15).png"></td>
							<td><img src="image/tieba/tb (16).png"></td>
						</tr>
						<tr>
							<td><img src="image/tieba/tb (17).png"></td>
							<td><img src="image/tieba/tb (18).png"></td>
							<td><img src="image/tieba/tb (19).png"></td>
							<td><img src="image/tieba/tb (20).png"></td>
							<td><img src="image/tieba/tb (21).png"></td>
							<td><img src="image/tieba/tb (22).png"></td>
							<td><img src="image/tieba/tb (23).png"></td>
							<td><img src="image/tieba/tb (24).png"></td>
						</tr>
						<tr>
							<td><img src="image/tieba/tb (25).png"></td>
							<td><img src="image/tieba/tb (26).png"></td>
							<td><img src="image/tieba/tb (27).png"></td>
							<td><img src="image/tieba/tb (28).png"></td>
							<td><img src="image/tieba/tb (29).png"></td>
							<td><img src="image/tieba/tb (30).png"></td>
							<td><img src="image/tieba/tb (31).png"></td>
							<td><img src="image/tieba/tb (32).png"></td>
						</tr>
					</table>
					<table id="weibo_table" class="table">
						<tr>
							<td><img src="image/weibo/wb (1).png"></td>
							<td><img src="image/weibo/wb (2).png"></td>
							<td><img src="image/weibo/wb (3).png"></td>
							<td><img src="image/weibo/wb (4).png"></td>
							<td><img src="image/weibo/wb (5).png"></td>
							<td><img src="image/weibo/wb (6).png"></td>
							<td><img src="image/weibo/wb (7).png"></td>
							<td><img src="image/weibo/wb (8).png"></td>
						</tr>
						<tr>
							<td><img src="image/weibo/wb (9).png"></td>
							<td><img src="image/weibo/wb (10).png"></td>
							<td><img src="image/weibo/wb (11).png"></td>
							<td><img src="image/weibo/wb (12).png"></td>
							<td><img src="image/weibo/wb (13).png"></td>
							<td><img src="image/weibo/wb (14).png"></td>
							<td><img src="image/weibo/wb (15).png"></td>
							<td><img src="image/weibo/wb (16).png"></td>
						</tr>
						<tr>
							<td><img src="image/weibo/wb (17).png"></td>
							<td><img src="image/weibo/wb (18).png"></td>
							<td><img src="image/weibo/wb (19).png"></td>
							<td><img src="image/weibo/wb (20).png"></td>
							<td><img src="image/weibo/wb (21).png"></td>
							<td><img src="image/weibo/wb (22).png"></td>
							<td><img src="image/weibo/wb (23).png"></td>
							<td><img src="image/weibo/wb (24).png"></td>
						</tr>
						<tr>
							<td><img src="image/weibo/wb (25).png"></td>
							<td><img src="image/weibo/wb (26).png"></td>
							<td><img src="image/weibo/wb (27).png"></td>
							<td><img src="image/weibo/wb (28).png"></td>
							<td><img src="image/weibo/wb (29).png"></td>
							<td><img src="image/weibo/wb (30).png"></td>
							<td><img src="image/weibo/wb (31).png"></td>
							<td><img src="image/weibo/wb (32).png"></td>
						</tr>
					</table>
				</div>
			</div>
			<input type="button" name="send" id="send" value="发送" onclick="send_echo()" />
		</div>
    </div>

	<div id="messageDialog" style="display: none;">
		<h5></h5>
	</div>
</body>
</html>
