package com.nazarati.bmr.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	private String homepage = "homepage.html";
	@GetMapping("/")
	public String homepageController() {
		return homepage;
	}
	
	@PostMapping("/textsubmission")
	public String textSubmissionResponse(@RequestParam String movieString) {
		Set<String> movies = new HashSet<String>(Arrays.asList(movieString.split(" ")));
		return homepage;
	}
	
	@PostMapping("/filesubmission")
	public String fileSubmissionResponse() {
		return homepage;
	}
	
}
