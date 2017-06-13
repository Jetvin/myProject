package com.ssh.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class User implements Serializable{
	
	private String number;
	private String name;
	private String sex;
	private String borth;
	private String grade;
	private String major;
	private String institute;
	private String addr;
	private String tel;
	private String qq;
	private Login login;
	private Flags flags;
	private List<Answer> answers;
	
	
	@Id
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBorth() {
		return borth;
	}
	public void setBorth(String borth) {
		this.borth = borth;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="number") 
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="number")
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="number")
	public Flags getFlags() {
		return flags;
	}
	public void setFlags(Flags flags) {
		this.flags = flags;
	}
	
	
	@Override
	public String toString() {
		return "User [number=" + number + ", name=" + name + ", sex=" + sex + ", borth=" + borth + ", grade=" + grade
				+ ", major=" + major + ", institute=" + institute + ", address=" + addr + ", tel=" + tel + ", qq="
				+ qq + ", login=" + login + ", Flags=" + flags + ", answwers=" + answers + "]";
	}
	
}
