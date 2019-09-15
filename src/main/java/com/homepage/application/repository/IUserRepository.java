package com.homepage.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.homepage.application.model.User;

public interface IUserRepository extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User u WHERE u.userId = ?1")
	public User findByUserId(String userId);

}
