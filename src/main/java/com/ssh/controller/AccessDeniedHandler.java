package com.ssh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;
/**
 * 访问未经授权的资源时的处理；
 * 处理结果：跳转到accessAdenied.jsp页面
 * 
 * @author Jetvin
 *
 */
@Component
public class AccessDeniedHandler extends AccessDeniedHandlerImpl{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse respone, AccessDeniedException authentication)
			throws IOException, ServletException {
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		String accessDeniedUrl = "/accessDenied";
		
		if(respone.isCommitted()){
			System.out.println("Can't redirect");
			return;
		}
		redirectStrategy.sendRedirect(request, respone, accessDeniedUrl);
	}
	
}
