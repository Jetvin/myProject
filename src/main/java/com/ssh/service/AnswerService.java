package com.ssh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.dao.AnswerDao;
import com.ssh.entity.Answer;
import com.ssh.entity.UserQueryDTO;
import com.ssh.util.PageBean;

@Service
@Transactional
public class AnswerService implements IAnswerService {
	
	@Autowired
	AnswerDao answerDao;
	
	/** 学生调用 **/
	@Override
	public void saveAnswer(Answer answer) {
		answerDao.save(answer);	
	}
	@Override
	public List<Answer> findResult(String number ,String paperNumber) {
		// TODO Auto-generated method stub
		return answerDao.findResult(number,paperNumber);
	}
	
	/** 教师调用 **/
	@Override
	public List<Answer> findAllResult(String paperNumber) {
		// TODO Auto-generated method stub
		return answerDao.findAllResult(paperNumber);
	}

	@Override
	public Page<Answer> findAllResult(Specification buildSpecification, Pageable pageable) {
		
		return answerDao.findAll(buildSpecification, pageable);
	}
	
}
