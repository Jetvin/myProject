package com.ssh.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import com.alibaba.fastjson.JSON;
import com.ssh.entity.ChatRecords;
import com.ssh.entity.Flags;
import com.ssh.entity.Paper;
import com.ssh.entity.User;
import com.ssh.service.ChatRecordsService;
import com.ssh.service.LoginService;
import com.ssh.service.PaperService;
import com.ssh.service.SessionListener;
import com.ssh.service.UserService;



/** 
 * 登录认证处理
 * 处理结果：跳转至响应的授权资源页面
 * 
 * @author Jetvin
 *
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Autowired
	LoginService loginService;
	@Autowired
	UserService userService;
	@Autowired
	ChatRecordsService chatRecordsService;
	@Autowired
	PaperService paperService;

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		//显示登录用户信息
		String number = request.getParameter("username");
		String password = request.getParameter("password");
		
		//登录用户信息
		User user = userService.findByNumber(number);
		
		List<Paper> papers = paperService.findAllPaper();
		Map<String, Boolean> maps = new HashMap<>();
		//问卷填写情况
		Flags flags = user.getFlags();
		if(flags == null){
			flags = new Flags();
			flags.setNumber(user.getNumber());
			for(int i = 0 ; i < papers.size() ; i++){
				String key = papers.get(i).getPaperNumber();
				maps.put(key, false);
			}
			String json = (String) JSON.toJSONString(maps);
			flags.setFlags(json);
			request.getSession().setAttribute("flags", maps);
		}else{
			String json = flags.getFlags();
			maps = JSON.parseObject(json, Map.class);
			if(maps.size() < papers.size()){
				for(int i = maps.size() ; i < papers.size(); i++){
					String key = papers.get(i).getPaperNumber();
					maps.put(key, false);
				}
				flags.setFlags((String) JSON.toJSONString(maps));
				request.getSession().setAttribute("flags", maps);
			}else if(maps.size() > papers.size() ){
				Map<String, Boolean> tempMaps = new HashMap<>();
				for(int i = 0 ; i < papers.size() ; i++){
					String key = papers.get(i).getPaperNumber();
					if(maps.containsKey(key) == true){
						tempMaps.put(key, maps.get(key));
					}
				}
				flags.setFlags((String) JSON.toJSONString(tempMaps));
				request.getSession().setAttribute("flags", tempMaps);
			}
			else {
				request.getSession().setAttribute("flags", maps);
			}
		}
		user.setFlags(flags);
		userService.updateOrSaveUser(user);
		SessionListener.user = user;
		SessionListener.userService = userService;
		
		//假如session存map，要修改
		//request.getSession().setAttribute("user", user);
		
		//假如session存map，要修改
		request.getSession().setAttribute("userService", userService);
		request.getSession().setAttribute("chatRecordsService",chatRecordsService);
		request.getSession().setAttribute("papers", papers);
		
		System.out.println("AuthenticationSuccessHandler 认证成功");
		System.out.println("AuthenticationSuccessHandler 登录用户[ username: " + number + "\t password: " + password + " ]");
		

		/******************要修改***********************/
		try {
			//1.返回权限认证对应的url
			//String targetUrl = "/main?Thread="+MyDES.encrypt(number);
			
			String targetUrl = "/main";
			System.out.println("AuthenticationSuccessHandler url = '" + targetUrl + "'");
			
			//2.响应是否已提交
			if (response.isCommitted()) {
				System.out.println("Can't redirect");
				return;
			}
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("message", null);
			
			//3.重定向
			redirectStrategy.sendRedirect(request, response, targetUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
}
