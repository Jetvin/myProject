$(document).ready(function() {
	var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	var csrfHeader = $("meta[name='_csrf_header']").attr("content");  //没用到
	var csrfToken = $("meta[name='_csrf']").attr("content");
	var csrf_url = csrfParameter+"="+csrfToken;
	$(document).ajaxSend(function(e, xhr, options) {  
	    xhr.setRequestHeader(csrfHeader, csrfToken);
	    
	});
	
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
		var A=0;
		var B=0;
		var C=0;
		var D=0;
		
		$(".tab").hide();
		//柱形图的标题
		var paperNumber = $(this).attr("name");
		var paperName = $(this).text();
		//获取点击标签的id
		var v = $(this).attr("id");
		var n = v + "_";
		v = "#" + v + "_";
		
		$(v+'div').children().remove();
		$(v).show();
//		$("#messageDialog").dialog('open');
		

		$.ajax({
			   async: true,
	           type: "POST",
	           url: "findAllResult",
	           data: {paperNumber:paperNumber},//参数列表
	           dataType:"json",
	           success: function(data){
	              //请求正确之后的操作
	        	  //alert("Success");
	        	   //console.log(data.length);
	        	   /** 显示查询结果 begin**/
	        	   if(data.length > 0){
	        		   for(var i = 0; i < data.length; i++){
		        		   var A = data[i].A;
		        		   var B = data[i].B;
		        		   var C = data[i].C;
		        		   var D = data[i].D;
		        		   //console.log(A + "-" + B + "-" + C + "-" + D);
		        		   var div = $('<div></div>'); //创建一个子DIV
		       			   div.attr('id', n+'div'+i); //给子DIV设置ID
//		       			   div.removeClass();
		       			   div.css({
		       				   'margin' : '8px auto',
		       				   'padding' : '0',
		       				   'width' : '760px',
		       				   'height' : '220px',
		       				   'background-color' : '#fff',
		       				   'border-radius;' : '5px',  
		       			   });
		       			   div.appendTo($(v+'div'));
		       			   //console.log(testNumber);
		       			   $.ajax({
		       				   	async: false,
		       				   	type: "POST",
		       				   	url: "readTestQuestion",
		       				   	data: {paperNumber:paperNumber},//参数列表
		       				   	dataType:"json",
		       				   	success:function(data){
		       				   		$('#'+n+'div'+i).highcharts({
		       				   			chart: {
		       				   				type: 'bar',
		       				   				height:220,
		       				   				width:760
		       				   			},
		       				   			title: {
		       				   				text: data[i].question,
		       				   				style:{"fontSize": "12px"},
		       				   				align:"left"
		       				   			},
		       				   			xAxis: {
		       				   				categories: ['A','B','C','D'],
		       				   				crosshair: true
		       				   			},
		       				   			yAxis: {
		       				   				min: 0,
		       				   				max: 100,
		       				   				title: {
		       				   					text: 'A: '+ data[i].answerA
		       				   						+ ' B: '+ data[i].answerB
		       				   						+ ' C: '+ data[i].answerC
		       				   						+ ' D: '+ data[i].answerD
		       				   				}
		       				   			},
		       				   			tooltip: {
		       				   				headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		       				   				pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		       				   				'<td style="padding:0"><b>{point.y}</b></td></tr>',
		       				   				footerFormat: '</table>',
		       				   				shared: true,
		       				   				useHTML: true
		       				   			},
		       				   			plotOptions: {
		       				   				column: {
		       				   					pointPadding: 0.2,
		       				   					borderWidth: 0,
		       				   				}
		       				   			},
		       				   			series: [{
		       				   				name: '人数',
		       				   				data: [A, B, C, D],
		       				   			}],
		       				   			credits:{
		       				   				enabled:false
		       				   			},
		       				   		});
		       				   	},
		       				   	error: function(data){
		       				   		//请求失败之后的操作
		       				   		//alert("请求失败");
		       				   	}
		       			   });
		        	   
	        		   }
	        	   }
	        	   /** 显示查询结果 end**/
	        	   
	        	   /** 无查询结果显示 begin**/
	        	   else{
	        		   var div = $('<div></div>'); //创建一个子DIV
	       			   div.attr('id', n+'div_'); //给子DIV设置ID
	       			   div.removeClass();
	       			   div.css({
	       				   'margin' : '8px auto',
	       				   'padding' : '0',
	       				   'width' : '760px',
	       				   'height' : '300px',
	       				   'background-color' : '#fff',
	       				   'border-radius;' : '5px',  
	       			   });
	       			   div.appendTo($(v+'div'));
	       			   Highcharts.setOptions({
	       				   lang: {
	       					   noData: '暂无数据'
	       				   }
	       			   });
	       			   $(v + 'div_').highcharts({
	       				   title: {
	       					   text: paperName
	       				   },
	       				   series: [{
	       					   type: 'column',
	       					   name: '人数',
	       					   data: []
	       				   }],
	       				   credits:{
	       					   enabled:false
	       				   },
	       			   });
	        	   }
	        	   /** 无查询结果显示 end**/
	        	   
	           },
	           error: function(data){
	              //请求失败之后的操作
	        	  //alert("请求失败");
	           }
		}).responseText;
//		$("#messageDialog").dialog('close');
	});
})