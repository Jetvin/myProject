$(document).ready(function() {
	var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	var csrfHeader = $("meta[name='_csrf_header']").attr("content");  //没用到
	var csrfToken = $("meta[name='_csrf']").attr("content");
	var csrf_url = csrfParameter+"="+csrfToken;
	$(document).ajaxSend(function(e, xhr, options) {  
	    xhr.setRequestHeader(csrfHeader, csrfToken);
	    
	});
	
	
	// 标签页
	$(".tab").hide();
	//$("#tab_01_").show();
	

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
		v = "#"+v + "_";
		$(v).show();	
		//后台请求数据
		$.ajax({
			   async: false,
	           type: "POST",
	           url: "findResult",
	           data: {paperNumber:paperNumber},//参数列表
	           dataType:"json",
	           success: function(data){
	              //请求正确之后的操作
	        	   //alert("Success");
	        	   //有请求数据
	        	   if(data.message == null){
	        		   $(".remark p").text(data.remark);
		        	   $(v+'container').highcharts({
		       	        chart: {
		       	            type: 'column'
		       	        },
		       	        title: {
		       	            text: paperName
		       	        },
		       	        xAxis: {
		       	            categories: [
		       	                'A',
		       	                'B',
		       	                'C',
		       	                'D'
		       	            ],
		       	            crosshair: true
		       	        },
		       	        yAxis: {
		       	            min: 0,
		       	            max:100,
		       	            title: {
		       	                text: '百分比 (%)'
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
		       	                borderWidth: 0
		       	            }
		       	        },
		       	        series: [{
		       	            name: '比例',
		       	            data: [parseInt(data.A), parseInt(data.B), parseInt(data.C), parseInt(data.D)]
		       	        }],
		       	        credits:{
			   				enabled:false
			   			},
		       	    }); 

	        	   }
	        	 //无请求数据
	        	   else{
	        		   Highcharts.setOptions({
	        		        lang: {
	        		            noData: '暂无数据'
	        		        }
	        		    });
	        		   $(".remark p").text(data.remark);
	        		   $(v+'container').highcharts({
	        		        title: {
	        		            text: paperName
	        		        },
	        		        series: [{
	        		        	type: 'column',
	        		            name: '比例',
	        		            data: []
	        		        }],
	        		        credits:{
   				   				enabled:false
   				   			},
	        		    });   
	        	   }
	        	   
	           },
	           error: function(data){
	              //请求失败之后的操作
	        	   alert("无请求数据");
	           }
		}).responseText;
		
	});
})