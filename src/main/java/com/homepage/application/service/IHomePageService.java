package com.homepage.application.service;

import java.util.List;

import com.homepage.application.model.HomePage;

public interface IHomePageService {

	public List<HomePage> getAllHomePage();

	public HomePage findByHomePageCode(String moduleName);

	public List<HomePage> findByHomePageId(Long homePageId);

	public HomePage createHomePage(HomePage homePage);

	public HomePage updateHomePage(Long homePageId, HomePage homePage);

	public void deleteHomePage(Long homePageId);

}
