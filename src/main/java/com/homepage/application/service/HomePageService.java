package com.homepage.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.homepage.application.model.HomePage;
import com.homepage.application.repository.IHomePageRepository;


@Service
public class HomePageService implements IHomePageService{

	@Autowired
	IHomePageRepository homePageRepository;
	
	@Override
	public List<HomePage> getAllHomePage() {
        return homePageRepository.findAll();
    }
	
	@Override
	public List<HomePage>  findByHomePageId(Long homePageId) {
        return homePageRepository.findByHomePageId(homePageId);
    }
	
	@Override
	public HomePage  findByHomePageCode(String homePageCode) {
        return homePageRepository.findByHomePageCode(homePageCode);
    }
	
	@Override
    public HomePage createHomePage(HomePage homePage) {
        return homePageRepository.save(homePage);
    }
    
	@Override
    public HomePage updateHomePage(Long homePageId,HomePage homePageNew) {
    	HomePage homePageTemp = homePageRepository.findById(homePageId)
                .orElseThrow(() -> new ResourceNotFoundException("HomePage not found with id : "+ homePageId));

		 homePageTemp.setHomePageName(homePageNew.getHomePageName());
	
		 HomePage updatedHomePage = homePageRepository.save(homePageTemp);
		 return updatedHomePage;
	    }
    
	@Override
    public void deleteHomePage(Long homePageId) {
    	HomePage homePage = homePageRepository.findById(homePageId)
	                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id : "+ homePageId));
    	homePageRepository.delete(homePage);
	    }
    
    
}


