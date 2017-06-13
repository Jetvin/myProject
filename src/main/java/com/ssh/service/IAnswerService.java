package com.ssh.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ssh.entity.Answer;

public interface IAnswerService {
	public void saveAnswer(Answer answer);
	public List<Answer> findResult(String number,String testNumber);
	public List<Answer> findAllResult(String testNumber);
	public Page<Answer> findAllResult(Specification buildSpecification, Pageable pageable);
}
