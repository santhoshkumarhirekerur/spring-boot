package com.optus.rest.pojos;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

public class WordCount {

	@JsonView(View.Summary.class)
	Map<String,Integer> counts;
	List<String> searchText;

	
	public Map<String, Integer> getWords() {
		return counts;
	}

	public void setWords(Map<String, Integer> words) {
		this.counts = words;
	}

	public List<String> getSearchText() {
		return searchText;
	}

	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	} 
	
	
}
