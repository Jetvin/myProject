package com.ssh.util;

/**
 * 在教师根据答题情况时的查询用到
 * filed ：条件，即数据表中的列名
 * op ：操作
 * data ：数据，即查询条件
 * @author Jetvin
 *
 */
public class RuleBean {
	private String field;
	private String op;
	private String data;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
}
