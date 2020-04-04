package com.nazarati.bmr.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.nazarati.bmr.service.FileFormService;
import com.nazarati.bmr.service.RecommenderService;
import com.nazarati.bmr.service.TextFormService;
import com.nazarati.bmr.storage.StorageService;

@Controller
public class HomeController {
	private String homepage = "homepage";
	private final StorageService storageService;
	private FileFormService ffs;
	
	public FileFormService ffs() {
		return ffs;
	}
	@Autowired
	public HomeController(StorageService storageService) {
		this.storageService = storageService;
	}
	@GetMapping("/")
	public String homepageController(
			@ModelAttribute(name="recommendations") String recs,
			@ModelAttribute(name="recommendationposters") String posters,
			@ModelAttribute(name="badmovie") String badMovieError,
			Model model) {
		model.addAttribute("successfulquery", false);
		
		if (!recs.isEmpty() && !posters.isEmpty()) {
			model.addAttribute("successfulquery", true);
			model.addAttribute("recommendations", recs.split(","));
			model.addAttribute("recommendationposters", posters.split(","));	
		}
		if (badMovieError == "true") {
			model.addAttribute("badmovie", badMovieError);
		}
		return homepage;
	}
	
	@PostMapping("/textsubmission")
	public RedirectView textSubmissionResponse(
			@RequestParam("movies") String movieString,
			RedirectAttributes attributes) throws UnirestException, MalformedURLException, URISyntaxException, UnsupportedEncodingException {
		
		TextFormService tfs = new TextFormService(movieString);
		RecommenderService rs = new RecommenderService(tfs.movies());
		Pair<Set<String>, Set<String>> res = rs.recommend(attributes);
		addRecommendations(attributes, res);

		return new RedirectView("/");
	}
	
	@PostMapping("/filesubmission")
	public RedirectView fileSubmissionResponse(
			@RequestParam("file") MultipartFile file,
			RedirectAttributes attributes) throws IOException, URISyntaxException, UnirestException {
		ffs = new FileFormService(file);
		Pair<Set<String>, Set<String>> res = new RecommenderService(ffs.movies()).recommend(attributes);
		addRecommendations(attributes, res);
		return new RedirectView("/");
	}
	private void addRecommendations(RedirectAttributes attributes, Pair<Set<String>, Set<String>> res) {
		if (!(res.getValue0().isEmpty() && res.getValue1().isEmpty())) {
			attributes.addAttribute("recommendations", res.getValue0());
			attributes.addAttribute("recommendationposters", res.getValue1());
		}
	}
}
