import styles from './styles.module.css';
import { Game } from '../game/Game';
import { MdOutlineAddCircle } from 'react-icons/md'
import { useState } from 'react';

export const GameList = ({ title, data, listStatus, updateGameStatus, deleteGame, onClickTitle, onClickGame }) => {
  const [enter, setEnter] = useState(false);

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
        game={game}
        deleteGame={deleteGame}
        onClick={onClickGame}
      />
    ));
  }

  return (
    <div className={styles.list} onDrop={handleDrop} onDragOver={handleDragOver} type={listStatus}>
      <div className={styles.title} onClick={() => onClickTitle(listStatus)}>
        <span>{title}</span>
        <MdOutlineAddCircle
          style={{ fontSize: 27, float: "right", color: enter ? "green" : "black" }}
          onMouseEnter={() => { setEnter(true) }}
          onMouseLeave={() => { setEnter(false) }}
        />
      </div>
      {renderGames()}
    </div>
  );
}


export default GameList;