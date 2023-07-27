import styles from './styles.module.css'

export const Button = ({ onClick, style, children }) => {
  return <>
    <button className={styles.button} onClick={onClick} style={style}>
      {children}
    </button>
  </>
}