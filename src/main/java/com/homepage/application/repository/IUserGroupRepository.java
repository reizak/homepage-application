package com.homepage.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.homepage.application.model.UserGroup;

public interface IUserGroupRepository extends JpaRepository<UserGroup, Long>{
	
	@Query("SELECT u FROM UserGroup u WHERE u.userGroupName = ?1 ")
	public UserGroup findByUserGroupName(String userGroupName);
	
}
