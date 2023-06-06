import styles from './Modal.module.css';

export const Modal = ({ title, visible, onSave, onClose, children }) => {
  return (
    <div className={styles.add_game_modal_overlay} style={{ display: visible ? "flex" : "none" }}>
      <div className={styles.add_game_modal}>
        <div className={styles.add_game_modal_title}>{title}</div>
        
        {children}
        
        <div>
          <button onClick={onSave} style={{ float: "right", fontWeight: "bold" }}>SAVE</button>
          <button onClick={onClose} style={{ float: "right", fontWeight: "bold" }}>CLOSE</button>
        </div>
      </div>
    </div>
  )
}