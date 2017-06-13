package com.ssh.util;

import java.util.List;

public class FilterBean {
	private String groupOp;
	private List<RuleBean> rules;
	
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public List<RuleBean> getRules() {
		return rules;
	}
	public void setRules(List<RuleBean> rules) {
		this.rules = rules;
	}
	
	
}
