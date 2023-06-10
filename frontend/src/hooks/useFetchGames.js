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

  const fetchGames = () => {
    getAllGames()
      .then(gameList => {
        const sortedGameList = gameList.sort(compareGamesByRating)

        const sortedWantToPlayGames = sortedGameList.filter(game => game.status === STATUS.WANT_TO_PLAY);
        const sortedPlayingGames = sortedGameList.filter(game => game.status === STATUS.PLAYING);
        const sortedPlayedGames = sortedGameList.filter(game => game.status === STATUS.PLAYED);
        const sortedAbandonedGames = sortedGameList.filter(game => game.status === STATUS.ABANDONED);

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

  useEffect(fetchGames);

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