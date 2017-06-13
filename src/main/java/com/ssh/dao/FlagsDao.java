package com.ssh.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssh.entity.Flags;

@Repository
public interface FlagsDao extends PagingAndSortingRepository<Flags, Long>{
	
	@Query(value="From Flags where number = :number")
	public Flags findByNumber(@Param("number") String number);
}
