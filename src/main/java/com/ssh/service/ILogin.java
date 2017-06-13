package com.ssh.service;

import com.ssh.entity.Login;

public interface ILogin {
	public void updateOrSavePassword(Login login);
	public Login findByNumber(String number);
	
}
