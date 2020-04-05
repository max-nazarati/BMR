package com.nazarati.bmr.service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TextFormService 
{
	private Set<String> movies;
	

	public Set<String> movies()
	{
		return movies;
	}
	

	public TextFormService() 
	{
		
	}
	

	public TextFormService(String movies) 
	{
		this.movies = parseMovieString(movies);
	}
	

	private Set<String> parseMovieString(String movies) 
	{
		// expecting movie titles to be separated by single quotes
		Set<String> res = new LinkedHashSet<String>(Arrays.asList(movies.split("' +'|'")));
		res.remove("");
		return res;
	}

}
