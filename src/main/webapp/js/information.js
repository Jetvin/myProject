var paperNumber;
$(document).ready(function() {
	var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	var csrfHeader = $("meta[name='_csrf_header']").attr("content");  //没用到
	var csrfToken = $("meta[name='_csrf']").attr("content");
	var csrf_url = csrfParameter+"="+csrfToken;
	
	$(document).ajaxSend(function(e, xhr, options) {  
	    xhr.setRequestHeader(csrfHeader, csrfToken);
	    
	});
	
	/** 标签展开关闭 begin **/
	$(".tab").hide();
	$("#tab_01_").show();

	$(".a").click(function() {
		$(".tab").hide();
		var v = $(this).attr("id");
		v = "#" + v + "_";
		$(v).show();
	});
	/** 标签展开关闭 end **/
	
	/** 日期选择器 begin **/
	// 本地化
	var i18n = { 
		previousMonth : '上个月',
		nextMonth : '下个月',
		months : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月','十月', '十一月', '十二月' ],
		weekdays : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
		weekdaysShort : [ '日', '一', '二', '三', '四', '五', '六' ]
	}
	moment.locale("zh-cn");
	var picker = new Pikaday({
		field : document.getElementById('borth'),
		minDate:	new Date('1985-01-01'), 
		maxDate:	new Date('2020-12-31'), 
		yearRange:	[1985,2020],
		i18n: 		i18n,
		format:    'YYYY-MM-DD',
        onSelect: function() {
        	var date = document.createTextNode(this.getMoment().format('YYYY-MM-DD') + ' '); //生成的时间格式化成 2013-09-25
        	console.log(date);
        }
	});
	/** 日期选择器 end **/
	
	/** 密码修改 begin **/
	$("#pwd_submit").click(function(){
		var old_pwd = $("#old_pwd").val();
		var fir_pwd = $("#fir_pwd").val();
		var sec_pwd = $("#sec_pwd").val();
		console.log(old_pwd+"-"+fir_pwd+"-"+sec_pwd);
	    $("#old_pwd").val("");
	    $("#fir_pwd").val("");
	    $("#sec_pwd").val("");
	    $("#message1").text("");
	    $("#message2").text("");
		if(old_pwd == ""){
			$("#messageDialog h5").text("旧密码不可为空");
			$("#messageDialog").dialog('open');
		}else if(fir_pwd == ""){
			$("#messageDialog h5").text("新密码不可为空");
			$("#messageDialog").dialog('open');
		}else if(sec_pwd == ""){
			$("#messageDialog h5").text("确认密码不可为空");
			$("#messageDialog").dialog('open');
		}else if(fir_pwd.length < 8){
			$("#messageDialog h5").text("新密码长度过短");
			$("#messageDialog").dialog('open');
		}else if(fir_pwd.length > 12){
			$("#messageDialog h5").text("新密码长度过长");
			$("#messageDialog").dialog('open');
		}else if(sec_pwd.length < 8){
			$("#messageDialog h5").text("确认密码长度过短");
			$("#messageDialog").dialog('open');
		}else if(sec_pwd.length > 12){
			$("#messageDialog h5").text("确认密码长度过长");
			$("#messageDialog").dialog('open');
		}else if(fir_pwd != sec_pwd){
			$("#messageDialog h5").text("密码不一致");
			$("#messageDialog").dialog('open');
		}else{
			$.ajax({
				   async: false,
		           type: "POST",
		           url: "modify_pwd",
		           data: {old_pwd:old_pwd,fir_pwd:fir_pwd,sec_pwd:sec_pwd},//参数列表
		           dataType:"json",
		           success: function(data){
		            //请求正确之后的操作
		        	$("#old_pwd").val("");
		       	    $("#fir_pwd").val("");
		       	    $("#sec_pwd").val("");
		              if(data.success){
				       	$("#messageDialog h5").text("密码修改成功，请重新登录");
						$("#messageDialog").dialog('open');
						window.location = "logout";
		              }else{
		              	$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
		              }
		        	  
		           },
		           error: function(data){
		            //请求失败之后的操作
		        	$("#old_pwd").val("");
		       	    $("#fir_pwd").val("");
		       	    $("#sec_pwd").val("");
		        	  	$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
		           }
			}).responseText;
		}
	});
	
	$("#fir_pwd").change(function(){
		var fir_pwd = $("#fir_pwd").val();
		
		if(fir_pwd.length < 8){
			$("#message1").text("密码长度过短");
		}else if(fir_pwd.length > 12){
			$("#message1").text("密码长度过长");
		}else{
			$("#message1").text("可使用");
		}
	});
	
	$("#sec_pwd").change(function(){
		var fir_pwd = $("#fir_pwd").val();
		var sec_pwd = $("#sec_pwd").val();
		if(sec_pwd.length < 8){
			$("#message2").text("密码长度过短");
		}else if(sec_pwd.length > 12){
			$("#message2").text("密码长度过长");
		}else if(fir_pwd != sec_pwd){
			$("#message2").text("密码不一致");
		}else{
			$("#message2").text("可使用");
		}
	});
	/** 密码修改 end **/
	
	/** 基本信息修改 begin **/
	$("#info_submit").click(function(){
		var name = $("#name").val();
		var sex = $("#sex").val();
		var borth = $("#borth").val();
		var grade = $("#grade").val();
		var institute = $("#institute").val();
		var major = $("#major").val();
		var formData = new FormData();
		formData.append("icon",$("#icon")[0].files[0]);
		formData.append("name",name);
		formData.append("sex",sex);
		formData.append("borth",borth);
		formData.append("grade",grade);
		formData.append("institute",institute);
		formData.append("major",major);
		$.ajaxUpload({
			   async: false,
	           type: "POST",
	           url: "modify_info",
	           data: formData,//参数列表
	           processData : false, // 告诉jQuery不要去处理发送的数据
				contentType : false,// 告诉jQuery不要去设置Content-Type请求头
	           dataType:"json",
	           success: function(data){
	              	//请求正确之后的操作
	               	$("#messageDialog h5").text(data.message);
					$("#messageDialog").dialog('open');
					 window.location.reload(); 
	           },
	           error: function(data){
	              	//请求失败之后的操作
	        	   	$("#messageDialog h5").text(data.message);
					$("#messageDialog").dialog('open');
					window.location.reload();
	           }
		}).responseText;
	});
	/** 基本信息修改 end **/
	
	/** 联系方式修改 begin **/
	$("#contact_submit").click(function(){
		var qq = $("#QQ").val();
		var tel = $("#tel").val();
		var addr = $("#addr").val();
		if(tel.length != 0){
			if(tel.length != 11 || tel.indexOf("1")){
				$("#messageDialog h5").text("请输入正确的手机号码");
				$("#messageDialog").dialog('open');
			}else{
				$.ajax({
					   async: false,
			           type: "POST",
			           url: "modify_contact",
			           data: {qq:qq,tel:tel,addr:addr},//参数列表
			           dataType:"json",
			           success: function(data){
			              	//请求正确之后的操作
			        	   	$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
			        	   
			           },
			           error: function(data){
			              	//请求失败之后的操作
			        	   	$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
			           }
				}).responseText;
			}
			
		}else{
			$.ajax({
				   async: false,
		           type: "POST",
		           url: "modify_contact",
		           data: {qq:qq,tel:tel,addr:addr},//参数列表
		           dataType:"json",
		           success: function(data){
		              	//请求正确之后的操作
		              	$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
		           },
		           error: function(data){
		              	//请求失败之后的操作
		        	   	$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
		           }
			}).responseText;
		}
	});
	/** 联系方式修改 end **/
	
	/** 试题维护表格 begin **/
	$('#Grid').jqGrid({
        hidegrid : false,
        url : "findAllPaper",
        datatype : "json",
        height : 390,
        width: 1087,
        colNames : [ '问卷编号', '问卷名称', '操作'],
        colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
                     {name : 'paperNumber',index : 'paperNumber',align : "center"}, 
                     {name : 'paperName',index : 'paperNumber',align : "left"}, 
                     {name : 'caozuo',index : 'caozuo',align : "center",
                    	 formatter: function (cellvalue, options, rowObject) {
                             var detail="<input type='button' value='更新' class onclick = 'updatePaper(\""+rowObject.paperNumber+"\")' style='width:100px;height:30px;margin:5px auto;margin-right:5px;' />"
                             		   +"<input type='button' value='删除' class onclick = 'deletePaper(\""+rowObject.paperNumber+"\")' style='width:100px;height:30px;margin:5px auto;' />";
                             return detail;
                         },}, 
                   ],
        rownumbers : true,
        //multiselect : true,
        toolbar : [ true, "top" ],
        rowNum : 10,
        pager : 'Toolbar',
        sortname : 'paperNumber',
        sortorder : "asc",
        viewrecords : true,
        jsonReader : {
        	repeatitems: false,
        	root : "content",
			total : "totalPages",
			records : "totalElements",
			cell : "numberOfElements"
        },
        caption : "问卷信息表",
        gridComplete:function(){
            $("#Grid").parents(".ui-jqgrid-bdiv").css("overflow-x","hidden");
        }
    });
	
	$('#Grid').jqGrid('navGrid', '#Toolbar', {
		add : false,
		edit : false,
		view : false,
		del : false,
		search : false,
		refresh : false
	});
	
	$('#Grid').jqGrid('navButtonAdd', '#Toolbar', {
		id: 'ToolbarBtn',
		caption : "",
		title : "添加试题",
		buttonicon : 'ui-icon-folder-open',
		onClickButton : function() {
			$("#upLoadDialog").dialog('open');
		}
	});
	/** 试题维护表格 end **/
	
	/** 对话框 begin **/
	$("#upDateDialog").dialog({
		autoOpen : false,
		modal: true,
		width : 500,   //弹出框宽度
		height : 370,   //弹出框高度
		title : "试题维护",  //弹出框标题
		show: {
	        effect: "explode",
	        duration: 1000
	      },
		hide: {
	        effect: "explode",
	        duration: 1000
	      },
		buttons:{
			'确定':function(){
				var description = $("#textarea").val();
				var formData = new FormData();
				formData.append("updateFile",$("#updateFile")[0].files[0]);
				formData.append("updateExcel",$("#updateExcel")[0].files[0]);
				formData.append("description",description);
				formData.append("paperNumber",paperNumber);
				$.ajaxUpload({ 
					url :  'upDateFile',
					type : 'POST', 
					data: formData,
					processData : false, // 告诉jQuery不要去处理发送的数据
					contentType : false,// 告诉jQuery不要去设置Content-Type请求头
					dataType:"json",
					success : function(data) {
						console.log("上传文件："+data.message);
						if(data.success){
							$("#upDateDialog").dialog('close');
							$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
						}
						else{
							$("#upDateDialog").dialog('close');
							$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
						}
					}, 
					error : function(data) { 
						console.log("上传文件："+data.message);
						$("#upDateDialog").dialog('close');
						$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
					} 
				});
			},
			'取消':function(){
				$(this).dialog("close");
				
			}
		}
	});
	
	$("#upLoadDialog").dialog({
		autoOpen : false,
		modal: true,
		width : 470,   //弹出框宽度
		height : 420,   //弹出框高度
		title : "试题添加",  //弹出框标题
		show: {
	        effect: "explode",
	        duration: 1000
	      },
		hide: {
	        effect: "explode",
	        duration: 1000
	      },
		buttons:{
			'确定':function(){
				var description = $("#description").val();
				paperNumber = $("#paperNumber").val();
				var formData = new FormData();
				formData.append("paperImage",$("#uploadImg")[0].files[0]);
				formData.append("paperFile",$("#uploadFile")[0].files[0]);
				formData.append("paperRemark",$("#uploadExcel")[0].files[0]);
				formData.append("description",description);
				formData.append("paperNumber",paperNumber);
				$.ajaxUpload({ 
					url :  'upLoadFile',
					type : 'POST', 
					data: formData,
					processData : false, // 告诉jQuery不要去处理发送的数据
					contentType : false,// 告诉jQuery不要去设置Content-Type请求头
					dataType:"json",
					success : function(data) {
						console.log("上传文件："+data.message);
						if(data.success){
							$("#upLoadDialog").dialog('close');
							$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
							$("#Grid").jqGrid('setGridParam',{url : 'findAllPaper'})
							  .trigger('reloadGrid', [{ page: 1}]);
						}else{
							$("#upLoadDialog").dialog('close');
							$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');		
						}
	
					}, 
					error : function(data) { 
						console.log("上传文件："+data.message);
						$("#upLoadDialog").dialog('close');
						$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
					}
				});
			},
			'取消':function(){
				$(this).dialog("close");
				
			}
		}
	});
	/** 对话框 end **/
	
	
	/** 用户注册 **/
	var numberList;
	$('#RegistryGrid').jqGrid({
        hidegrid : false,
        url : "findAllUser",
        datatype : "json",
        height : 390,
        width: 1087,
        colNames : [ '号码', '姓名', '操作'],
        colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式..... 
                     {name : 'number',index : 'number',align : "center",editable: true}, 
                     {name : 'name',index : 'name',align : "center",editable: true}, 
                     {name : 'caozuo',index : 'caozuo',align : "center",editable: false,
                    	 formatter: function (cellvalue, options, rowObject) {
                             var detail="<input type='button' value='密码重置' class onclick = 'resetPassword(\""+rowObject.number+"\")' style='width:100px;height:27px;margin:4px auto;margin-right:5px;' />";
                             return detail;
                         },}, 
                   ],
        rownumbers : true,
        multiselect : true,
        toolbar : [ true, "top" ],
        rowNum : 10,
        pager : 'RegistryToolbar',
        sortname : 'number',
        sortorder : "asc",
        viewrecords : true,
        jsonReader : {
        	repeatitems: false,
        	root : "content",
			total : "totalPages",
			records : "totalElements",
			cell : "numberOfElements"
        },
        caption : "用户列表",
        onSelectRow:function(){
        	var ids = $("#RegistryGrid").jqGrid('getGridParam','selarrrow');
        	console.log(ids.length);
        	var numberArr = [];
        	for(var i = 0 ; i < ids.length; i++){
        		var rowData = $("#RegistryGrid").jqGrid('getRowData',ids[i]);
        		numberArr.push(rowData.number);
        	}
        	console.log(numberArr);	
        	//numberList = numberArr;
        	numberList = JSON.stringify(numberArr);
        },
        gridComplete:function(){
            $("#RegistryGrid").parents(".ui-jqgrid-bdiv").css("overflow-x","hidden");
        }
    });
	
	$('#RegistryGrid').jqGrid('navGrid', '#RegistryToolbar', {
		add : false,
		edit : false,
		del : true,
		search : false,
		refresh : false
	},{
		/* Edit*/
		
	},{
		/* Add*/
		
	},{
		/* Delete*/
		jqModal:true,
		modal: true,
		top : 200,
		left : 500,
		url : "deleteUser",
		onclickSubmit :function(params,postdata){
			/**
			 *  params：方法选择配置项目
			 *	postdata：被提交的数据
			 *	方法可以返回json键值对对象用于扩展postdata数据
			 */
			return {numberList:numberList};
		},
		afterSubmit : function(response, postdata) {
			/* 刷新数据 */
			var data = $.parseJSON(response.responseText);
			if (data.success) {
				$("#messageDialog h5").text(data.message);
				$("#messageDialog").dialog('open');
				$('#RegistryGrid').jqGrid('setGridParam', {
					url : 'findAllUser'
				}).trigger('reloadGrid', [ {
					page : 1
				} ]);

				return [ true , data.message ];
			} else {

				return [ false , data.message ];
			}

		}
	});
	
	
	$('#RegistryGrid').jqGrid('navButtonAdd', '#RegistryToolbar', {
		id: 'RegistryBtn',
		caption : "",
		title : "注册用户",
		buttonicon : 'ui-icon-folder-open',
		onClickButton : function() {
			$("#registryDialog").dialog('open');
		}
	});
	
	$("#registryDialog").dialog({
		autoOpen : false,
		modal: true,
		width : 400,   //弹出框宽度
		height : 200,   //弹出框高度
		title : "用户注册",  //弹出框标题
		show: {
	        effect: "explode",
	        duration: 1000
	      },
		hide: {
	        effect: "explode",
	        duration: 1000
	      },
		buttons:{
			'确定':function(){

				var formData = new FormData();
				formData.append("registryFile",$("#registryFile")[0].files[0]);
				$.ajaxUpload({ 
					url :  'registry',
					type : 'POST', 
					data: formData,
					processData : false, // 告诉jQuery不要去处理发送的数据
					contentType : false,// 告诉jQuery不要去设置Content-Type请求头
					dataType:"json",
					success : function(data) {
						console.log("注册用户："+data.message);
						if(data.success){
							$("#registryDialog").dialog('close');
							$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
							$("#RegistryGrid").jqGrid('setGridParam',{url : 'findAllUser'})
							  .trigger('reloadGrid', [{ page: 1}]);
						}else{
							$("#registryDialog").dialog('close');
							$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
						}
	
					}, 
					error : function(data) { 
						console.log("上传文件："+data.message);
						$("#registryDialog").dialog('close');
						$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
					} 
				});
			},
			'取消':function(){
				$(this).dialog("close");
				
			}
		}
	});
	/** 用户注册 **/
	
	/** 刷新按钮 **/
	$('#RegistryGrid').jqGrid('navButtonAdd', '#RegistryToolbar', {
		id: 'RefreshBtn',
		caption : "",
		title : "刷新",
		buttonicon : 'ui-icon-refresh',
		onClickButton : function() {
			$("#RegistryGrid").jqGrid('setGridParam',{datatype:'json',url : 'findAllUser'})
			  .trigger('reloadGrid', [{ page: 1}]);
		}
	});
	/** 刷新按钮 **/
	
	/** 查询按钮 **/
	$('#RegistryGrid').jqGrid('navButtonAdd', '#RegistryToolbar', {
		id: 'SearchBtn',
		caption : "",
		title : "查询用户",
		buttonicon : 'ui-icon-search',
		onClickButton : function() {
			$("#searchDialog").dialog('open');
		}
	});
	/** 查询按钮 **/
	
	/** 查询对话框 **/
	$("#searchDialog").dialog({
		autoOpen : false,
		modal: true,
		width : 400,   //弹出框宽度
		height : 200,   //弹出框高度
		title : "用户查询",  //弹出框标题
		show: {
	        effect: "explode",
	        duration: 1000
	      },
		hide: {
	        effect: "explode",
	        duration: 1000
	      },
		buttons:{
			'确定':function(){
				var field = $("#field").val();
				var op = $("#op").val();
				var data = $("#data").val();
				$.ajax({
					   async: false,
			           type: "POST",
			           url: "searchByCondition",
			           data: {field:field,op:op,data:data},//参数列表
			           dataType:"json",
			           success: function(data){
			              	//请求正确之后的操作
			        	   	$("#searchDialog").dialog('close');
			        	   	$("#RegistryGrid").clearGridData();
			        	   	$("#RegistryGrid").jqGrid('setGridParam',{datatype:'local',data : data})
							  .trigger('reloadGrid', [{ page: 1}]);
			           },
			           error: function(data){
			              	//请求失败之后的操作
			        	   	$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
			           }
				})
				
			},
			'取消':function(){
				$(this).dialog("close");
				
			}
		}
	});
	/** 查询对话框 **/
	
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
				$(this).dialog("close");
			}
		}
	});
	/** 消息提示框 **/
	$("#deleteDialog").dialog({
		autoOpen : false,
		modal: true,
		width : 250,   //弹出框宽度
		height : 150,   //弹出框高度
		title : "删除提示",  //弹出框标题
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
				$.ajax({
					async: false,
			        type: "POST",
			        url: "deletePaper",
			        data: {paperNumber:paperNumber},//参数列表
			        dataType:"json",
			        success: function(data){
			           //请求正确之后的操作
			     	   if(data.success){
			     		   	$('#deleteDialog').dialog("close");
			     		   	$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
			     		  	$("#Grid").jqGrid('setGridParam',{url : 'findAllPaper'})
						  		.trigger('reloadGrid', [{ page: 1}]);
			     	   }else{
			     		   	$('#deleteDialog').dialog("close");
			     		   	$("#messageDialog h5").text(data.message);
							$("#messageDialog").dialog('open');
			     	   }
			        },
			        error: function(data){
			           	//请求失败之后的操作
			        	$('#deleteDialog').dialog("close");
			     	   	$("#messageDialog h5").text(data.message);
						$("#messageDialog").dialog('open');
			        }
				}).responseText;
			},
			'取消':function(){
				$(this).dialog("close");
			}
		}
	});
})

/** 更新问卷事件 **/
function updatePaper(id){
	paperNumber = id;
	$("#textarea").val("");
	$("#file").val("");
	$("#upDateDialog").dialog('open');
}
/** 头像预览事件 **/
function previewIcon(file)
{
  var width  = 50; 
  var height = 50;
  var div = document.getElementById('user_icon');
  if (file.files && file.files[0])
  {
	  div.innerHTML ='<img id=imghead>';
      var img = document.getElementById('imghead');
      img.onload = function(){
        var rect = {top:0, left:0, width:width, height:height};
        img.width  =  rect.width;
        img.height =  rect.height;
        img.style.marginTop = rect.top+'px';
      }
      var reader = new FileReader();
      reader.onload = function(evt){img.src = evt.target.result;}
      reader.readAsDataURL(file.files[0]);
  }
}
/** 图片预览事件 **/
function previewImage(file)
{
  var width  = 100; 
  var height = 100;
  var div = document.getElementById('img');
  if (file.files && file.files[0])
  {
	  div.innerHTML ='<img id=imghead>';
      var img = document.getElementById('imghead');
      img.onload = function(){
        var rect = {top:0, left:0, width:width, height:height};
        img.width  =  rect.width;
        img.height =  rect.height;
        img.style.marginTop = rect.top+'px';
      }
      var reader = new FileReader();
      reader.onload = function(evt){img.src = evt.target.result;}
      reader.readAsDataURL(file.files[0]);
  }
}

/** 删除问卷事件 **/
function deletePaper(id){
	paperNumber = id;	
	$("#deleteDialog").dialog('open');
}

/** 密码重置事件 **/
function resetPassword(number){
	$.ajax({
		async: false,
        type: "POST",
        url: "resetPassword",
        data: {number:number},//参数列表
        dataType:"json",
        success: function(data){
           //请求正确之后的操作
        	if(data.success){
        		$("#messageDialog h5").text(data.message);
				$("#messageDialog").dialog('open');
        	}
        	else{
        		$("#messageDialog h5").text(data.message);
				$("#messageDialog").dialog('open');
        	}
        	
        },
        error: function(data){
           	//请求失败之后的操作
     	   	$("#messageDialog h5").text(data.message);
			$("#messageDialog").dialog('open');
        }
	})
}