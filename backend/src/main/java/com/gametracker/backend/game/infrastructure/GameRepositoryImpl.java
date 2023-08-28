package com.gametracker.backend.game.infrastructure;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class GameRepositoryImpl implements GameRepository {
    private static final String BASE_URL = "https://api.igdb.com/v4";
    private static final String CLIENT_ID = "3w9bxydpqm8mukoc9c8h1qo62lx6ci";
    private static final String ACCESS_TOKEN = "nd1zylrs3n4dom0zoutrdlgt754s1g";

    private final RestTemplate restTemplate;

    public GameRepositoryImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Optional<Game> findGame(String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", CLIENT_ID);
        headers.setBearerAuth(ACCESS_TOKEN);
        headers.setContentType(MediaType.TEXT_PLAIN);

        String body = "fields *; where name = \"" + title + "\";";
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL + "/games",
                HttpMethod.POST,
                requestEntity,
                String.class);
        JSONArray gamesJsonArray = new JSONArray(response.getBody());

        if (gamesJsonArray.length() == 0) {
            return Optional.empty();
        }

        JSONObject gameJsonObject = gamesJsonArray.getJSONObject(0);
        Game game = Game.builder()
                .title(gameJsonObject.getString("name"))
                .follows(gameJsonObject.getInt("follows"))
                .releaseDate(new Timestamp(gameJsonObject.getLong("first_release_date")).toString())
                .build();

        return Optional.of(game);
    }
}
