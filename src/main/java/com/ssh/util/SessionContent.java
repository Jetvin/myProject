package com.ssh.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Session容器
 * 被InitSessionContent在初始化是调用setter方法对 requestLocal 和  responseLocal赋值
 * @author Jetvin
 *
 */
public class SessionContent {  
    private static ThreadLocal<HttpServletRequest> requestLocal= new ThreadLocal<HttpServletRequest>();  
    private static ThreadLocal<HttpServletResponse> responseLocal= new ThreadLocal<HttpServletResponse>();  
      
   public static HttpServletRequest getRequest() {  
       return (HttpServletRequest)requestLocal.get();  
   }  
   public static void setRequest(HttpServletRequest request) {  
       requestLocal.set(request);  
   }  
   public static HttpServletResponse getResponse() {  
       return (HttpServletResponse)responseLocal.get();  
   }  
   public static void setResponse(HttpServletResponse response) {  
       responseLocal.set(response);  
   }  
   public static HttpSession getSession() {  
       return (HttpSession)((HttpServletRequest)requestLocal.get()).getSession();  
   }  
}
