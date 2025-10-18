package com.osato.hngstagezero;

import org.springframework.web.bind.annotation.*;
import java.time.Instant;

@RestController
public class MeController {
	private final CatService catService;

	public MeController(CatService catService) {
		this.catService = catService;
	}

	@GetMapping("/me")
	public MeResponse getProfile() {
		UserDto user = new UserDto(
				"osarenkhoeosato45@gmail.com",
				"Osato Osarenkhoe",
				"Java/Spring Boot"
		);

		return new MeResponse(
				"success",
				user,
				Instant.now().toString(),
				catService.getCatFact()
		);
	}
}
