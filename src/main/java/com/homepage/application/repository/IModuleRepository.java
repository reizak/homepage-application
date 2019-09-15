package com.homepage.application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homepage.application.model.Module;

public interface IModuleRepository extends JpaRepository<Module, Long>{
	
	@Query("SELECT u FROM Module u WHERE u.userGroupName = ?1 and u.moduleOrder = ?2 and u.moduleName = ?3")
	public Module findByUserGroupNameAndModuleOrderAndModuleName(String userGroupName, int moduleOrder ,String moduleName);
	
	@Query("SELECT count(u) FROM Module u WHERE u.userGroupName = ?1")
	public long countByUserGroupName(String userGroupName);
	
	@Query("SELECT u FROM Module u WHERE u.userGroupName = ?1")
	public List<Module> findByuserGroupName(String userGroupName);
	
	@Query("SELECT u FROM Module u WHERE u.userGroupName = ?1 and u.moduleOrder = ?2")
	public Module findByUserGroupNameAndModuleOrder(String userGroupName, int moduleOrder);
	
	@Query("SELECT u FROM Module u WHERE u.userGroupName = ?1 and u.moduleName = ?2")
	public Module findByUserGroupNameAndModuleName(String userGroupName, String moduleName);


}
