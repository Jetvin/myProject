package com.ssh.util;

/**
 * 信息工具类
 * success ：操作结果，true为成功，false为失败
 * message ：提示信息
 * @author Jetvin
 *
 */
public class ResultMessage {
	private boolean success;
	private String message;
	
	public ResultMessage(boolean success,String message){
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
