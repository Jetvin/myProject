package com.ssh.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import com.ssh.entity.User;
import com.ssh.util.SessionContent;

@Component
@Aspect
public class MyAspectService {
	
//	public MyAspectService(){
//		System.out.println("MyAspectService 初始化");
//	}
//	
//	public Boolean isAdmin() throws IOException{
//		User user = (User)SessionContent.getSession().getAttribute("user");
//		if(user != null){
//			String authority = user.getLogin().getAuthority();
//			if(authority.equals("ROLE_ADMIN") == true){
//				
//				return true;
//			}
//		}else{
//			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//			HttpServletRequest request = SessionContent.getRequest();
//			HttpServletResponse response = SessionContent.getResponse();
//			String url = "/sessionTimeout";
//			redirectStrategy.sendRedirect(request, response, url);
//			
//			return false;
//		}
//		
//		return false;
//	}
//	
//	public Boolean isCurrentUser(){
//		User user = (User)SessionContent.getSession().getAttribute("user");
//		HttpServletRequest request = SessionContent.getRequest();
//		Map<String, String[]> map = request.getParameterMap();
//		if(user != null){
//			String number = user.getNumber();
//			if(map.containsKey("number")){
//				if(map.get("number").equals(number)){
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	
//	String valuestring = "getAdminResultPage()"
//			+ " || getAdminconsultPage()"
//			+ " || getSearchPage()"
//			+ " || findAllResult()"
//			+ " || findAllPaper()"
//			+ " || findByCondition()"
//			+ " || upDateFile()"
//			+ " || upLoadFile()"
//			+ " || exportExcel()"
//			+ " || downExcel()"
//			+ " || deletePaper()"
//			+ " || findAllUser()"
//			+ " || searchByCondition()"
//			+ " || registry()"
//			+ " || readRegistryExcel()"
//			+ " || readRemarkExcel()"
//			+ " || deleteUser()"
//			+ " || resetPassword()"
//			;
//	
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.getAdminResultPage(..))")
//	public void getAdminResultPage(){
//		System.out.println("[url: '/t_result']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.getAdminconsultPage(..))")
//	public  void getAdminconsultPage(){
//		System.out.println("[url: '/t_consult']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.getSearchPage(..))")
//	public  void getSearchPage(){
//		System.out.println("[url: '/search']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.findAllResult(..))")
//	public  void findAllResult(){
//		System.out.println("[url: '/findAllResult']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.findAllPaper(..))")
//	public  void findAllPaper(){
//		System.out.println("[url: '/findAllPaper']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.findByCondition(..))")
//	public  void findByCondition(){
//		System.out.println("[url: '/findByCondition']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.upDateFile(..))")
//	public  void upDateFile(){
//		System.out.println("[url: '/upDateFile']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.upLoadFile(..))")
//	public  void upLoadFile(){
//		System.out.println("[url: '/upLoadFile']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.exportExcel(..))")
//	public  void exportExcel(){
//		System.out.println("[url: '/exportExcel']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.downExcel(..))")
//	public  void downExcel(){
//		System.out.println("[url: '/downExcel']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.deletePaper(..))")
//	public  void deletePaper(){
//		System.out.println("[url: '/deletePaper']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.findAllUser(..))")
//	public  void findAllUser(){
//		System.out.println("[url: '/findAllUser']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.searchByCondition(..))")
//	public  void searchByCondition(){
//		System.out.println("[url: '/searchByCondition']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.registry(..))")
//	public  void registry(){
//		System.out.println("[url: '/registry']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.readRegistryExcel(..))")
//	public  void readRegistryExcel(){
//		System.out.println("[url: '/readRegistryExcel']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.readRemarkExcel(..))")
//	public  void readRemarkExcel(){
//		System.out.println("[url: '/readRemarkExcel']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.deleteUser(..))")
//	public  void deleteUser(){
//		System.out.println("[url: '/deleteUser']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.resetPassword(..))")
//	public  void resetPassword(){
//		System.out.println("[url: '/resetPassword']");
//	}
//	/***********************分界线*******************************/
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.getUserResultPage(..))")
//	public  void getUserResultPage(){
//		System.out.println("[url: '/u_result']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.getUserconsultPage(..))")
//	public  void getUserconsultPage(){
//		System.out.println("[url: '/u_consult']");
//	}
//	@Pointcut(value="execution(* com.ssh.service.AspectFlag.findResult(..))")
//	public  void findResult(){
//		System.out.println("[url: '/findResult']");
//	}
//	
//	@Pointcut(value="getAdminResultPage()"
//			+ " || getAdminconsultPage()"
//			+ " || getSearchPage()"
//			+ " || findAllResult()"
//			+ " || findAllPaper()"
//			+ " || findByCondition()"
//			+ " || upDateFile()"
//			+ " || upLoadFile()"
//			+ " || exportExcel()"
//			+ " || downExcel()"
//			+ " || deletePaper()"
//			+ " || findAllUser()"
//			+ " || searchByCondition()"
//			+ " || registry()"
//			+ " || readRegistryExcel()"
//			+ " || readRemarkExcel()"
//			+ " || deleteUser()"
//			+ " || resetPassword()")
//	public void adminPointioncut(){};
//	
//	@Pointcut(value="getUserResultPage() || getUserconsultPage() || findResult()")
//	public void userPointcut(){};
//	
//	
//	@Around(value="adminPointioncut()")
//	public Object methodForAdmin(ProceedingJoinPoint point) throws Throwable{
//		if (isAdmin()) {
//
//			return point.proceed();
//		} else {
//
//			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//			HttpServletResponse response = SessionContent.getResponse();
//			HttpServletRequest request = SessionContent.getRequest();
//			String url = "/accessDenied";
//			redirectStrategy.sendRedirect(request, response, url);
//			return null;
//		}
//	}
//	
//	@Around(value="userPointcut()")
//	public Object methodForUser(ProceedingJoinPoint point) throws Throwable{
//		if(isAdmin()){
//			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//			HttpServletResponse response = SessionContent.getResponse();
//			HttpServletRequest request = SessionContent.getRequest();
//			String url = "/accessDenied";
//			redirectStrategy.sendRedirect(request, response, url);
//			return null;
//		}else {
//			return point.proceed();
//		}
//	}
}
