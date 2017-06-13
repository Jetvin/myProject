package com.ssh.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session过滤器，用于保存Seesion，便于在无法获得Session的方法中可以获得Session
 * @author Jetvin
 *
 */
public class InitSessionContent implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		SessionContent.setRequest((HttpServletRequest) request);
		SessionContent.setResponse((HttpServletResponse) response);
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
