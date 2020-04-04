package com.nazarati.bmr.test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.nazarati.bmr.controller.HomeController;
import com.nazarati.bmr.service.FileFormService;
import com.nazarati.bmr.service.TextFormService;

@SpringBootTest
@AutoConfigureMockMvc
public class FormTest {
	@Autowired
	private HomeController controller;
	
	@Autowired
	private MockMvc mvc;
	private String homepage = "homepage.html";
	
	
	@Test
	public void fileReceivedTest() throws Exception {
		MockMultipartFile f1 = new MockMultipartFile("file", "file1", "text/plain", "random text\ntext".getBytes());
		mvc.perform(multipart("/filesubmission").file(f1))
		.andExpect(status().is(302));// TODO: Also check redirect adress
		assertThat(controller.ffs().file().getOriginalFilename()).isEqualTo("file1");
	}
	
	
	@Test
	public void fileParseTest() throws Exception {
		// TODO: Check if Mockito can help with mocking FileFormService so that we can remove it from HomeController
		MockMultipartFile f1 = new MockMultipartFile("f1", "file1", "text/plain", "random text\ntext".getBytes());
		MockMultipartFile f2 = new MockMultipartFile("f2", "file2", "text/plain", "    \n  ".getBytes());
	
		Set<String> testres = new HashSet<String>(Arrays.asList("random text", "text"));
		FileFormService ffs = new FileFormService(f1);
		assertThat(ffs.parseFile()).isEqualTo(testres);
	
		Set<String> testres2 = new HashSet<String>(Arrays.asList("    ", "  "));
		ffs = new FileFormService(f2);
		assertThat(ffs.parseFile()).isEqualTo(testres2);
	}
	
	
	@Test
	public void textReceivedTest() throws Exception {
		mvc.perform(post("/textsubmission").param("movies", "'moviea' 'movieb'"))
		.andExpect(status().is(302));// TODO: Also check redirect adress
	}
	
	
	@Test
	public void parseTest() throws Exception {
		TextFormService tfs;
		String s1 = "'a' 'b'";
		Set<String> set1 = new HashSet<String>(Arrays.asList("a", "b"));
		
		String s2 = "'aaaa' 'b b b' 'bleh'";
		Set<String> set2 = new HashSet<String>(Arrays.asList("aaaa", "b b b", "bleh"));
	
		String s3 = "";
		Set<String> set3 = new HashSet<String>(Arrays.asList());
	
		String s4 = "''";
		Set<String> set4 = new HashSet<String>(Arrays.asList());
	
		tfs = new TextFormService(s1);
		assertThat(tfs.movies()).isEqualTo(set1);
		
		tfs = new TextFormService(s2);
		assertThat(tfs.movies()).isEqualTo(set2);
		
		tfs = new TextFormService(s3);
		assertThat(tfs.movies()).isEqualTo(set3);
		
		tfs = new TextFormService(s4);
		assertThat(tfs.movies()).isEqualTo(set4);
	}

}
