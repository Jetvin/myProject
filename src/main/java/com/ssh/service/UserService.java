package com.ssh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.dao.UserDao;
import com.ssh.entity.User;

@Service
@Transactional
public class UserService implements IUserService {
	
	@Autowired
	UserDao userDao;
	
	/** 学生调用 **/
	@Override
	public void updateOrSaveUser(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	@Override
	public User findByNumber(String number) {
		// TODO Auto-generated method stub
		return userDao.findByNumber(number);
	}

	/** 教师调用  **/
	
	public Page<User> findAllUser(Pageable pageable) {
		
		return userDao.findAll(pageable);
	}
	
	public void deleteUser(User user){
		
		userDao.delete(user);
	}

	public List<User> findAllUser(Specification buildSpecification) {
		// TODO 自动生成的方法存根
		
		return userDao.findAll(buildSpecification);
	}

	public List<User> findAllUser() {
		// TODO 自动生成的方法存根
		return (List<User>) userDao.findAll();
	}
}
