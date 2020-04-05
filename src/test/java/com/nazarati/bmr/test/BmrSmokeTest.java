package com.nazarati.bmr.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
	
	private String homepage = "homepage.html";


	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	

	@Test
	public void homepageControllerStartingBehaviourTest() throws Exception {
		mockMvc.perform(get("/")).andExpect(view().name(homepage));
	}
}
