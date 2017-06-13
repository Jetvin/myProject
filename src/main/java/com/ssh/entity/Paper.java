package com.ssh.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Paper")
public class Paper implements Serializable{
	private String paperNumber;
	private String paperName;
	private String description;
	private List<Remark> remarks;
	private List<Question> questions;
	
	@Id
	public String getPaperNumber() {
		return paperNumber;
	}
	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="paperNumber")
	public List<Remark> getRemarks() {
		return remarks;
	}
	public void setRemarks(List<Remark> remarks) {
		this.remarks = remarks;
	}
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="paperNumber")
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
