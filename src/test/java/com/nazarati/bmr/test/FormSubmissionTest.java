package com.nazarati.bmr.test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.nazarati.bmr.controller.HomeController;

@SpringBootTest
@AutoConfigureMockMvc
public class FormSubmissionTest 
{
	@Autowired
	private HomeController controller;
	@Autowired
	private MockMvc mvc;
	
	
	@Test
	public void fileReceivedTest() throws Exception 
	{
		MockMultipartFile f1 = new MockMultipartFile("file", "file1", "text/plain", "random text\ntext".getBytes());
		mvc.perform(multipart("/filesubmission").file(f1))
		.andExpect(status().is(302));
		assertThat(controller.ffs().file().getOriginalFilename()).isEqualTo("file1");
	}
	
	
	@Test
	public void textReceivedTest() throws Exception 
	{
		mvc.perform(post("/textsubmission").param("movies", "'moviea' 'movieb'"))
		.andExpect(status().is(302));
	}
}
