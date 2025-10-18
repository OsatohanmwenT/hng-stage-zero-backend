package com.osato.hngstagezero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class MeController {
	private static final Logger log = LoggerFactory.getLogger(MeController.class);

	private final CatService catService;
	private final String email;
	private final String name;
	private final String stack;

	public MeController(CatService catService,
						@Value("${profile.email:youremail@example.com}") String email,
						@Value("${profile.name:Your Full Name}") String name,
						@Value("${profile.stack:Java/Spring Boot}") String stack) {
		this.catService = catService;
		this.email = email;
		this.name = name;
		this.stack = stack;
		log.debug("MeController initialized with profile email={}, name={}, stack={}", email, name, stack);
	}

	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	public MeResponse me() {
		log.info("GET /me received");
		UserDto user = new UserDto(email, name, stack);
		String timestamp = Instant.now().toString(); // ISO 8601 UTC
		String fact = catService.getFact();
		MeResponse response = new MeResponse("success", user, timestamp, fact);
		log.debug("Responding to /me with timestamp={} and fact-length={}", timestamp, fact != null ? fact.length() : 0);
		return response;
	}
}
