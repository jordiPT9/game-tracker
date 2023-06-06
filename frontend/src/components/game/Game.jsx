import React, { useState } from 'react';
import styles from './Game.module.css';
import { MdModeEditOutline } from "react-icons/md";

export const Game = ({ id, title, rating, status, deleteGame, onClick }) => {
  const [isHovered, setIsHovered] = useState(false);

  function handleDragStart(evt) {
    evt.dataTransfer.setData("gameId", id)
  }

  function handleClick() {
    onClick(id, title, rating, status);
  }

  function handleRightClick(evt) {
    evt.preventDefault();
    deleteGame(id);
  };

  function getBorderStyle() {
    if (status === "Want to play") {
      return "3px solid #f1cc68";
    }
    else if (status === "Playing") {
      return "3px solid #7ff168";
    }
    else if (status === "Played") {
      return "3px solid #6881f1";
    }
    else if (status === "Abandoned") {
      return "3px solid #f16868";
    }
  }

  function getRatingColor() {
    const value = parseFloat(rating);

    if (value === 10) {
      return "rgb(191, 104, 241)";
    }
    else if (value >= 9 && value < 10) {
      return "rgb(104, 236, 241)";
    }
    else if (value >= 8 && value < 9) {
      return "rgb(104, 241, 161)";
    }
    else if (value >= 7 && value < 8) {
      return "rgb(154, 241, 104)";
    }
    else if (value >= 6 && value < 7) {
      return "rgb(241, 232, 104)";
    }
    else if (value >= 5 && value < 6) {
      return "rgb(241, 159, 104)";
    }
    else if (value >= 0 && value < 5) {
      return "rgb(241, 104, 104)";
    } else {
      return "white";
    }
  }

  return (
    <div
      className={styles.game}
      id={id}
      draggable
      style={{ borderLeft: getBorderStyle() }}
      onContextMenu={handleRightClick}
      onDragStart={handleDragStart}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >

      {title}
      {
        isHovered && <MdModeEditOutline
          className={styles.edit_icon}
          onClick={handleClick}
        />
      }
      <div className={styles.rating} style={{ color: getRatingColor() }} onClick={handleClick}>
        {rating === "-0.1" ? "" : rating}
      </div>
    </div>
  );
}