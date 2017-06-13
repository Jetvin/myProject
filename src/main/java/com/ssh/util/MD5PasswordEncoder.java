package com.ssh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密算法
 * 2017-03-02
 * @author Jetvin
 *
 */
public class MD5PasswordEncoder {

	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	        'a', 'b', 'c', 'd', 'e', 'f' };

	public static String encodePassword(String password,String salt) throws Exception {
		
		//1.拼接加密字符串
		String saltPass = password + "{" + salt + "}";
		
		//2.指定加密算法 MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
        
		//3.将字符串转换为byte数组
		byte[] bytes = saltPass.getBytes("utf-8");
        byte[] digestBytes = digest.digest(bytes);
		
        //4.通过遍历digestBytes 生成32位的字符串；
	    final int nBytes = digestBytes.length;
	    char[] result = new char[2 * nBytes];
	    int j = 0;
	    for (int i = 0; i < nBytes; i++) {
	        
	    	//转换成16尽职字符
	    	//无符号右移4位，忽略符号位，空位都以0补齐
	        result[j++] = HEX[(0xF0 & digestBytes[i]) >>> 4];
	        result[j++] = HEX[(0x0F & digestBytes[i])];

	    }
	    
	    return new String(result);
	}
	
	public static void main(String[] args){
		try {
			
			System.out.println(encodePassword("admin", "admin"));
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
