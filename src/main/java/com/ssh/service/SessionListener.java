package com.ssh.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import com.ssh.entity.Login;
import com.ssh.entity.User;
import com.ssh.util.SessionContent;

@WebListener
public class SessionListener  implements HttpSessionListener{
	static public User user;
	static public UserService userService;
	private String creatTime;
	private String destroyTime;
	
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		Date date = new Date();
		creatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println("creatTime = "+creatTime);
		System.out.println("sessionCreated " + event.getSession().getId());
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		Date date = new Date();
		destroyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println("destroyTime = "+destroyTime);
		System.out.println("sessionDestroyed");
	}

}
