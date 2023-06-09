import { useEffect, useState } from 'react';
import { getAllGames } from '../services/gameService';
import { STATUS } from "../constants/constants";

export const useFetchGames = () => {
  const [wantToPlayGames, setWantToPlayGames] = useState([]);
  const [playingGames, setPlayingGames] = useState([]);
  const [playedGames, setPlayedGames] = useState([]);
  const [abandonedGames, setAbandonedGames] = useState([]);

  const compareGamesByRating = (a, b) => {
    const ratingA = a.rating || Number.NEGATIVE_INFINITY;
    const ratingB = b.rating || Number.NEGATIVE_INFINITY;
  
    return ratingB - ratingA || a.title.localeCompare(b.title);
  }

  const fetchGames = async () => {
    getAllGames()
      .then(gameList => {
        const sortedWantToPlayGames = gameList.filter(game => game.status === STATUS.WANT_TO_PLAY).sort(compareGamesByRating);
        const sortedPlayingGames = gameList.filter(game => game.status === STATUS.PLAYING).sort(compareGamesByRating);
        const sortedPlayedGames = gameList.filter(game => game.status === STATUS.PLAYED).sort(compareGamesByRating);
        const sortedAbandonedGames = gameList.filter(game => game.status === STATUS.ABANDONED).sort(compareGamesByRating);

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
    games: {
      wantToPlayGames,
      playingGames,
      playedGames,
      abandonedGames
    },
    fetchGames
  }
}