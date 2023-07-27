import styles from './styles.module.css';

export const GameResult = ({ game }) => {

  // useEffect(() => { console.log(game) }, [])

  const handleDragStart = (evt) => {
    //evt.dataTransfer.setData("gameId", id)
  }

  return (
    <div
      className={styles.game}
      id={game.name}
      draggable
      onDragStart={handleDragStart}
    >
      <img className={styles.coverImage} src={game.url} alt={game.title} />
      <div className={styles.wrapper}>
        <div style={{ padding: "5px 5px 5px 0px" }}>
          <p className={styles.title}>{game.title}</p>
          <p className={styles.releaseDate}>{game.releaseDate}</p>
        </div>
      </div>
    </div>
  );
}