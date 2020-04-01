package com.nazarati.bmr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.nazarati.bmr.service.FileFormService;
import com.nazarati.bmr.service.TextFormService;
import com.nazarati.bmr.storage.BmrStorageService;
import com.nazarati.bmr.storage.StorageService;

@Controller
public class HomeController {
	private String homepage = "homepage.html";
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
	public String homepageController() {
		return homepage;
	}
	
	@PostMapping("/textsubmission")
	public RedirectView textSubmissionResponse(@RequestParam("movies") String movieString) {
		TextFormService tfs = new TextFormService(movieString);
		return new RedirectView("/");
	}
	
	@PostMapping("/filesubmission")
	public RedirectView fileSubmissionResponse(@RequestParam("file") MultipartFile file) {
		ffs = new FileFormService(file);
		return new RedirectView("/");
	}
	
}
