package com.osato.hngstagezero;

public record MeResponse(
		String status,
		UserDto user,
		String timestamp,
		String fact
) {}
