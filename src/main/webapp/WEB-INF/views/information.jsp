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
    <link rel="stylesheet" type="text/css" href="css/pikaday.css">
    <link rel="stylesheet" type="text/css" href="css/ui.jqgrid.css">
	<link rel="stylesheet" type="text/css" href="css/cupertino/jquery-ui-1.8.16.custom.css">
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="css/nav.css">
    <link rel="stylesheet" type="text/css" href="css/information.css">

    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <script type="text/javascript" src="js/moment.js"></script>
    <script type="text/javascript" src="js/pikaday.js"></script>
    <script type="text/javascript" src="js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="js/grid.locale-cn.js"></script>
	<script type="text/javascript" src="js/jquery.ajaxupload.js"></script>
    <script type="text/javascript" src="js/information.js"></script>
</head>
<body>
    <!-- 导航栏 -->
    <%@include file="nav.jsp" %>

    <div class="west">
        <ul>
            <li><a href="#tab_01_" id="tab_01" class="a">基本资料</a></li>
            <li><a href="#tab_02_" id="tab_02" class="a">联系方式</a></li>
            <li><a href="#tab_03_" id="tab_03" class="a">修改密码</a></li>
            <sec:authorize access="hasRole('ADMIN')">
            <li><a href="#tab_04_" id="tab_04" class="a">试题维护</a></li>
            <li><a href="#tab_05_" id="tab_05" class="a">用户注册</a></li>
            </sec:authorize>
        </ul>
    </div>
    <div class="center">
        <div id="tab_01_" class="tab">
            <ul>
                <li><a href="#">基本资料</a></li>
            </ul>
            <div class="info">
                <form>
                    <ol>
                    	<li>
                            <label for="icon">头像</label>
                            <input type="file" name="icon" id="icon" accept="image/jpeg,image/png" onchange="previewIcon(this)">
                            <c:if test="${iconType == 1 }">
								<div id="user_icon">
									<img alt="" src="image/${user.number }.jpg">
								</div>
							</c:if> <c:if test="${iconType == 2 }">
								<div id="user_icon">
									<img alt="" src="image/${user.number }.png">
								</div>
							</c:if> <c:if test="${iconType == 0 }">
								<div id="user_icon">
									<img alt="" src="image/default_icon.jpg">
								</div>
							</c:if>
						</li>
                        <li>
                            <label for="name">姓名</label>
                            <input type="text" name="name" id="name"  value="${user.name}">
                        </li>
                        <li>
                            <label for="sex">性别</label>
                            <select name="sex" id="sex" value="${user.sex }">
                                <option value="男" selected="selected">男</option>
                                <option value="女">女</option>
                                <option value="保密">保密</option>
                            </select>
                        </li>
                        <li>
                            <label for="borth">生日</label>
                            <input type="text" id="borth" value="${user.borth }">
                        </li>
                        <li>
                            <label>年级</label>
                            <input type="text" name="grade" id="grade" value="${user.grade }">
                        </li>
                        <li>
                            <label>学院</label>
                            <input type="text" name="institute" id="institute" value="${user.institute }">
                        </li>
                        <li>
                            <label>专业</label>
                            <input type="text" name="major" id="major" value="${user.major }">
                        </li>
                    </ol>
                    <input type="button" id="info_submit" name="submit" value="提交">
                </form>
            </div>
        </div>
        <div id="tab_02_" class="tab">
            <ul>
                <li><a href="#">联系方式</a></li>
            </ul>
            <div class="contact">
                <form action="#" method="post">
                    <ol>
                        <li>
                            <label for="qq">QQ：</label>
                            <input type="text" name="QQ" id="QQ" value="${user.qq }">
                        </li>
                        <li>
                            <label for="tel">手机号码：</label>
                            <input type="text" name="tel" id="tel" value="${user.tel }">
                        </li>
                        <li>
                            <label for="addr">联系地址：</label>
                            <input type="text" name="addr" id="addr" value="${user.addr }">
                        </li>
                    </ol>
                    <input type="button" id="contact_submit" name="submit" value="提交">
                </form>
            </div>
        </div>
        <div id="tab_03_" class="tab">
            <ul>
                <li><a href="#">修改密码</a></li>
            </ul>
            <div class="pwd">
                <form action="modify_pwd" method="post">
                    <ol>
                        <li>
                            <label for="old_pwd">原始密码：</label>
                            <input id="old_pwd" name="old_pwd" type="password" placeholder="请输入旧密码" autofocus>
                            <label for="remark">*(必填)</label>
                        </li>
                        <li>
                            <label for="fir_pwd">新密码：</label>
                            <input id="fir_pwd" name="fir_pwd" type="password" placeholder="请输入新密码，长度8~12位">
                            <label for="remark" id="message1"></label>
                        </li>
                        <li>
                            <label for="sec_pwd">确认密码：</label>
                            <input id="sec_pwd" name="sec_pwd" type="password" placeholder="请确认密码，长度8~12位">
                            <label for="remark" id="message2"></label>
                        </li>
                    </ol> 
                    <input type=button id="pwd_submit" name="submit" value="提交">
                </form>
            </div>
        </div>
        
        <div id="tab_04_" class="tab">
            <ul>
                <li><a href="#">试题维护</a></li>
            </ul>
            <div class="gridDiv">
               	<table id="Grid"></table>
                <div id="Toolbar"></div>
            </div>
        </div>
        
        <div id="tab_05_" class="tab">
            <ul>
                <li><a href="#">用户注册</a></li>
            </ul>
            <div class="gridDiv">
               	<table id="RegistryGrid"></table>
                <div id="RegistryToolbar"></div>
            </div>
        </div>
        
        <div id="registryDialog" style="display: none;">
        	<form action="Registry" method="post" enctype="multipart/form-data">
        		<div name="outter">
        			<label for="text">Excel文件：</label><input type="file" id="registryFile" class="file" name="registry" accept="application/vnd.ms-excel"/>
        		</div>
        		
        	</form>
		</div>
        
        <div id="upDateDialog" style="display: none;">
        	<form action="upDateFile" method="post" enctype="multipart/form-data">
        		<!-- <input type="file" id="update" class="file" name="file" accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/msword"/>
				<textarea id="textarea" name="description" placeholder="请50字左右的问卷描述"></textarea> -->
				<div name="outter">
        			<label for="text">问卷：</label><input type="file" id="updateFile" class="file" name="file" accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/msword"/>
        		</div> 
        		<div name="outter">
        			<label for="text">评语：</label><input type="file" id="updateExcel" class="file" name="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
        		</div> 
				<textarea id="textarea" name="description" placeholder="请输入50字左右的问卷描述"></textarea>
        	</form>
		</div>
		
		<div id="upLoadDialog" style="display: none;">
        	<form action="upLoadFile" method="post" enctype="multipart/form-data">
        		<div name="outter">
        			<label for="text">卷号：</label><input type="input" id="paperNumber" class="file" name="paperNumber" placeholder="格式：00001"/>
        		</div>
        		<div id="img"></div>
        		<div name="outter">
        			<label for="text">图片：</label><input type="file" id="uploadImg" class="file" name="img" accept="image/jpeg" onchange="previewImage(this)"/>
        		</div>
        		<div name="outter">
        			<label for="text">问卷：</label><input type="file" id="uploadFile" class="file" name="file" accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/msword"/>
        		</div> 
        		<div name="outter">
        			<label for="text">评语：</label><input type="file" id="uploadExcel" class="file" name="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
        		</div> 
				<textarea id="description" name="description" placeholder="请输入50字左右的问卷描述"></textarea>
        	</form>
		</div>
		
		<div id="deleteDialog" style="display: none;">
			<h5>是否删除所选记录？</h5>
		</div>
		
		<div id="messageDialog" style="display: none;">
			<h5></h5>
		</div> 
		
		<div id="searchDialog" style="display: none;">
			<select id="field">
				<option value="number" selected="selected">学号</option>
				<option value="name">姓名</option>
			</select>
			<select id="op">
				<option value="eq" selected="selected">等于</option>
				<option value="ne">不等于</option>
				<option value="bw">开头是</option>
				<option value="bn">开头不是</option>
				<option value="ew">结尾是</option>
				<option value="en">结尾不是</option>
				<option value="cn">包含</option>
				<option value="nc">不包含</option>
			</select>
			<input id="data"/>
		</div>
    </div>
</body>
</html>