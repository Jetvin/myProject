package com.ssh.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Remark implements Serializable{
	private String remarkNumber;
	private String paperNumber;
	private int score;
	private String remark;
	
	@Id
	public String getRemarkNumber() {
		return remarkNumber;
	}
	public void setRemarkNumber(String remarkNumber) {
		this.remarkNumber = remarkNumber;
	}
	public String getPaperNumber() {
		return paperNumber;
	}
	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
