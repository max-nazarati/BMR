package com.nazarati.bmr.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

import org.javatuples.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nazarati.bmr.secretystuff.APISecrets;

@Service
public class RecommenderService {
	private Set<String> watchedMovies;
	private String tmdb = "http://api.themoviedb.org/3";
	private String key = APISecrets.apiKey();
	public RecommenderService() {
		// TODO Auto-generated constructor stub
	}
	
	public RecommenderService(Set<String> movies) {
		watchedMovies = movies;
	}
	
	public Pair<Set<String>, Set<String>> recommend(RedirectAttributes attributes) throws URISyntaxException, MalformedURLException, UnirestException, UnsupportedEncodingException{
		Set<String> recommendations = new LinkedHashSet<String>();
		Set<String> recommendationPosters = new LinkedHashSet<String>();
		String posterPath = "http://image.tmdb.org/t/p/w185/";
		for (String movie : watchedMovies) {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je;
			
			// figure out IMDB ID of the movie title
			String req = "/search/movie/?" + key + "&query=" + encode(movie);
			HttpResponse<JsonNode> resp = Unirest.get(tmdb + req).asJson();
			je = jp.parse(resp.getBody().toString());
			
			if (je.getAsJsonObject().get("results").getAsJsonArray().size() == 0) {
				attributes.addAttribute("badmovie", true);
				return new Pair<Set<String>, Set<String>>(recommendations, recommendationPosters);
			}
			int movieId = je.getAsJsonObject().get("results")
					.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt();
			
			// use now known IMDB ID to search for recommended movies
			String req2 = "/movie/" + movieId + "/recommendations?" + key;
			HttpResponse<JsonNode> resp2 = Unirest.get(tmdb + req2).asJson();
			je = jp.parse(resp2.getBody().toString());
			
			// extract original_title from the recommendations JSON Object
			JsonArray arr = je.getAsJsonObject().get("results").getAsJsonArray();
			// only take the top 25% of the suggestions
			for (int i = 0; i < arr.size(); i++) {
				if (!arr.get(i).getAsJsonObject().get("original_title").isJsonNull()) {
					recommendations.add(arr.get(i).getAsJsonObject()
						.get("original_title").getAsString());
				}
				else {
					recommendations.add("");
				}
				if (!arr.get(i).getAsJsonObject().get("poster_path").isJsonNull()) {
					recommendationPosters.add(posterPath + arr.get(i).getAsJsonObject()
						.get("poster_path").getAsString());
				}
				else {
					recommendationPosters.add("");
				}
			}
		}
		
		return new Pair<Set<String>, Set<String>>(recommendations, recommendationPosters);
	}
	
	private String encode(String val) throws UnsupportedEncodingException {
		return URLEncoder.encode(val, StandardCharsets.UTF_8.toString());
	}
}
