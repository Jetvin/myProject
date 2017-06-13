package com.ssh.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="login")
public class Login implements Serializable{
	private String number;
	private String password;
	private String authority;
	
	@Id
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String toString() {
		return "Login [number=" + number + ", password=" + password  + ", authority=" + authority
				+ "]";
	}
	
	
}
