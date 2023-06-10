import React from "react";
import styles from "./Rating.module.css";
import { NO_RATING } from '../../constants/constants'


export const Rating = ({ value, handleValueChange }) => {
  return (
    <div className={styles.rating_container}>
      <p>Rating</p>
      <input
        className={styles.rating_slider}
        type="range"
        min={NO_RATING}
        max={10}
        step={0.1}
        value={value}
        onChange={(evt) => handleValueChange(parseFloat(evt.target.value))}
      />
      <p
        className={styles.rating_value}
        style={{
          color: value === NO_RATING ? "rgba(255, 255, 255, 0.25)" : "white",
        }}
      >
        {value === NO_RATING ? "No rating" : value}
      </p>
    </div>
  );
};