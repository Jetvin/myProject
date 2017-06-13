package com.ssh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ssh.entity.ChatRecords;

public interface ChatRecordsDao extends PagingAndSortingRepository<ChatRecords, Long> ,JpaSpecificationExecutor<ChatRecords>{

	@Query(value="From ChatRecords Where fromid = :number or toId =:number Order By time")
	public List<ChatRecords> findByNumber(@Param("number") String number);

	@Query(value="FROM ChatRecords WHERE (fromId = :fromId AND toId = :toId) OR (fromId = :toId  AND toId = :fromId) ORDER BY time")
	public List<ChatRecords> findByCondition(@Param("fromId") String fromId,@Param("toId") String toId);
}
