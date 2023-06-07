import React from 'react';
import styles from './List.module.css';
import { Game } from '../game/Game';

export const List = ({ title, data, listStatus, updateGameStatus, deleteGame, onClickTitle, onClickGame }) => {
  const handleDrop = async (evt) => {
    evt.preventDefault();
    let gameId = evt.dataTransfer.getData("gameId");
    await updateGameStatus(gameId, listStatus);
  };

  const handleDragOver = (evt) => {
    evt.preventDefault();
  }

  const renderGames = () => {
    return data.map(game => (
      <Game
        key={game.id}
        id={game.id}
        title={game.title}
        rating={game.rating}
        status={game.status}
        deleteGame={deleteGame}
        onClick={onClickGame}
      />
    ));
  }

  return (
    <div className={styles.list} onDrop={handleDrop} onDragOver={handleDragOver} type={listStatus}>
      <div className={styles.list_title} onClick={onClickTitle}>{title}</div>
      {renderGames()}
    </div>
  );
}


export default List;