package com.ssh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.dao.LoginDao;
import com.ssh.entity.Login;

@Service
@Transactional
public class LoginService implements ILogin {
	
	@Autowired
	LoginDao loginDao;
	
	@Override
	public void updateOrSavePassword(Login login) {
		// TODO Auto-generated method stub
		loginDao.save(login);
	}

	@Override
	public Login findByNumber(String number) {
		// TODO Auto-generated method stub
		return loginDao.findByNumber(number);
	}

}
