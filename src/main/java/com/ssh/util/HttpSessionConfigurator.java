package com.ssh.util;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * 已弃用》》》》》》》》》》》》》》》》》》》
 * @author Jetvin
 *
 */
public class HttpSessionConfigurator extends Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		// TODO Auto-generated method stub
		super.modifyHandshake(sec, request, response);
		HttpSession httpSession = (HttpSession) request.getHttpSession();
	    sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
	}
	
	
}
