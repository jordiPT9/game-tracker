import React from 'react';
import styles from './Input.module.css';

export const Input = ({ value, handleValueChange }) => {
  return (
    <input
      className={styles.game_title_input}
      value={value}
      placeholder="Enter your game"
      onChange={(evt) => handleValueChange(evt.target.value)}
    />
  );
};