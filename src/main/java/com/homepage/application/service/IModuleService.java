package com.homepage.application.service;

import java.util.List;

import com.homepage.application.model.Module;

public interface IModuleService {

	public Module findByUserGroupNameAndModuleOrderAndModuleName(String userGroupName, int moduleOrder ,String moduleName);
	
	public long countByUserGroupName(String userGroupName);
	
	public List<Module> findByuserGroupName(String userGroupName);
	
	public Module findByUserGroupNameAndModuleOrder(String userGroupName, int moduleOrder);
	
	public Module findByUserGroupNameAndModuleName(String userGroupName, String moduleName);

	public void deleteModule(Module module);

	public Module createModule(Module module);

	public Module updateModule(Module module);

	public List<Module> getAllModule();
	

}
