import styles from './styles.module.css';

export const Input = ({ value, handleValueChange }) => {
  return (
    <input
      className={styles.titleInput}
      value={value}
      placeholder="Enter your game"
      onChange={(evt) => handleValueChange(evt.target.value)}
    />
  );
};