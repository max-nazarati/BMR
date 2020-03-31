package com.nazarati.bmr.test;

import static org.hamcrest.Matchers.equalTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.nazarati.bmr.controller.HomeController;

@SpringBootTest
@AutoConfigureMockMvc
public class BmrSmokeTest {
	@Autowired
	private HomeController controller;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void homepageControllerStartingBehaviourTest() throws Exception {
		mockMvc.perform(get("/")).andExpect(view().name("homepage.html"));
	}
	
	@Test
	public void homepageControllerTextSubmitBehaviourTest() throws Exception {
		mockMvc.perform(post("/textsubmission"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(equalTo("text submission")));
	}
	
	@Test
	public void homepageControllerFileSubmitBehaviourTest() throws Exception{
		mockMvc.perform(post("/filesubmission"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(equalTo("file submission")));
	}

}
