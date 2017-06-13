package com.ssh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
/**
 * 认证失败处理
 * 1.显示认证失败理由
 * 2.跳转至相应响应页面
 * 
 * @author Jetvin
 *
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println(exception.getMessage());
		
		String message = exception.getMessage();
		
		//显示登录用户信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String encodePassword = encoder.encodePassword(password, password);
		System.out.println("认证失败 : " + message);
		System.out.println("AuthenticationFailureHandler 登录用户[ username: " + username + "\t password: " + encodePassword + " ]");
		
		//返回登录页面
		String targetUrl = "/login";
		if(message.equals("Maximum sessions of 1 for this principal exceeded")){
			request.getSession().setAttribute("message", "用户名在线");
		}
		else if(message.equals("Bad credentials")){
			request.getSession().setAttribute("message", "用户名或密码错误");
		}
		
		
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
}
