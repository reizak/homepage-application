package com.homepage.application.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.homepage.application.model.Response;
import com.homepage.application.model.User;
import com.homepage.application.model.UserGroup;
import com.homepage.application.model.UserRequest;
import com.homepage.application.service.IUserGroupService;
import com.homepage.application.service.IUserService;

@RestController
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	IUserService userService;

	@Autowired
	IUserGroupService userGroupService;

	@GetMapping("/users")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}

	@PostMapping("/users")
	public ResponseEntity<Response> createUser(@Valid @RequestBody UserRequest userRequest) {
		Response response = new Response();
		User user = new User();

		try {
			UserGroup userGroup = userGroupService.findByUserGroupName(userRequest.getUserGroupName());
			if (userGroup == null) {
				throw new Exception("UserGroup not found with UserGroupName : " + userRequest.getUserGroupName());
			} else {
				user.setUserGroupName(userRequest.getUserGroupName());
				user.setUserId(userRequest.getUserId());

				User userTemp = userService.findByUserId(user.getUserId());
				if (userTemp != null) {
					throw new Exception("User found with UserId : " + user.getUserId());
				}

				user = userService.createUser(user);
				response.setResult(user);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			response.setMessage(e.getMessage());
		}

		return ResponseEntity.accepted().body(response);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<Response> updateUser(@PathVariable(value = "id") Long id,
			@Valid @RequestBody UserRequest userRequest) {

		Response response = new Response();
		try {
			User user = new User();
			user.setUserGroupName(userRequest.getUserGroupName());
			user.setUserId(userRequest.getUserId());

			user = userService.updateUser(id, user);
			response.setResult(user);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}

		return ResponseEntity.accepted().body(response);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
		userService.deleteUser(userId);

		return ResponseEntity.ok().build();
	}
}
