package com.ssh.service;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssh.entity.Login;
import com.ssh.util.GetGrantedAuthority;

@Service
public class myUserDetailService implements UserDetailsService {
	@Autowired
	LoginService loginService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		Login login = loginService.findByNumber(username);
		String number = "default";
		String password = "default";
		String authority = "ROLE_USER";
		
		if(login != null){
			System.out.println("UserDetailService 用户存在");
			number = login.getNumber();
			password = login.getPassword();
			authority = login.getAuthority();
		}
		else{
			System.out.println("UserDetailService 用户不存在");
		}
		
		//获取权限
		GrantedAuthority grantedAuthority = new GetGrantedAuthority(authority);
		authorities.add(grantedAuthority);

		//授权用户
		UserDetails userDetails = new User(number, password, authorities);
		
		System.out.println("UserDetailService 授权用户 [username:" + number + " password:" + password + " authority:"+authority+"]");
		return userDetails;
	}

}
