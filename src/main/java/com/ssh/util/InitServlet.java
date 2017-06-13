package com.ssh.util;

import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.ssh.controller.MyWebSocket;

@SuppressWarnings("deprecation")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = -3163557381361759907L;
	private static HashMap<String, MyWebSocket> socketList;

	public void init(ServletConfig config) throws ServletException {
		// InitServlet.socketList = new ArrayList<MessageInbound>();
		InitServlet.socketList = new HashMap<String, MyWebSocket>();
		super.init(config);
		System.out.println("Server start");
	}

	public static HashMap<String, MyWebSocket> getSocketList() {
		return InitServlet.socketList;
	}
}
