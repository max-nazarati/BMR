package com.nazarati.bmr.test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.nazarati.bmr.service.TextFormService;

public class TextFormServiceTest {
	
	TextFormService tfs;
	@Test
	public void parseTest() throws Exception {
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
