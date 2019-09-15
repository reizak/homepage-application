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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.homepage.application.model.Response;
import com.homepage.application.model.UserGroup;
import com.homepage.application.model.UserGroupRequest;
import com.homepage.application.service.IUserGroupService;

@RestController
public class UserGroupController {

	@Autowired
	IUserGroupService userGroupService;

	@GetMapping("/userGroups")
	public List<UserGroup> getAllUserGroup() {
		return userGroupService.getAllUserGroup();
	}

	@PostMapping("/userGroups")
	public ResponseEntity<Response> createUserGroup(@Valid @RequestBody UserGroupRequest request) {

		Response response = new Response();
		UserGroup userGroup = new UserGroup();
		userGroup.setUserGroupName(request.getUserGroupName());

		try {
			userGroup = userGroupService.createUserGroup(userGroup);
			;
			response.setResult(userGroup);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}

		return ResponseEntity.accepted().body(response);
	}

	@PutMapping("/userGroup/{userGroupId}")
	public ResponseEntity<Response> updateUserGroup(@PathVariable(value = "userGroupId") Long userGroupId,
			@Valid @RequestBody UserGroupRequest request) {
		UserGroup userGroup = new UserGroup();
		userGroup.setUserGroupName(request.getUserGroupName());

		Response response = new Response();
		try {
			UserGroup updateUserGroup = userGroupService.updateUserGroup(userGroupId, userGroup);
			response.setResult(updateUserGroup);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}

		return ResponseEntity.accepted().body(response);
	}

	@DeleteMapping("/userGroup")
	public ResponseEntity<?> deleteUserGroup(@RequestParam(value = "userGroupName") String userGroupName) {
		userGroupService.deleteUserGroup(userGroupName);
		return ResponseEntity.ok().build();
	}
}
