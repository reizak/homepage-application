package com.homepage.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userGroupId;
	
	@Column(unique=true, nullable=false) 
	private String userGroupName;
	
	public UserGroup() {
		super();
	}

	public UserGroup(String userGroupName) {
		super();
		this.userGroupName = userGroupName;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	@Override
	public String toString() {
		return "UserGroup [userGroupId=" + userGroupId + ", userGroupName=" + userGroupName + "]";
	}
	
	
	
}
