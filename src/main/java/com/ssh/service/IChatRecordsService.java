package com.ssh.service;

import java.util.List;

import com.ssh.entity.ChatRecords;

public interface IChatRecordsService {
	public void save(ChatRecords chatRecords);
	public List<ChatRecords> findByNumber(String number);
}
