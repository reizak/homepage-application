package com.homepage.application.service;

import java.util.List;

import com.homepage.application.model.User;

public interface IUserService {
	public User findByUserId(String userId);

	public List<User> getAllUser();

	public User createUser(User user);

	public User updateUser(Long id, User user);

	public void deleteUser(Long userId);

}
