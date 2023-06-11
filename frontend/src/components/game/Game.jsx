import React, { useState } from 'react';
import styles from './Game.module.css';
import { STATUS, NO_RATING } from '../../constants/constants';
import { MdModeEditOutline, MdOutlineDragIndicator } from "react-icons/md";

export const Game = ({ game, deleteGame, onClick }) => {
  const [isHovered, setIsHovered] = useState(false);
  const { id, title, rating, status } = game;

  const handleDragStart = (evt) => {
    evt.dataTransfer.setData("gameId", id)
  }

  const handleClick = () => {
    onClick(id, title, rating, status);
  }

  const handleRightClick = (evt) => {
    evt.preventDefault();
    deleteGame(id);
  };

  const getBorderStyle = () => {
    if (status === STATUS.WANT_TO_PLAY) {
      return "3px solid #f1aa68";
    }
    else if (status === STATUS.PLAYING) {
      return "3px solid #7ff168";
    }
    else if (status === STATUS.PLAYED) {
      return "3px solid #6881f1";
    }
    else if (status === STATUS.ABANDONED) {
      return "3px solid #f16868";
    }
  }

  const getRatingColor = () => {
    if (rating === 100) {
      return "rgb(191, 104, 241)";
    }
    else if (rating >= 90 && rating < 100) {
      return "rgb(104, 236, 241)";
    }
    else if (rating >= 80 && rating < 90) {
      return "rgb(104, 241, 161)";
    }
    else if (rating >= 70 && rating < 80) {
      return "rgb(154, 241, 104)";
    }
    else if (rating >= 60 && rating < 70) {
      return "rgb(241, 232, 104)";
    }
    else if (rating >= 50 && rating < 60) {
      return "rgb(241, 159, 104)";
    }
    else if (rating >= 0 && rating < 50) {
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
      <MdOutlineDragIndicator style={{ marginRight: "5px", color: "rgba(255, 255, 255, 0.2)", fontSize: "20px" }} />
      {title}
      {isHovered && <MdModeEditOutline className={styles.edit_icon} onClick={handleClick} />}
      <div className={styles.rating} style={{ color: getRatingColor() }} onClick={handleClick}>
        {rating === NO_RATING ? "" : rating/10}
      </div>
    </div>
  );
}