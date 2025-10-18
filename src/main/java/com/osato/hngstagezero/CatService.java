package com.osato.hngstagezero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
public class CatService {
	private static final Logger log = LoggerFactory.getLogger(CatService.class);

	private final WebClient webClient;
	private final Duration timeout;
	private final String fallback;

	public CatService(
			@Value("${catfact.url:https://catfact.ninja/fact}") String catFactUrl,
			@Value("${catfact.timeout-ms:5000}") long timeoutMs,
			@Value("${catfact.fallback:Could not fetch a cat fact right now.}") String fallback
	) {
		this.webClient = WebClient.builder().baseUrl(catFactUrl).build();
		this.timeout = Duration.ofMillis(timeoutMs);
		this.fallback = fallback;
		log.debug("CatService initialized with url={}, timeoutMs={}, fallback='{}'", catFactUrl, timeoutMs, fallback);
	}

	public String getFact() {
		try {
			log.debug("Requesting cat fact from external API");
			Mono<Map> mono = webClient.get()
									  .retrieve()
									  .bodyToMono(Map.class)
									  .timeout(timeout);

			Map<String, Object> body = mono.block(timeout);
			if (body == null) {
				log.warn("CatService.getFact: response body null, returning fallback");
				return fallback;
			}
			Object fact = body.get("fact");
			if (fact == null) {
				log.warn("CatService.getFact: 'fact' key missing in response, returning fallback");
				return fallback;
			}
			log.info("Fetched cat fact successfully");
			return fact.toString();
		} catch (Exception e) {
			log.error("Failed to fetch cat fact: {}", e.toString());
			return fallback;
		}
	}
}
