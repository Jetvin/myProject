package com.ssh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ssh.entity.Paper;

public interface PaperDao extends PagingAndSortingRepository<Paper, Long> , JpaSpecificationExecutor<Paper>{

	public Paper findByPaperNumber(String paperNumber);
	
}
