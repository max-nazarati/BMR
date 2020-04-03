package com.nazarati.bmr.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

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
	
	public Set<String> recommend() throws URISyntaxException, MalformedURLException, UnirestException, UnsupportedEncodingException{
		Set<String> recommendations = new LinkedHashSet<String>();
		for (String movie : watchedMovies) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je;
			// figure out IMDB ID of the movie title
			String req = "/search/movie/?" + key + "&query=" + encode(movie);
			HttpResponse<JsonNode> resp = Unirest.get(tmdb + req).asJson();
			je = jp.parse(resp.getBody().toString());
			int recommendationId = je.getAsJsonObject().get("results")
					.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt();
			// dubugging
			System.out.println(je.getAsJsonObject().get("results")
					.getAsJsonArray().get(0).getAsJsonObject().get("original_title").getAsString());
			// use now known IMDB ID to search for recommended movies
			String req2 = "/movie/" + recommendationId + "/recommendations?" + key;
			HttpResponse<JsonNode> resp2 = Unirest.get(tmdb + req2).asJson();
			je = jp.parse(resp2.getBody().toString());
			
			// extract original_title from the recommendations JSON Object
			JsonArray arr = je.getAsJsonObject().get("results").getAsJsonArray();
			for (int i = 0; i < arr.size(); i++) {
				recommendations.add(arr.get(i).getAsJsonObject()
						.get("original_title").getAsString());
			}
		}
		return recommendations;
	}
	
	private String encode(String val) throws UnsupportedEncodingException {
		return URLEncoder.encode(val, StandardCharsets.UTF_8.toString());
	}
}
