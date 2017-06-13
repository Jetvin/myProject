package com.ssh.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ssh.entity.User;
import com.ssh.util.SessionTimeoutException;

public class SessionTimeoutInterceptor implements HandlerInterceptor {
	private List<String> allowUrls;

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUrl = request.getRequestURI();

		for (String url : allowUrls) {
			if (requestUrl.endsWith(url)) {
				return true;
			}
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			return true;
		} else {
			
			throw new SessionTimeoutException();
		}	
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
