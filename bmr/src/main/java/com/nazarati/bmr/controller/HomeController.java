package com.nazarati.bmr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	@GetMapping("/")
	public String homepageController() {
		return "homepage.html";
	}
	
	@PostMapping("/textsubmission")
	public @ResponseBody String textSubmissionResponse() {
		return "text submission";
	}
	
	@PostMapping("/filesubmission")
	public @ResponseBody String fileSubmissionResponse() {
		return "file submission";
	}
	
}
