package com.homepage.application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Module {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long moduleId;
	private String moduleName;
	private int moduleOrder;
	
	@JsonIgnore
	private String userGroupName;
	
	public Module() {
		super();
	}
	
	public Module(String moduleName, int moduleOrder, String userGroupName) {
		super();
		this.moduleName = moduleName;
		this.moduleOrder = moduleOrder;
		this.userGroupName = userGroupName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getModuleOrder() {
		return moduleOrder;
	}

	public void setModuleOrder(int moduleOrder) {
		this.moduleOrder = moduleOrder;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

}
