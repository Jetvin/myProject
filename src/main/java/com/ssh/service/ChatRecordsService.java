package com.ssh.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.dao.ChatRecordsDao;
import com.ssh.entity.ChatRecords;

@Service
@Transactional
public class ChatRecordsService implements IChatRecordsService{
	
	@Autowired
	ChatRecordsDao chatRecordsDao;
	@Override
	public void save(ChatRecords chatRecords) {
		
		chatRecordsDao.save(chatRecords);
	}
	
	@Override
	public List<ChatRecords> findByNumber(String number) {
		
		return chatRecordsDao.findByNumber(number);
	}
	
	public List<ChatRecords> findByCondition(String fromId, String toId) {
		// TODO Auto-generated method stub
		return chatRecordsDao.findByCondition(fromId,toId);
	}

}
