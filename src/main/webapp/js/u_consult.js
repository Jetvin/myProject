//	var Thread = document.getElementById('Thread').value;
	var echo_websocket;
	var toId;
	var wsUri = "ws://localhost:8080/PAsystem/websocket/"+number;
	var id;
	var chat_content;
	var scrollTop;

	function startWebSocket(){
		chat_content = document.getElementById("chat_content");
		scrollTop = $("#chat_content")[0].scrollHeight;
		$("#chat_content").scrollTop(scrollTop); 

        	//1.建立websocket对象
        	echo_websocket = new WebSocket(wsUri);
        	//2.建立连接
        	echo_websocket.onopen = function(evt){
        		console.log("echo_websocket");
        	}
        	//3.接收信息
        	echo_websocket.onmessage = function (evt) {
        		
        		writeToScreen(evt.data);
        		scrollTop = $("#chat_content")[0].scrollHeight;  
        		$("#chat_content").scrollTop(scrollTop); 
        	};
        	echo_websocket.onerror = function (evt) {
        		//发送失败的？连接失败的？
        		writeToScreen(evt.data);
        		echo_websocket.close();
        	};
        } 
        
        function send_echo() {
        	//发送信息
        	toId = id;
        	var content = $("#input").html();
        	if(content != ""){
        		var content = content;
        		var message = {toId:toId,content:content};
        		echo_websocket.send(JSON.stringify(message));
        		$("#input").html("");
        		
        	}else{
        		$("#messageDialog h5").text("不可发送空文本");
        		$("#messageDialog").dialog('open');
        	}

        }

        function writeToScreen(message) {
        	
        	var data = JSON.parse(message);
        	var fromName = data.fromName;
        	var time = data.time;
        	var fromId = data.fromId;
        	var toId = data.toId;
        	var flag = data.flag;
        	var content = data.content;
        	console.log("{ id:" +id+",fromId:"+fromId+",toId:"+toId+"}");
        	
        	//消息列表以隐藏
        	if($(".msglist").is(":hidden")){
        		if(flag == 'L'){
        			if(id == fromId){
            			//
            			$.ajax({
            				async : false,
            				type : 'POST',
            				url : 'findRecordsByCondition',
            				data : {toId : id , fromId : number},
            				dataType : 'json',
            				success:function(data){
            					console.log("u_consult : SUCCESS");
            				},
            				error:function(data){
            					console.log("u_consult : ERROR");
            				}
            			});
            			var imageName = "";
    					for(var j=0 ; j < icons.length ; j++){
    						var iconNumber = icons[j].iconNumber;
    						var iconName = icons[j].iconName;
    						if(iconNumber == fromId){
    							imageName = iconName;
    							break;
    						}
    					}
            			chat_content.innerHTML = chat_content.innerHTML + '<div class="chat_rec_l">'
            			+ '	<div><img src="image/'+imageName+'" ></div>'
            			+ ' <ul>'
            			+ '   <li class="chat_c_l">' + fromName+': '+content + '</li>'
            			+ '	  <li class="chat_t_l">' + time + '</li>'
            			+ '	</ul>'
            			+ '</div>';
            		}else{
            			$("i").show();
            		}
            	}
            	else if(flag == 'R'){
            		chat_content.innerHTML = chat_content.innerHTML + '<div class="chat_rec_r">'
            		+ '	<div><img src="image/'+usericon+'" ></div>'
            		+ ' <ul>'
            		+ '   <li class="chat_c_r">' + content +' :' + fromName + '</li>'
            		+ '	  <li class="chat_t_r">' + time + '</li>'
            		+ '	</ul>'
            		+ '</div>';
            	}
            }else{
            	window.location.reload();
            }
        }
        $(document).ready(function(){
        	startWebSocket();
        	
        	$("table").hide();
			$("#onion_table").show();
        	
        	$(".Onion").click(function(){
				$("table").hide();
				$("#onion_table").show();
			});

			$(".Tieba").click(function(){
				$("table").hide();
				$("#tieba_table").show();
			});

			$(".Weibo").click(function(){
				$("table").hide();
				$("#weibo_table").show();
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
        	
        	$(".chat_div").hide();
        	
        	/* 打开或关闭表情窗口 */
        	$(".tool_icon").click(function(){
        		if($("#tool_tip").is(":hidden")){
        			$("#tool_tip").show();
        		}else{
        			$("#tool_tip").hide();
        		}
        	});
        	
        	$("td").click(function(){
        		var new_html = $(this).html();
        		var old_html = $("#input").html();
        		$("#input").html(old_html + new_html);
        		$("#tool_tip").hide();
        	});

        	/* 打开聊天界面 */
        	$(".to_chat").click(function(){
        		$("#tool_tip").hide();
        		$(".msglist").hide();
        		$("i").hide();
        		id = $(this).attr('name');
        		console.log(icons);
        		$.ajax({
        			async : false,
        			type : 'POST',
        			url : 'findRecordsByCondition',
        			data : {toId : id , fromId : number},
        			dataType : 'json',
        			success : function(data){
        				console.log(data.length);
        				var html = "";
        				for(var i = 0 ; i < data.length ; i++){
        					var fromName = data[i].fromName;
        					var fromId = data[i].fromId;
        					var toId = data[i].toId;
        					var time = data[i].time;
        					var content = data[i].content;
        					var imageName = "";
        					for(var j=0 ; j < icons.length ; j++){
        						var iconNumber = icons[j].iconNumber;
        						var iconName = icons[j].iconName;
        						if(iconNumber == fromId){
        							imageName = iconName;
        							break;
        						}
        					}
        					if(toId == number){
        						html += '<div class="chat_rec_l">'
        						+ '<div><img src="image/'+imageName+'" ></div>'
        						+ ' <ul>'
        						+ '   <li class="chat_c_l">' + fromName+': '+content + '</li>'
        						+ '	  <li class="chat_t_l">' + time + '</li>'
        						+ '	</ul>'
        						+ '</div>';
        					}else{
        						html += '<div class="chat_rec_r">'
        						+ '	<div><img src="image/'+usericon+'" ></div>'
        						+ ' <ul>'
        						+ '   <li class="chat_c_r">' + content +' :' + fromName + '</li>'
        						+ '	  <li class="chat_t_r">' + time + '</li>'
        						+ '	</ul>'
        						+ '</div>';
        					}
        				}
        				$('#chat_content').html(html);
        				$('.title_span').html('与&nbsp;&nbsp;'+ id +'&nbsp;&nbsp;聊天中');
        				$(".chat_div").show();
        				var scrollTop = $("#chat_content")[0].scrollHeight;
        				$("#chat_content").scrollTop(scrollTop);

        			},
        			error : function(data){
        				alert("error");
        			}
        		});
        	});

        /* 聊天记录列表界面 */
        $(".return").click(function(){
        		window.location.reload();
        	});
    });