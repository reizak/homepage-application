package com.homepage.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class HomePage {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long homePageId;
	
	@Column(nullable=false) 
	private String homePageName;
	
	@Column(unique=true, nullable=false) 
	private String homePageCode;
	
	
	public HomePage() {
		super();
	}
	
	public HomePage(String homePageName, String homePageCode) {
		super();
		this.homePageName = homePageName;
		this.homePageCode = homePageCode;
	}

	public Long getHomePageId() {
		return homePageId;
	}

	public void setHomePageId(Long homePageId) {
		this.homePageId = homePageId;
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
