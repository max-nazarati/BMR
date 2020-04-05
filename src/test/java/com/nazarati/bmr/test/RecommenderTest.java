package com.nazarati.bmr.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.nazarati.bmr.service.RecommenderService;

public class RecommenderTest {

	@Test
	public void recommendationTest() throws UnsupportedEncodingException, UnirestException {
		RedirectAttributes attributes = mock(RedirectAttributes.class);
		Set<String> movies1 = new HashSet<String>(Arrays.asList("Avengers: Endgame", "Pikachu"));
		RecommenderService rs = new RecommenderService(movies1);
		Pair<Set<String>, Set<String>> recs = rs.recommend(attributes);
		assertThat(recs.getValue0().size()).isEqualTo(29);
		
		Set<String> movies2 = new HashSet<String>(Arrays.asList("Avengers: Endgame"));
		rs = new RecommenderService(movies2);
		recs = rs.recommend(attributes);
		assertThat(recs.getValue0().size()).isEqualTo(20);
	}

}
