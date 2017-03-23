package com.optus.rest.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.io.IOUtils;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optus.rest.pojos.View;
import com.optus.rest.pojos.WordCount;

@RestController
public class WordCountController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	String fileText;

	@PostConstruct
	public void init() {
		logger.info("Loading text file from resournces");
		fileText = getFile("file/test_text.txt");
	}

	@JsonView(View.Summary.class)
	@RequestMapping(value = "/counter-api/search", method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WordCount> searchWords(@RequestBody String input) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			
			WordCount wordCount = mapper.readValue(input, WordCount.class);
			List<String> searchTextList = wordCount.getSearchText();
			List<String> list = Stream.of(fileText).map(w -> w.split("\\s+")).flatMap(Arrays::stream)
					.collect(Collectors.toList());

			Map<String, Integer> wordCounterMap = list.stream().filter(x -> searchTextList.contains(x))
					.collect(Collectors.toMap(w -> w.toLowerCase(), w -> 1, Integer::sum));

			System.out.println(wordCounterMap);

			for (String searchText : searchTextList) {
				Integer count = wordCounterMap.get(searchText.toLowerCase());
				if (count == null) {
					wordCounterMap.put(searchText, 0);
				}

			}

			wordCount.setWords(wordCounterMap);
			return new ResponseEntity<WordCount>(wordCount, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error in method:searchWords class: WordCountController" + e.getMessage(), e);
			
			throw new ConstraintViolationException("error", Collections.emptySet());
			
		}

		
	}

	@JsonView(View.Summary.class)
	@RequestMapping(value = "/counter-api/top/{topCount}")
	public String topWordCount(HttpServletResponse response, @PathVariable Integer topCount) {
		WordCount wordCount = new WordCount();
		try {

			List<ObjectIntPair<String>> pairs = Stream.of(fileText).map(w -> w.split("\\s+")).flatMap(Arrays::stream)
					.collect(Collectors.toCollection(TreeBag::new)).topOccurrences(topCount);
			TreeMap<String, Integer> wordCounterMap = new TreeMap<String, Integer>();
			for (ObjectIntPair<String> objectIntPair : pairs) {
				wordCounterMap.put(objectIntPair.getOne(), objectIntPair.getTwo());
			}
			wordCount.setWords(sortMapByValues(wordCounterMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/csv; charset=utf-8");
		StringBuilder sb = new StringBuilder();
		Map<String, Integer> map = wordCount.getWords();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			sb.append(entry.getKey() + "," + entry.getValue() + "\n");
		}
		return sb.toString();
	}

	// below function is to sort map on values.
	private <K, V extends Comparable<V>> Map<K, V> sortMapByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				final V v1 = map.get(k1);
				final V v2 = map.get(k2);

				/* Not sure how to handle nulls ... */
				if (v1 == null) {
					return (v2 == null) ? 0 : 1;
				}

				int compare = v2.compareTo(v1);
				if (compare != 0) {
					return compare;
				} else {
					Integer h1 = k1.hashCode();
					Integer h2 = k2.hashCode();
					return h2.compareTo(h1);
				}
			}
		};
		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	private String getFile(String fileName) {

		String result = "";

		ClassLoader classLoader = getClass().getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			logger.error("Error in method:getFile class: WordCountController" + e.getMessage(), e);
		}

		return result;

	}
}
