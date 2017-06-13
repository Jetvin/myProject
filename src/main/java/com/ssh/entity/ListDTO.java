package com.ssh.entity;

import java.util.List;

public class ListDTO {
	List<String> header;
	List<User> users;
	public List<String> getHeader() {
		return header;
	}
	public void setHeader(List<String> header) {
		this.header = header;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
