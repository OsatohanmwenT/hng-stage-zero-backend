package com.osato.hngstagezero;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.Map;

@Service
public class CatService {
	private static final String CAT_API_URL = "https://catfact.ninja/fact";

	public String getCatFact() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			Map response = restTemplate.getForObject(CAT_API_URL, Map.class);
			return response != null ? (String) response.get("fact") : "No cat fact available right now.";
		} catch (RestClientException e) {
			return "Failed to fetch cat fact. Try again later.";
		}
	}
}
