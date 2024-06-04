package com.gametracker.backend.reports.infrastructure.endpoints.get_average_game_rating;

import com.gametracker.backend.reports.application.get_average_game_rating.GetAverageGameRatingRequest;
import com.gametracker.backend.reports.application.get_average_game_rating.GetAverageGameRatingResponse;
import com.gametracker.backend.reports.application.get_average_game_rating.GetAverageGameRatingUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class GetAverageGameRatingController {
    private final GetAverageGameRatingUseCase getAverageGameRatingUseCase;

    public GetAverageGameRatingController(GetAverageGameRatingUseCase getAverageGameRatingUseCase) {
        this.getAverageGameRatingUseCase = getAverageGameRatingUseCase;
    }

    @GetMapping("/reports/average-game-rating/{gameTitle}")
    public ResponseEntity<GetAverageGameRatingResponse> execute(Principal principal, @PathVariable String gameTitle) {
        GetAverageGameRatingRequest request = new GetAverageGameRatingRequest(gameTitle, principal.getName());
        GetAverageGameRatingResponse response = getAverageGameRatingUseCase.execute(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
