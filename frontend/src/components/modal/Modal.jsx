import { Button } from '../button/Button';
import styles from './styles.module.css';

export const Modal = ({ title, visible, onSave, onClose, children }) => {
  return (
    <div className={styles.modalOverlay} style={{ display: visible ? "flex" : "none" }}>
      <div className={styles.modal}>
        <div className={styles.title}>{title}</div>

        {children}

        <div>
          <Button onClick={onSave} style={{ float: "right", fontWeight: "bold" }}>SAVE</Button>
          <Button onClick={onClose} style={{ float: "right", fontWeight: "bold" }}>CLOSE</Button>
        </div>
      </div>
    </div>
  )
}