package com.ssh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssh.entity.User;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> , JpaSpecificationExecutor<User>{
	
	@Query(value="From User where number = :number")
	public User findByNumber(@Param("number")String number);
	
}
