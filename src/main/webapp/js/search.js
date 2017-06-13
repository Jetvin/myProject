$(document).ready(function() {
	var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	var csrfHeader = $("meta[name='_csrf_header']").attr("content");  //没用到
	var csrfToken = $("meta[name='_csrf']").attr("content");
	var csrf_url = csrfParameter+"="+csrfToken;
	$(document).ajaxSend(function(e, xhr, options) {  
	    xhr.setRequestHeader(csrfHeader, csrfToken);
	    
	});
	
	var paperNumber;
	var count;
	var v;
	var n;

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
	
	// 标签页
	$(".tab").hide();

	$(".a").click(function() {
		count = 1;
		var A=0;
		var B=0;
		var C=0;
		var D=0;
		var exportData;
		
		$(".tab").hide();
		//柱形图的标题
		paperNumber = $(this).attr("name");
		//获取点击标签的id
		v = $(this).attr("id");
		
		/* "tab_0?_" */
		n = v + "_";
		/* "#tab_0?_" */
		v = "#" + v + "_";

		
		/*****	初始化查询条件  begin	*****/
		$(v+"condition").children().remove();
		var form = $('<form></form>'); 
		// "tab_0?_condition?"
		form.attr('id',n + 'condition1');
		console.log("ADD : " + n + 'condition1');
		// "#tab_0?_condition"
		var id = v+'condition';
		form.appendTo($(id));
		// 创建一个子select question
		var select = $('<select></select>');
		// "tab_0?_question?"
		select.attr('id', n + 'question1');
		select.attr('name','question');
		select.attr('class','question');
		select.appendTo($(id + '1'));
		select = $('<select></select>');
		select.attr('id', n + 'answer1');
		select.attr('name','answer');
		select.appendTo($(id + '1'));
		flag = true;
		$.ajax({
			async : false,
			type : "POST",
			url : "readTestQuestion",
			data : {paperNumber : paperNumber},// 参数列表
			dataType : "json",
			success:function(data){
				
				for(var i = 0 ; i < data.length ; i++){
					//添加问题option
					var option = $('<option></option>');
					var qid = v+'question1';
					option.attr('value',i+1);
					option.text(data[i].question);
					option.appendTo($(qid));
					if(i == 0){
						var qid = v+'answer1';
						var option = $('<option></option>');
						option.attr('value','A');
						option.text(data[i].answerA);
						option.appendTo($(qid));
						option = $('<option></option>');
						option.attr('value','B');
						option.text(data[i].answerB);
						option.appendTo($(qid));
						option = $('<option></option>');
						option.attr('value','C');
						option.text(data[i].answerC);
						option.appendTo($(qid));
						option = $('<option></option>');
						option.attr('value','D');
						option.text(data[i].answerD);
						option.appendTo($(qid));
					}
				}
			},
			error:function(data){
				$("#messageDialog h5").text("ERROR");
				$("#messageDialog").dialog('open');
			}
		});
		
		$(v).show();
		/*****	初始化查询条件  end	*****/
		
		/*****	初始化jqGrid  begin	*****/
		$(v+"Grid").clearGridData();
		//$(v+'Grid').jqGrid('navButtonDel', v+'Toolbar');
		$(v+'Grid').jqGrid({
            hidegrid : false,
            datatype : "local",
            height : 217,
            width: 1070,
            colNames : [ '学号', '姓名', '性别', '年级', '学院','专业', '电话' ],
            colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
                         {name : 'number',index : 'number',width : 150,align : "center"}, 
                         {name : 'name',index : 'name',width : 100,align : "center"}, 
                         {name : 'sex',index : 'sex',width : 50,align : "center"}, 
                         {name : 'grade',index : 'grade',width : 100,align : "center"}, 
                         {name : 'institute',index : 'institute',width : 150,align : "center"}, 
                         {name : 'major',index : 'major',width : 150,align : "center"}, 
                         {name : 'tel',index : 'tel',width : 200,align : "center"} 
                       ],
            rownumbers : true,
            //multiselect : true,
            toolbar : [ true, "top" ],
            rowNum : 10,
            rowList : [ 10, 20, 30, 50, 100, 200, 500 ],
            pager : v+'Toolbar',
            sortname : 'number',
            sortorder : "asc",
            viewrecords : true,
            jsonReader : {
            	repeatitems: false,
                total : "total",
                records : "records",
                cell : "cell",
                root: "rows"
            },
            caption : "学生信息表",
            gridComplete:function(){
                $(v+"Grid").parents(".ui-jqgrid-bdiv").css("overflow-x","hidden");
            }
        });
		$(v+'Grid').jqGrid('navGrid', v+'Toolbar', {
			add : false,
			edit : false,
			view : false,
			del : false,
			search : false,
			refresh : false
		});
		
		$(v+'Grid').jqGrid('navButtonAdd', v+'Toolbar', {
			id: n+'ToolbarBtn',
			caption : "",
			title : "导出Excel",
			buttonicon : 'ui-icon-folder-collapsed',
			onClickButton : function() {
				var obj = $(v+'Grid').jqGrid("getRowData");
				var colNames=$(v+'Grid').jqGrid('getGridParam','colNames')
				var listDTO = [{header:colNames,users:obj}];
				listDTO = JSON.stringify(listDTO);
				
				colNames = JSON.stringify(colNames);
				obj = JSON.stringify(obj);
				console.log(colNames);
				console.log(obj);
				$.ajax({
					caach : false,
					async : false,
					type : "POST",
					url : "exportExcel",
					data : listDTO,// 参数列表
					dataType : "json",
					contentType: "application/json",
					success:function(data){
						if(data.filename != null){
							var iframe = document.createElement("iframe");
					        iframe.src = "downExcel?filename=" + data.filename;
					        iframe.style.display = "none";
					        document.body.appendChild(iframe);
						}
						
					},
					error:function(data){
						$("#messageDialog h5").text("ERROR");
						$("#messageDialog").dialog('open');
					}
				});
			}
		});
		/*****	初始化jqGrid  end	*****/
		
		/* 为现有的以及未来的具有add类的标签添加点击事件 */
		/* 1.7- 用live()方法，1.7+用on()方法*/
		/*****	添加事件  begin	*****/
		$('.search').off('click',v+'add');
		$('.search').on('click',v+'add',function(){
			console.log(v+"add");
			if(count < 3){
				// 创建一个子DIV
				count++;
				var form = $('<form></form>'); 
				// "tab_0?_condition?"
				form.attr('id',n + 'condition' + count);
				console.log("ADD : " + n + 'condition' + count);
				// "#tab_0?_condition"
				var id = v+'condition';
				form.appendTo($(id));
				// 创建一个子select question
				var select = $('<select></select>');
				// "tab_0?_question?"
				select.attr('id', n + 'question' + count);
				console.log("ADD : " + n + 'question' + count);
				select.attr('name','question');
				select.attr('class','question');
				select.appendTo($(id + count));
				flag = true;
				$.ajax({
					async : false,
					type : "POST",
					url : "readTestQuestion",
					data : {paperNumber : paperNumber},// 参数列表
					dataType : "json",
					success:function(data){
						
						for(var i = 0 ; i < data.length ; i++){
							//添加问题option
							var option = $('<option></option>');
							var qid = v+'question' + count;
							option.attr('value',i+1);
							option.text(data[i].question);
							option.appendTo($(qid));
						}
					},
					error:function(data){
						$("#messageDialog h5").text("ERROR");
						$("#messageDialog").dialog('open');
					}
				});
				select = $('<select></select>');
				select.attr('id', n + 'answer' + count);
				select.attr('name','answer');
				select.appendTo($(id + count));
				$.ajax({
					async : false,
					type : "POST",
					url : "readTestQuestion",
					data : {paperNumber : paperNumber},// 参数列表
					dataType : "json",
					success:function(data){
						//添加问题option
						var qid = v+'answer' + count;
						var option = $('<option></option>');
						option.attr('value','A');
						option.text(data[0].answerA);
						option.appendTo($(qid));
						option = $('<option></option>');
						option.attr('value','B');
						option.text(data[0].answerB);
						option.appendTo($(qid));
						option = $('<option></option>');
						option.attr('value','C');
						option.text(data[0].answerC);
						option.appendTo($(qid));
						option = $('<option></option>');
						option.attr('value','D');
						option.text(data[0].answerD);
						option.appendTo($(qid));	
					},
					error:function(data){
						$("#messageDialog h5").text("ERROR");
						$("#messageDialog").dialog('open');
					}
				})
			}else{
				$("#messageDialog h5").text("最多允许三个查询条件");
				$("#messageDialog").dialog('open');
			}	
		});
		/*****	查询事件  end	*****/
		
		/*****	改变事件  begin	*****/
		$(".condition").off('change','.question');
		$(".condition").on('change','.question',function(){
			var qid = '#'+$(this).attr('id');
			var c = qid.charAt(qid.length - 1);
			$(v+'answer'+c).remove();
			var val = $(qid).val();
			var select = $('<select></select>');
			// "tab_0?_question?"
			select.attr('id', n + 'answer' + c);
			select.attr('name','answer');
			select.appendTo($(id + c));
			$.ajax({
				async : false,
				type : "POST",
				url : "readTestQuestion",
				data : {paperNumber : paperNumber},// 参数列表
				dataType : "json",
				success:function(data){
					for(var i = 0 ; i < data.length ; i++){
						//添加问题option
						if(val == i+1){
							var qid = v+'answer' + c;
							var option = $('<option></option>');
							option.attr('value','A');
							option.text(data[i].answerA);
							option.appendTo($(qid));
							option = $('<option></option>');
							option.attr('value','B');
							option.text(data[i].answerB);
							option.appendTo($(qid));
							option = $('<option></option>');
							option.attr('value','C');
							option.text(data[i].answerC);
							option.appendTo($(qid));
							option = $('<option></option>');
							option.attr('value','D');
							option.text(data[i].answerD);
							option.appendTo($(qid));
							break;
						}
						
					}
				},
				error:function(data){
					$("#messageDialog h5").text("ERROR");
					$("#messageDialog").dialog('open');
				}
			});
			
		});
		/*****	改变事件  end	*****/
		
		/*****	删除事件  begin	*****/
		$('.search').off('click',v+"delete");
		$('.search').on('click',v+"delete",function(){
			if(count > 1){
				var rid = v+"condition"+(count--);
				console.log(rid);
				$(rid).remove();
			}
			else{
				$("#messageDialog h5").text("查询条件至少有一个");
				$("#messageDialog").dialog('open');
			}
		});
		/*****	删除事件  end	*****/
		
		/*****	查询事件  begin	*****/
		$('.search').off('click',v+"search_btn");
		$('.search').on('click',v+"search_btn",function(){
			var m = count;
			console.log("条件数："+m);
			var data = [];
			for(var i=1;i<=m;i++){
				data.push({"paperNumber":paperNumber,"question":$(v+"question"+i).val(),"answer":$(v+"answer"+i).val()});
				console.log("条件"+i + "-" + v+"question"+i + "-" + v+"answer"+i);
			}
			data = JSON.stringify(data);
			console.log("JSON长度"+data.length);
			console.log("JSON"+data);
			$.ajax({
				caach : false,
				async : false,
				type : "POST",
				url : "findByCondition",
				data : data,// 参数列表
				dataType : "json",
				contentType: "application/json",
				success:function(responsedata){
					console.log(responsedata);
					exportData = responsedata;
					console.log(JSON.stringify(exportData));
					$(v+"Grid").clearGridData();
					$(v+"Grid").jqGrid('setGridParam',{data : responsedata})
							  .trigger('reloadGrid', [{ page: 1}]);

				},
				error:function(data){
					$("#messageDialog h5").text("ERROR");
					$("#messageDialog").dialog('open');
				}
			});
		})
		/*****	查询事件  end	*****/
	});
})