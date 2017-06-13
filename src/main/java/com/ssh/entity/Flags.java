package com.ssh.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="flags")
public class Flags implements Serializable{
	private String number;
	private String flags;
	
	@Id
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	
	
}
