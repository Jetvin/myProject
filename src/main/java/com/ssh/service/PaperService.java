package com.ssh.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssh.dao.PaperDao;
import com.ssh.entity.Paper;

@Service
@Transactional
public class PaperService {
	
	@Autowired
	PaperDao paperDao;
	
	/** 学生调用 **/
	public Page<Paper> findAllPaper(Pageable pageable){
		
		return paperDao.findAll(pageable);
	}
	
	public List<Paper> findAllPaper(){
		
		return (List<Paper>) paperDao.findAll();
	}
	
	/** 教师调用  **/
	
	public Paper findByPaperNumber(String paperNumber){
		
		return paperDao.findByPaperNumber(paperNumber);
	}
	
	public void saveOrUpdate(Paper paper){
		
		paperDao.save(paper);
	}

	public void deleteByPaperNumber(String paperNumber) {
		Paper paper = paperDao.findByPaperNumber(paperNumber);
		paperDao.delete(paper);
	}
}
