package com.ssh.util;


import org.springframework.security.core.GrantedAuthority;
/**
 * 获取授权
 * 
 * @author Jetvin
 *
 */
@SuppressWarnings("serial")
public class GetGrantedAuthority implements GrantedAuthority {
	private String authority = "ROLE_USER";
	
	public GetGrantedAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}
	

}
