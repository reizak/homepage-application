package com.homepage.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGroupRequest {

	@JsonProperty("userGroupName")
	private String userGroupName;
	
	public UserGroupRequest() {
		super();
	}


	public String getUserGroupName() {
		return userGroupName;
	}


	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

}
