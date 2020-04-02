package com.nazarati.bmr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileFormService {
	private MultipartFile file;
	private Set<String> movies = new HashSet<String>();

	public FileFormService() {
	}


	public FileFormService(MultipartFile file) throws IOException {
		this.file = file;
		movies = parseFile();
	}


	public MultipartFile file() {
		return file;
	}


	public Set<String> movies(){
		return movies;
	}


	public Set<String> parseFile() throws IOException{
		String s = new String(file.getBytes(), "UTF-8");
		Set<String> res = new HashSet<String>(Arrays.asList(s.split("\r\n|\n|\r")));
		return res;
	}

}

