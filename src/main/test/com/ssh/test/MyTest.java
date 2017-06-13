package com.ssh.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssh.entity.User;
import com.ssh.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:applicationContext*.xml"
				})
public class MyTest {
	
	@Autowired
	private UserService userSerVice;
	
	@Test
	public void test() {
		System.out.println(userSerVice == null);
		System.out.println(123);
	}

}
