package com.homepage.application.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.homepage.application.model.HomePage;

public interface IHomePageRepository extends JpaRepository<HomePage, Long>{

	@Query("SELECT u FROM HomePage u WHERE u.homePageId = ?1")
	public List<HomePage>  findByHomePageId(Long homePageId);
	
	@Query("SELECT u FROM HomePage u WHERE u.homePageCode = ?1")
	public HomePage findByHomePageCode(String homePageCode);

}
