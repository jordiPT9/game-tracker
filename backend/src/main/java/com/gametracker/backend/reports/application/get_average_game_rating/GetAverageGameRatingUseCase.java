package com.gametracker.backend.reports.application.get_average_game_rating;

import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAverageGameRatingUseCase {
    private final LibraryGameRepository libraryGameRepository;

    public GetAverageGameRatingUseCase(LibraryGameRepository libraryGameRepository) {
        this.libraryGameRepository = libraryGameRepository;
    }

    public GetAverageGameRatingResponse execute(GetAverageGameRatingRequest request) {
        Double rating = libraryGameRepository.getAverageGameRating(request.gameTitle());

        if (rating == null) {
            return new GetAverageGameRatingResponse(0);
        }

        return new GetAverageGameRatingResponse(rating.doubleValue());
    }
}
