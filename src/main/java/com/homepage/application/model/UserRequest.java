package com.homepage.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("userGroupName")
	private String userGroupName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

}

