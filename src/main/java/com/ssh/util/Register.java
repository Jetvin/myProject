package com.ssh.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.ssh.entity.Login;
import com.ssh.entity.User;
import com.ssh.service.LoginService;
import com.ssh.service.UserService;
/**
 * 该类已弃用了。。。。。。。。。。。。。。。。。。
 * @author Jetvin
 *
 */
public class Register {
	
	public Register(LoginService loginService,UserService userService) throws IOException{
		String path = this.getClass().getClassLoader().getResource("register.txt").getPath();
		InputStream in = new FileInputStream(path);
		InputStreamReader fr = new InputStreamReader(in, "utf-8");
		BufferedReader reader = new BufferedReader(fr);
		String s = null;
		while((s=reader.readLine()) != null){
			String[] split = s.split("#");
			String number = split[0];
			String name = split[1];
			String authority = split[2];
			Login login = loginService.findByNumber(number);
			
			if(login == null){
				login = new Login();
				Md5PasswordEncoder encoder = new Md5PasswordEncoder();
//				String password = encoder.encodePassword(password, username);
				String password = encoder.encodePassword(number, number);
				login.setNumber(number);
				login.setPassword(password);
				login.setAuthority(authority);
				loginService.updateOrSavePassword(login);
				User user = new User();
				user.setNumber(number);
				user.setName(name);
				userService.updateOrSaveUser(user);
				
			}
			
		}
		reader.close();
		fr.close();
		in.close();
	}
}
