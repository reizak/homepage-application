package com.homepage.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HomePageRequest {

	@JsonProperty("homePageName")
	private String homePageName;
	
	@JsonProperty("homePageCode")
	private String homePageCode;
	
	
	public HomePageRequest() {
		super();
	}

	public String getHomePageName() {
		return homePageName;
	}

	public void setHomePageName(String homePageName) {
		this.homePageName = homePageName;
	}

	public String getHomePageCode() {
		return homePageCode;
	}

	public void setHomePageCode(String homePageCode) {
		this.homePageCode = homePageCode;
	}

	


}
