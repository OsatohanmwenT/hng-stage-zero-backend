# HNG Stage 0 — Profile Endpoint (Spring Boot)

A minimal Spring Boot REST API exposing `GET /me` that returns profile information and a dynamic cat fact fetched from `https://catfact.ninja/fact`.

## Features
- `GET /me` returns JSON with exact structure required by the spec.
- Fetches a fresh cat fact on every request.
- Returns `timestamp` in UTC ISO-8601.
- Graceful fallback when external API fails.
- Basic logging for debugging.
- Configurable via environment variables.
