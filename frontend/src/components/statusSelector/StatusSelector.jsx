import styles from './styles.module.css';

export const StatusSelector = ({ options, value, handleValueChange }) => {
  const statusSelectorStyle = {
    backgroundColor: "#ffdf52",
    textShadow: "0px 0px 10px rgba(0, 0, 0, 0.3)",
    color: "black"
  }

  return (
    <div className={styles.status_selector}>
      {options.map((option) => (
        <span
          className={styles.status_selector_button}
          key={option}
          style={value === option ? statusSelectorStyle : {}}
          onClick={(evt) => handleValueChange(evt.target.id)}
          id={option}
        >
          {option}
        </span>
      ))}
    </div>
  );
};