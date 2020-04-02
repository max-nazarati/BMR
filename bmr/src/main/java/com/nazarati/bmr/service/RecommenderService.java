package com.nazarati.bmr.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
		String movie = "Avengers: Endgame";
		String req = "/search/movie/?" + key + "&query=" + encode(movie);
		System.out.println(req);
		HttpResponse<JsonNode> resp = Unirest.get(tmdb + req).asJson();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(resp.getBody().toString());
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);
		return null;
	}
	
	private String encode(String val) throws UnsupportedEncodingException {
		return URLEncoder.encode(val, StandardCharsets.UTF_8.toString());
	}
}
