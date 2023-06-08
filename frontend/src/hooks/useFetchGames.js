import { useEffect, useState } from 'react';
import { getAllGames } from '../services/GameService';

export const useFetchGames = () => {
  const [wantToPlayGames, setWantToPlayGames] = useState([]);
  const [playingGames, setPlayingGames] = useState([]);
  const [playedGames, setPlayedGames] = useState([]);
  const [abandonedGames, setAbandonedGames] = useState([]);

  const compareGamesByRating = (a, b) => {
    const NO_RATING = -0.1;
    const ratingA = a.rating;
    const ratingB = b.rating;

    if (ratingA === NO_RATING || ratingB === NO_RATING) {
      if (ratingA === NO_RATING && ratingB !== NO_RATING) {
        return 1;
      } else if (ratingB === NO_RATING && ratingA !== NO_RATING) {
        return -1;
      } else {
        return a.title.localeCompare(b.title);
      }
    }

    const result = ratingB - ratingA;
    if (result === 0) {
      return a.title.localeCompare(b.title);
    }

    return ratingB - ratingA;
  }

  const fetchGames = async () => {
    getAllGames()
      .then(gameList => {
        const sortedWantToPlayGames = gameList.filter(game => game.status === 'Want to play').sort(compareGamesByRating);
        const sortedPlayingGames = gameList.filter(game => game.status === 'Playing').sort(compareGamesByRating);
        const sortedPlayedGames = gameList.filter(game => game.status === 'Played').sort(compareGamesByRating);
        const sortedAbandonedGames = gameList.filter(game => game.status === 'Abandoned').sort(compareGamesByRating);

        setWantToPlayGames(sortedWantToPlayGames);
        setPlayingGames(sortedPlayingGames);
        setPlayedGames(sortedPlayedGames);
        setAbandonedGames(sortedAbandonedGames);
      })
      .catch(error => {
        setWantToPlayGames([]);
        setPlayingGames([]);
        setPlayedGames([]);
        setAbandonedGames([]);
        console.log(error)
      })
  }

  useEffect(() => {
    fetchGames();
  });

  return {
    games: { wantToPlayGames, playingGames, playedGames, abandonedGames },
    fetchGames
  }
}