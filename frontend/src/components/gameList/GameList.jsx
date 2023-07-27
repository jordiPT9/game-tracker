import styles from './styles.module.css';
import { Game } from '../game/Game';

export const GameList = ({ title, data, listStatus, updateGameStatus, deleteGame, onClickTitle, onClickGame }) => {
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
      <div className={styles.title} onClick={() => onClickTitle(listStatus)}>{title}</div>
      {renderGames()}
    </div>
  );
}


export default GameList;