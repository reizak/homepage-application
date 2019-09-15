package com.homepage.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.homepage.application.model.UserGroup;
import com.homepage.application.repository.IUserGroupRepository;

@Service
public class UserGroupService implements IUserGroupService{

	@Autowired
	private IUserGroupRepository userGroupRepository;

	@Override
	public List<UserGroup> getAllUserGroup() {
		return userGroupRepository.findAll();
	}

	@Override
	public UserGroup findByUserGroupName(String userGroupName) {
		return userGroupRepository.findByUserGroupName(userGroupName);
	}
	
	@Override
	public UserGroup createUserGroup(UserGroup userGroup) {

		UserGroup userGroupTemp = findByUserGroupName(userGroup.getUserGroupName());
		if (userGroupTemp  != null) {
			throw new ResourceNotFoundException("UserGroup found with UserGroupName id : " + userGroup.getUserGroupName());
		}

		return userGroupRepository.save(userGroup);
	}

	@Override
	public UserGroup updateUserGroup(Long userGroupId, UserGroup userGroupNew) {
		UserGroup userGroupTemp = userGroupRepository.findById(userGroupId).orElseThrow(
				() -> new ResourceNotFoundException("UserGroup not found with id : " + userGroupId));

		userGroupTemp.setUserGroupName(userGroupNew.getUserGroupName());
		userGroupTemp.setUserGroupId(userGroupNew.getUserGroupId());
		UserGroup updatedUserGroup = userGroupRepository.save(userGroupTemp);
		return updatedUserGroup;
	}

	@Override
	public void deleteUserGroup(String userGroupName) {
		UserGroup userGroupTemp = userGroupRepository.findByUserGroupName(userGroupName);
		if (userGroupTemp  == null) {
			throw new ResourceNotFoundException("UserGroup not found with userGroupName : " + userGroupName);
		}
		
		userGroupRepository.delete(userGroupTemp);
	}
}
