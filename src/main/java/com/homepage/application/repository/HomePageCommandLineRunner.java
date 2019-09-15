package com.homepage.application.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.homepage.application.model.HomePage;
import com.homepage.application.model.Module;
import com.homepage.application.model.User;
import com.homepage.application.model.UserGroup;

@Component
public class HomePageCommandLineRunner implements CommandLineRunner{

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IHomePageRepository homePageRepository;
	
	@Autowired
	private IUserGroupRepository userGroupRepository;
	
	@Autowired
	private IModuleRepository moduleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		homePageRepository.save(new HomePage("Promo", "PromoCard"));
		homePageRepository.save(new HomePage("Category","CategoryCard"));
		homePageRepository.save(new HomePage("FlashSale", "FlashSaleCard"));
		homePageRepository.save(new HomePage("History", "HistoryCard"));
		homePageRepository.save(new HomePage("News", "NewsCard"));
		

		userGroupRepository.save(new UserGroup("UserA"));
		userGroupRepository.save(new UserGroup("UserB"));
		userGroupRepository.save(new UserGroup("UserC"));
		
		userRepository.save(new User("Reiza", "UserA"));
		userRepository.save(new User("Kunhadi", "UserB"));
		userRepository.save(new User("Ginanjar", "UserC"));
		
		moduleRepository.save(new Module("PromoCard", 1, "UserA"));
		moduleRepository.save(new Module("CategoryCard", 2, "UserA"));
		moduleRepository.save(new Module("FlashSaleCard", 3, "UserA"));
		moduleRepository.save(new Module("HistoryCard", 4, "UserA"));
		moduleRepository.save(new Module("NewsCard", 5, "UserA"));
		
		moduleRepository.save(new Module("PromoCard", 1, "UserB"));
		moduleRepository.save(new Module("NewsCard", 2, "UserB"));
		moduleRepository.save(new Module("FlashSaleCard", 3, "UserB"));
		moduleRepository.save(new Module("CategoryCard", 4, "UserB"));
		moduleRepository.save(new Module("HistoryCard", 5, "UserB"));
		
		moduleRepository.save(new Module("PromoCard", 1, "UserC"));
		moduleRepository.save(new Module("FlashSaleCard", 2, "UserC"));
		moduleRepository.save(new Module("CategoryCard", 3, "UserC"));
		moduleRepository.save(new Module("NewsCard", 4, "UserC"));
		moduleRepository.save(new Module("HistoryCard", 5, "UserC"));
		
	}
}

