package com.ssh.entity;

public class UserQueryDTO {
	private String paperNumber;
	private String question;
	private String answer;
	
//	public UserQueryDTO(){
//		
//	}
//	public UserQueryDTO(String question,String answer) {
//		this.question = question;
//		this.answer = answer;
//	}
	
	@Override
	public String toString() {
		return "UserQueryDTO [paperNumber=" + paperNumber + ", question=" + question + ", answer=" + answer + "]";
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getPaperNumber() {
		return paperNumber;
	}
	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}
	
	
}
