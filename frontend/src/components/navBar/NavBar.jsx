import React from 'react';
import styles from './NavBar.module.css';
// import { RxCross2 } from 'react-icons/rx'

export const NavBar = ({ value, onChange, onEnter, onResetField }) => {
  return (
    <nav className={styles.navbar}>
      <input
        className={styles.search_input}
        placeholder='Search game'
        value={value}
        onChange={onChange}
        onKeyDown={(evt) => {
          if (evt.key === 'Enter') onEnter();
        }}
      />
      {/* <button className={styles.search_button} onClick={onResetField}><RxCross2/></button> */}
    </nav>
  );
};
