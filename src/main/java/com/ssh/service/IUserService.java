package com.ssh.service;

import com.ssh.entity.User;

public interface IUserService {
	public void updateOrSaveUser(User user);
	public User findByNumber(String number);
}
