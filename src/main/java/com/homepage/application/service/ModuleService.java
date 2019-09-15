package com.homepage.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homepage.application.model.Module;
import com.homepage.application.repository.IModuleRepository;

@Service
public class ModuleService implements IModuleService{

	@Autowired
	IModuleRepository moduleRepository;

	@Override
	public List<Module> getAllModule() {
		return moduleRepository.findAll();
	}
	
	@Override
	public List<Module> findByuserGroupName(String userGroupName) {
        return moduleRepository.findByuserGroupName(userGroupName);
    }
	
	@Override
	public long countByUserGroupName(String userGroupName) {
        return moduleRepository.countByUserGroupName(userGroupName);
    }

	@Override
	public Module createModule(Module module) {
		return moduleRepository.save(module);
	}

	@Override
	public Module updateModule(Module module) {
		return moduleRepository.save(module);
	}

	@Override
	public void deleteModule(Module module) {
		moduleRepository.delete(module);
	}

	@Override
	public Module findByUserGroupNameAndModuleOrderAndModuleName(String userGroupName, int moduleOrder,
			String moduleName) {
		return moduleRepository.findByUserGroupNameAndModuleOrderAndModuleName(userGroupName, moduleOrder, moduleName);
	}

	@Override
	public Module findByUserGroupNameAndModuleOrder(String userGroupName, int moduleOrder) {
		return moduleRepository.findByUserGroupNameAndModuleOrder(userGroupName, moduleOrder);
	}

	@Override
	public Module findByUserGroupNameAndModuleName(String userGroupName, String moduleName) {
		return moduleRepository.findByUserGroupNameAndModuleName(userGroupName, moduleName);
	}

}
