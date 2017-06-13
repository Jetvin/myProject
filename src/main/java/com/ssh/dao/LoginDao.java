package com.ssh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssh.entity.Login;

@Repository
public interface LoginDao extends PagingAndSortingRepository<Login, Long>,JpaSpecificationExecutor<Login> {

	@Query(value="From Login Where number = :number")
	public Login findByNumber(@Param("number") String number);

}
