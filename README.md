# MoveBuddy backend

This repository contains the Spring Boot API and PostgreSQL persistence layer for [MoveBuddy](https://github.com/romansimunovic/MoveBuddyApp), a Social Impact Award project that helps people take the first step toward regular movement by making it easier to record activity and connect with others.

The API provides registration and JWT login, saved walking/running/cycling activities, cumulative points, a member list and invitations between signed-in community members. It is designed to be consumed by the Expo mobile app.

## Run locally

Requirements: Java 17 and PostgreSQL.

Set these environment variables before starting the application:

```text
DB_URL=jdbc:postgresql://localhost:5432/movebuddy
DB_USERNAME=postgres
DB_PASSWORD=your-password
JWT_SECRET_KEY=a-long-random-secret-of-at-least-32-characters
```

Then run from `backend/backend`:

```bash
./mvnw spring-boot:run
```

On Windows use `mvnw.cmd spring-boot:run`. The service listens on port 8080 unless Render or `PORT` provides another port.

## Render deployment

The deployed API is intended to run at `https://movebuddy-db.onrender.com`.

In Render, point the web service to this repository and set the Root Directory to `backend/backend`. Use:

```text
Build Command: ./mvnw clean package -DskipTests
Start Command: java -jar target/backend-0.0.1-SNAPSHOT.jar
```

Add `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` and `JWT_SECRET_KEY` as secret environment variables. Never commit these values. Render supplies `PORT` automatically.

## API overview

Public endpoints:

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/` — simple service check

Authenticated endpoints:

- `GET /api/users`
- `GET /api/activities/user/{userId}`
- `POST /api/activities`
- `POST /api/invitations/send`

Send the token returned at login as `Authorization: Bearer <token>`. Password hashes and Expo push tokens are excluded from JSON responses.

## GenAI transparency

MoveBuddy was conceived, directed and reviewed by its author. ChatGPT/Codex with the **GPT-5.6 Terra** model was used as assistance for technical organisation, code troubleshooting and documentation editing. The model is described by OpenAI as a GPT-5.6 model designed to balance intelligence and cost. Final decisions, verification and responsibility remain with the project author. [OpenAI model documentation](https://developers.openai.com/api/docs/models/gpt-5.6-terra)

## License

MIT.
