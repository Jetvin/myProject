package com.ssh.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.ssh.entity.ChatRecords;
import com.ssh.entity.User;
import com.ssh.service.ChatRecordsService;
import com.ssh.service.UserService;
import com.ssh.util.HttpSessionConfigurator;
import com.ssh.util.InitServlet;
 
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint(value="/websocket/{number}", configurator=HttpSessionConfigurator.class)
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    
    ChatRecordsService chatRecordsService;
    UserService userService;
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private HttpSession httpSession;
    
    private User user;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws Exception 
     */
    @OnOpen
    public void onOpen(@PathParam("number") String number,Session session, EndpointConfig config) throws Exception{
    	this.session = session;
    	this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.chatRecordsService = (ChatRecordsService)httpSession.getAttribute("chatRecordsService");
        this.userService = (UserService)httpSession.getAttribute("userService");
        System.out.println("在线用户：" + number);
        this.user = userService.findByNumber(number);
        
        //用来存放每个客户端对应的MyWebSocket对象
        InitServlet.getSocketList().put(number, this);
        
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    	System.out.println("=连接关闭=");
        //webSocketSet.remove(this);  //从set中删除
        //subOnlineCount();           //在线数减1    
        //System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @SuppressWarnings("unchecked")
	@OnMessage
    public void onMessage(String message, Session session) {
    	
    	//同步块，防止多个线程访问一个数据对象时，对数据造成的破坏
    	synchronized (this) {
    		System.out.println("来自客户端的封装消息:" + message);
            Map<String, String> msg = JSON.parseObject(message, Map.class);
            
            //上线用户集合类map
            HashMap<String, MyWebSocket> userMsgMap = InitServlet.getSocketList();
            
            String fromId = user.getNumber();
            String toId = msg.get("toId");
            String content = msg.get("content");
            String fromName = userService.findByNumber(fromId).getName();
            System.out.println("消息发往的ID:" + toId);
            System.out.println("消息的内容:" + content);
            
            MyWebSocket webSocket = userMsgMap.get(toId);
            Date date=new Date();
            DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String time=format.format(date);
            System.out.println(time);
            
            ChatRecords chatRecords = new ChatRecords();
            
            chatRecords.setFromName(fromName);
            chatRecords.setFromId(fromId);
            chatRecords.setToId(toId);
            chatRecords.setContent(content);
            chatRecords.setTime(time);
            chatRecords.setStatus("未读");
            
    		try {
    			chatRecordsService.save(chatRecords);
    			msg = new HashMap<>();
    			msg.put("fromName", fromName);
    			msg.put("fromId", fromId);
    			msg.put("toId", toId);
    			msg.put("content", content);
    			msg.put("time", time);
    			msg.put("flag", "R");
    			message = (String) JSON.toJSONString(msg);
    			
    			//发送方显示
    			this.sendMessage(message);
    			
    			httpSession.setAttribute("chatRecords", chatRecordsService.findByNumber(fromId));
    			if (webSocket != null) {
    				//接收方显示
    				msg.put("flag", "L");
    				message = (String) JSON.toJSONString(msg);
    				System.out.println("=webSocket=");
    				webSocket.sendMessage(message);
    			}
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

		}
     }
     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("=发生错误=");
        error.printStackTrace();
    }
     
    /**
     * 
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }
     
    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
