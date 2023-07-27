import styles from './styles.module.css';
import { AiOutlineSearch } from 'react-icons/ai'

export const NavBar = ({ value, onChange, onEnter }) => {
  return (
    <div className={styles.topNav}>
      <div className={styles.logo}>Game<span className={styles.whiteText}>tracker_</span></div>
      <div className={styles.inputWrapper}>
        <AiOutlineSearch style={{fontSize: 20, marginLeft: 7, color: "#888"}}/>
        <input
          placeholder=" Search game"
          value={value}
          onChange={onChange}
          onKeyDown={(evt) => {
            if (evt.key === 'Enter') onEnter();
          }}
        />
      </div>
      <div className={styles.profileWrapper}>
        <div>Alviin</div>
        <div className={styles.profile}>
          <img src="/profile.jpg" alt="Profile logo" />
        </div>
      </div>
    </div >
  );
};
