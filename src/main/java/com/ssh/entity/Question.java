package com.ssh.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="question")
public class Question implements Serializable{
	private String id;
	private String paperNumber;
	private String questionNumber;
	private String question;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPaperNumber() {
		return paperNumber;
	}
	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}
	public String getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswerA() {
		return answerA;
	}
	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}
	public String getAnswerB() {
		return answerB;
	}
	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}
	public String getAnswerC() {
		return answerC;
	}
	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}
	public String getAnswerD() {
		return answerD;
	}
	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", paperNumber=" + paperNumber + ", questionNumber=" + questionNumber
				+ ", question=" + question + ", answerA=" + answerA + ", answerB=" + answerB + ", answerC=" + answerC
				+ ", answerD=" + answerD + "]";
	}
	
	
}
