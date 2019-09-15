package com.homepage.application;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homepage.application.model.HomePage;
import com.homepage.application.model.Module;
import com.homepage.application.model.ModuleRequest;
import com.homepage.application.model.Response;
import com.homepage.application.model.ResponseModule;
import com.homepage.application.model.User;
import com.homepage.application.model.UserGroup;
import com.homepage.application.repository.IHomePageRepository;
import com.homepage.application.service.HomePageService;
import com.homepage.application.service.ModuleService;
import com.homepage.application.service.UserGroupService;
import com.homepage.application.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomePageApplication.class)
public class ModuleControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private ObjectMapper mapper = new ObjectMapper();
		
	@MockBean
	private ModuleService moduleService;
	
	@MockBean
	private HomePageService homePageService;
	
	@MockBean
	private IHomePageRepository homePageRepository;
	
	@MockBean
	private UserGroupService userGroupService;
	
	@MockBean
	private UserService userService;
	
	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	private void performGet(String url,MultiValueMap<String, String> params, ResultMatcher matcher) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(url).params(params)).andExpect(matcher);
	}
	
	private void performPost(String url,ModuleRequest request, ResultMatcher matcher) throws Exception {
		String jsonInString = mapper.writeValueAsString(request);
		
		mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString))
				.andExpect(matcher);
	}
	
	private void performDelete(String url,MultiValueMap<String, String> params, ResultMatcher matcher) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(url)
				.contentType(MediaType.APPLICATION_JSON)
				.params(params))
				.andExpect(matcher);
	}
	
	
	private void performPut(String url,MultiValueMap<String, String> params,ModuleRequest request , ResultMatcher matcher) throws Exception {
		String jsonInString = mapper.writeValueAsString(request);
		mockMvc.perform(MockMvcRequestBuilders.put(url)
				.contentType(MediaType.APPLICATION_JSON)
				.params(params).content(jsonInString))
				.andExpect(matcher);
	}
	
	@Test
	public void testGetModuleByUserId() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("userId", "Reiza");
		
		List<Module> listModule = new ArrayList<>();
		listModule.add(new Module("PromoCard", 1, "UserA"));
		listModule.add(new Module("CategoryCard", 2, "UserA"));
		listModule.add(new Module("FlashSaleCard", 3, "UserA"));
		listModule.add(new Module("HistoryCard", 4, "UserA"));
		listModule.add(new Module("NewsCard", 5, "UserA"));
		
		Mockito.when(userService.findByUserId(Mockito.anyString())).thenReturn(new User("Reiza", "UserA"));
		Mockito.when(moduleService.findByuserGroupName(Mockito.anyString())).thenReturn(listModule);
		performGet("/modules/byUserId",params, new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				ResponseModule response = mapper.readValue(result.getResponse().getContentAsString(), ResponseModule.class);
				String expected = "{\"modules\":[{\"moduleName\":\"PromoCard\",\"moduleOrder\":1},{\"moduleName\":\"CategoryCard\",\"moduleOrder\":2},{\"moduleName\":\"FlashSaleCard\",\"moduleOrder\":3},{\"moduleName\":\"HistoryCard\",\"moduleOrder\":4},{\"moduleName\":\"NewsCard\",\"moduleOrder\":5}]}";
				JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
			}
		});
		
	}
	
	private ModuleRequest createRequest(String userGroupName, int moduleOrder ,String moduleName) {
		ModuleRequest request = new ModuleRequest();
		request.setModuleName(moduleName);
		request.setModuleOrder(moduleOrder);
		request.setUserGroupName(userGroupName);
		return request;
	}
	
	@Test
	public void testDeleteModule() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("moduleOrder", "1");
		params.add("userGroupName", "UserA");
		List<HomePage> listHomePage = new ArrayList<>();
		listHomePage.add(new HomePage("Category", "CategoryCard"));
		listHomePage.add(new HomePage("FlashSale", "FlashSaleCard"));
		Mockito.when(moduleService.countByUserGroupName("UserA")).thenReturn(1L);
		Mockito.when(homePageService.getAllHomePage()).thenReturn(listHomePage);
		Mockito.when(userGroupService.findByUserGroupName(Mockito.anyString())).thenReturn(new UserGroup("UserA"));
		Mockito.when(moduleService.findByUserGroupNameAndModuleOrder(Mockito.anyString(), Mockito.anyInt())).thenReturn(new Module("UserA", 1, "CategoryCard"));
		Mockito.doNothing().when(moduleService).deleteModule(Mockito.any(Module.class));
		
		performDelete("/module",params, new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
				Assert.assertEquals("00", response.getCode());	
			}
		});
	}
	
	@Test
	public void testCreateModule() throws Exception {

		List<HomePage> listHomePage = new ArrayList<>();
		listHomePage.add(new HomePage("Category", "CategoryCard"));
		listHomePage.add(new HomePage("FlashSale", "FlashSaleCard"));
		Mockito.when(moduleService.countByUserGroupName("UserA")).thenReturn(1L);
		Mockito.when(homePageService.getAllHomePage()).thenReturn(listHomePage);
		Mockito.when(homePageService.findByHomePageCode(Mockito.anyString())).thenReturn(new HomePage("FlashSale", "FlashSaleCard"));
		Mockito.when(userGroupService.findByUserGroupName(Mockito.anyString())).thenReturn(new UserGroup("UserA"));
		Mockito.when( moduleService.findByUserGroupNameAndModuleName(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		
		List<Module> listModule = new ArrayList<>();
		listModule.add(new Module("UserA", 1, "CategoryCard"));
		Mockito.when(moduleService.getAllModule()).thenReturn(listModule);
		
		Mockito.when(moduleService.createModule(Mockito.any(Module.class))).thenReturn(new Module("UserA", 1, "PromoCard"));
		
		performPost("/module",createRequest("UserA", 1, "PromoCard"), new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
				Assert.assertEquals("00", response.getCode());					}
		});
	}
	
	@Test
	public void testUpdateModule() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("moduleOrder", "1");
		params.add("userGroupName", "UserA");
		
		List<HomePage> listHomePage = new ArrayList<>();
		listHomePage.add(new HomePage("Category", "CategoryCard"));
		listHomePage.add(new HomePage("FlashSale", "FlashSaleCard"));
		
		Mockito.when(moduleService.countByUserGroupName("UserA")).thenReturn(1L);
		Mockito.when(homePageService.getAllHomePage()).thenReturn(listHomePage);
		Mockito.when(userGroupService.findByUserGroupName(Mockito.anyString())).thenReturn(new UserGroup("UserA"));
		Mockito.when(moduleService.findByUserGroupNameAndModuleOrder(Mockito.anyString(), Mockito.anyInt())).thenReturn(new Module("UserA", 1, "CategoryCard"));
		Mockito.when(homePageService.findByHomePageCode(Mockito.anyString())).thenReturn(new HomePage("Category", "PromoCard"));
		Mockito.when(moduleService.updateModule(Mockito.any(Module.class))).thenReturn(new Module("UserA", 1, "PromoCard"));
		
		performPut("/module",params,createRequest("UserA", 1, "PromoCard"), new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);
				Assert.assertEquals("00", response.getCode());	
			}
		});
	}
}
