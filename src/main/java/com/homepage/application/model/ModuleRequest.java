package com.homepage.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModuleRequest {

	@JsonProperty("moduleName")
	private String moduleName;
	
	@JsonProperty("moduleOrder")
	private int moduleOrder;
	
	@JsonProperty("userGroupName")
	private String userGroupName;
	
	public ModuleRequest() {
		super();
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
