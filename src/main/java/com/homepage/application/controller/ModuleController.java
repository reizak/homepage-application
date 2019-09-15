package com.homepage.application.controller;

import java.util.Comparator;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.homepage.application.model.HomePage;
import com.homepage.application.model.Module;
import com.homepage.application.model.ModuleRequest;
import com.homepage.application.model.Response;
import com.homepage.application.model.ResponseModule;
import com.homepage.application.model.User;
import com.homepage.application.model.UserGroup;
import com.homepage.application.service.IHomePageService;
import com.homepage.application.service.IModuleService;
import com.homepage.application.service.IUserGroupService;
import com.homepage.application.service.IUserService;

@RestController
public class ModuleController {

	private static final Logger log = LoggerFactory.getLogger(ModuleController.class);

	@Autowired
	IModuleService moduleService;

	@Autowired
	IHomePageService homePageService;

	@Autowired
	IUserGroupService userGroupService;

	@Autowired
	IUserService userService;

	@GetMapping("/modules")
	public List<Module> getAllModule() {
		return moduleService.getAllModule();
	}

	@GetMapping("/modules/byUserId")
	public ResponseEntity<ResponseModule> getByUserId(@RequestParam(value = "userId") String userId) {
		ResponseModule response = new ResponseModule();

		User user = userService.findByUserId(userId);
		if (user == null) {
			response.setMessage("User Id Not Found");
			return ResponseEntity.accepted().body(response);
		}
		List<Module> modules = moduleService.findByuserGroupName(user.getUserGroupName());
		modules.sort(Comparator.comparing(Module::getModuleOrder));
		response.setResult(modules);

		return ResponseEntity.accepted().body(response);

	}

	public void validated(ModuleRequest request, String methode) throws Exception {
		if (!"DELETE".equalsIgnoreCase(methode)) {
			int countHomePage = homePageService.getAllHomePage().size();
			
			if (request.getModuleOrder() > countHomePage) {
				throw new Exception("moduleOrder number cannot be greater than " + countHomePage);
			}
			
			if (moduleService.countByUserGroupName(request.getUserGroupName()) >= countHomePage
					&& "POST".equalsIgnoreCase(methode)) {
				throw new Exception("the maximum homepage is ".concat(String.valueOf(countHomePage)).concat(" per user group"));
			}
		}
		

		if (request.getModuleOrder() < 0) {
			throw new Exception("moduleOrder is Mandatory");
		}

		if (!methode.equalsIgnoreCase("DELETE")) {
			if (request.getModuleName().isEmpty()) {
				throw new Exception("moduleName is Mandatory");
			}

			HomePage homePage = homePageService.findByHomePageCode(request.getModuleName());
			if (homePage == null) {
				throw new Exception("ModuleName not Found");
			}
		}

		if (request.getUserGroupName().isEmpty()) {
			throw new Exception("groupName is Mandatory");
		}

		UserGroup userGroup = userGroupService.findByUserGroupName(request.getUserGroupName());

		if (userGroup == null) {
			throw new Exception("UserGroup not Found");
		}

	}

	@PostMapping("/module")
	public ResponseEntity<Response> createModule(@Valid @RequestBody ModuleRequest request) {

		Response response = new Response();

		try {
			validated(request, "POST");
		} catch (Exception e) {
			log.error(e.getMessage());
			response.setMessage(e.getMessage());
			return ResponseEntity.accepted().body(response);
		}

		try {

			Module moduleTemp = new Module();

			moduleTemp = moduleService.findByUserGroupNameAndModuleOrder(request.getUserGroupName(),
					request.getModuleOrder());
			if (moduleTemp != null) {
				throw new Exception("UserGroupName : " + request.getUserGroupName() + " , ModuleOrder : "
						+ request.getModuleOrder() + " already used");
			}

			moduleTemp = moduleService.findByUserGroupNameAndModuleName(request.getUserGroupName(),
					request.getModuleName());

			if (moduleTemp != null) {
				throw new Exception("UserGroupName : " + request.getUserGroupName() + " , ModuleName : "
						+ request.getModuleName() + " already used");
			}

			Module module = new Module();
			module.setModuleName(request.getModuleName());
			module.setModuleOrder(request.getModuleOrder());
			module.setUserGroupName(request.getUserGroupName());

			moduleService.createModule(module);
			response.setCode("00");
			response.setMessage("success");
			response.setResult(module);
		} catch (Exception e) {
			response.setCode("01");
			response.setMessage("failed - ".concat(e.getMessage()));
			log.error(e.getMessage());
		}

		return ResponseEntity.accepted().body(response);

	}

	@PutMapping("/module")
	public ResponseEntity<Response> updateModule(@RequestParam(value = "userGroupName") String userGroupName,
			@RequestParam(value = "moduleOrder") int moduleOrder, @RequestBody ModuleRequest request) {

		Response response = new Response();

		try {
			request.setUserGroupName(userGroupName);
			request.setModuleOrder(moduleOrder);
			validated(request, "PUT");
		} catch (Exception e) {
			response.setCode("01");
			response.setMessage("failed - ".concat(e.getMessage()));
			return ResponseEntity.accepted().body(response);
		}

		try {
			Module moduleTemp = new Module();

			moduleTemp = moduleService.findByUserGroupNameAndModuleOrder(request.getUserGroupName(),
					request.getModuleOrder());

			if (moduleTemp == null) {
				throw new Exception("Module not found with UserGroupName : " + request.getUserGroupName()
						+ " , ModuleOrder : " + request.getModuleOrder());
			}

			if (moduleTemp.getModuleName().equalsIgnoreCase(request.getModuleName())) {
				throw new Exception("UserGroupName : " + moduleTemp.getUserGroupName() + " , ModuleOrder : "
						+ moduleTemp.getModuleOrder() + " , ModuleName : " + moduleTemp.getModuleName() + " already updated");
			}

			moduleTemp = moduleService.findByUserGroupNameAndModuleName(request.getUserGroupName(),
					request.getModuleName());

			if (moduleTemp != null) {
				throw new Exception("UserGroupName : " + moduleTemp.getUserGroupName() + ", ModuleName : "
						+ moduleTemp.getModuleName() + " already used");
			}

			Module module = new Module();
			module.setModuleName(request.getModuleName());
			module.setModuleOrder(request.getModuleOrder());
			module.setUserGroupName(request.getUserGroupName());

			module = moduleService.updateModule(module);
			response.setCode("00");
			response.setMessage("success");
			response.setResult(module);
		} catch (Exception e) {
			response.setCode("01");
			response.setMessage("failed - ".concat(e.getMessage()));
		}

		return ResponseEntity.accepted().body(response);
	}

	@DeleteMapping("/module")
	public ResponseEntity<Response> deleteModule(@RequestParam(value = "userGroupName") String userGroupName,
			@RequestParam(value = "moduleOrder") int moduleOrder) {
		Response response = new Response();
		ModuleRequest request = new ModuleRequest();
		try {

			request.setUserGroupName(userGroupName);
			request.setModuleOrder(moduleOrder);
			validated(request, "DELETE");
		} catch (Exception e) {
			response.setCode("01");
			response.setMessage("failed - ".concat(e.getMessage()));
			return ResponseEntity.accepted().body(response);
		}

		try {
			Module module = new Module();
			Module moduleTemp = moduleService.findByUserGroupNameAndModuleOrder(request.getUserGroupName(),
					request.getModuleOrder());
			if (moduleTemp == null) {
				throw new Exception("Module not found with UserGroupName : " + request.getUserGroupName()
						+ " , ModuleOrder : " + request.getModuleOrder());
			}

			module.setModuleName(request.getModuleName());
			module.setModuleOrder(request.getModuleOrder());
			module.setUserGroupName(request.getUserGroupName());
			moduleService.deleteModule(moduleTemp);
			response.setCode("00");
			response.setMessage("success");
			response.setResult(moduleTemp);
		} catch (Exception e) {
			response.setCode("01");
			response.setMessage("failed - ".concat(e.getMessage()));
			return ResponseEntity.accepted().body(response);
		}

		return ResponseEntity.accepted().body(response);
	}
}
