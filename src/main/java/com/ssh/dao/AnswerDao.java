package com.ssh.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssh.entity.Answer;
import com.ssh.entity.UserQueryDTO;

@Repository
public interface AnswerDao extends PagingAndSortingRepository<Answer, Long>,JpaSpecificationExecutor<Answer>{
	
	@Query(value="from Answer where number = :number and paperNumber = :paperNumber")
	public List<Answer> findResult(@Param("number") String number, @Param("paperNumber") String paperNumber);
	
	@Query(value="from Answer where paperNumber = :paperNumber Order By questionNumber")
	public List<Answer> findAllResult(@Param("paperNumber") String paperNumber);
	
//	@Query(value="")
//	public Page<Answer> findAll(List<UserQueryDTO>,Pageable pageable);
}
