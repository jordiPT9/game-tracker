import React, { useEffect } from 'react';
import styles from './GameResult.module.css';
import { MdOutlineDragIndicator } from "react-icons/md";

export const GameResult = ({ game }) => {

  useEffect(() => { console.log(game) }, [])

  const handleDragStart = (evt) => {
    //evt.dataTransfer.setData("gameId", id)
  }

  return (
    <div
      className={styles.game}
      id={game.name}
      draggable
      onDragStart={handleDragStart}
    >
      <img className={styles.game_image} src={game.url} alt={game.title} />
      <div>
        <p className={styles.game_title}>{game.title}</p>
        <p className={styles.release_date}>{game.releaseDate}</p>
      </div>
    </div>
  );
}