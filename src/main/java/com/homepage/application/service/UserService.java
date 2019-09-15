package com.homepage.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.homepage.application.model.User;
import com.homepage.application.repository.IUserRepository;

@Service
public class UserService implements IUserService{

	@Autowired
	IUserRepository userRepository;
	
	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	@Override
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}
	
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(Long userId, User userNew) {
		User userTemp = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));

		userTemp.setUserId(userNew.getUserId());
		User updatedUser = userRepository.save(userTemp);
		return updatedUser;
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
		userRepository.delete(user);
	}

}
