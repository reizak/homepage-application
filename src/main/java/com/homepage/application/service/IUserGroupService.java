package com.homepage.application.service;

import java.util.List;

import com.homepage.application.model.UserGroup;

public interface IUserGroupService {

	public UserGroup findByUserGroupName(String userGroupName);

	public List<UserGroup> getAllUserGroup();

	public UserGroup createUserGroup(UserGroup userGroup);

	public UserGroup updateUserGroup(Long userGroupId, UserGroup userGroup);

	public void deleteUserGroup(String userGroupName);

}
