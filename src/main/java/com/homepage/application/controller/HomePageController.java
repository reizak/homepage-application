package com.homepage.application.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.homepage.application.model.HomePage;
import com.homepage.application.model.HomePageRequest;
import com.homepage.application.model.Response;
import com.homepage.application.service.IHomePageService;

@RestController
public class HomePageController {

	@Autowired
	IHomePageService homePageService;

	@GetMapping("/homePages")
	public List<HomePage> getAllHomePage() {
		return homePageService.getAllHomePage();
	}

	@GetMapping("/homePage/{id}")
	public List<HomePage> getById(@PathVariable(value = "id") Long homePageId) {
		return homePageService.findByHomePageId(homePageId);
	}

	@PostMapping("/homePages")
	public HomePage createHomePage(@Valid @RequestBody HomePageRequest homePageRequest) {
		HomePage homePage = new HomePage();
		homePage.setHomePageCode(homePageRequest.getHomePageCode());
		homePage.setHomePageName(homePageRequest.getHomePageName());

		return homePageService.createHomePage(homePage);
	}

	@PutMapping("/homePage/{id}")
	public ResponseEntity<Response> updateHomePage(@PathVariable(value = "id") Long homePageId,
			@Valid @RequestBody HomePageRequest homePageRequest) {
		HomePage homePage = new HomePage();
		homePage.setHomePageCode(homePageRequest.getHomePageCode());
		homePage.setHomePageName(homePageRequest.getHomePageName());

		Response response = new Response();
		try {
			HomePage updatedHomePage = homePageService.updateHomePage(homePageId, homePage);
			response.setResult(updatedHomePage);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}

		return ResponseEntity.accepted().body(response);
	}

	@DeleteMapping("/homePage/{id}")
	public ResponseEntity<?> deleteHomePage(@PathVariable(value = "id") Long homePageId) {
		homePageService.deleteHomePage(homePageId);
		return ResponseEntity.ok().build();
	}
}
