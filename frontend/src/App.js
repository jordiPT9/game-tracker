import React, { useState } from 'react';
import styles from './App.module.css';
import { v4 as uuid } from 'uuid';
import { createGame, updateGame, deleteGame } from './services/gameService';
import { STATUS } from './constants/constants'
import { List } from './components/list/List';
import { Modal } from './components/modal/Modal';
import { useFetchGames } from './hooks/useFetchGames';
import { useModal } from './hooks/useModal';

const App = () => {
  const { games, fetchGames } = useFetchGames();
  const addGameModal = useModal();
  const editGameModal = useModal();

  const [currentGameId, setCurrentGameId] = useState("");
  const [currentGameRating, setCurrentGameRating] = useState(-0.1);
  const [currentGameTitle, setCurrentGameTitle] = useState("");
  const [currentGameStatus, setCurrentGameStatus] = useState(STATUS.WANT_TO_PLAY);

  const editGame = async (id, title, rating, status) => {
    await updateGame({ id, title, rating, status });
    await fetchGames();
  };

  const editGameStatus = async (id, status) => {
    await updateGame({ id, status });
    await fetchGames();
  };

  const removeGame = async (id) => {
    await deleteGame(id)
    await fetchGames();
  }

  const handleGameClick = (id, title, rating, status) => {
    setCurrentGameId(id);
    setCurrentGameTitle(title);
    setCurrentGameRating(rating);
    setCurrentGameStatus(status);
    editGameModal.showModal();
  }

  const handleListTitleClick = (status) => {
    setCurrentGameStatus(status);
    addGameModal.showModal();
  }

  const handleCloseAddGameModal = () => {
    addGameModal.closeModal();
    resetModalFields()
  }

  const handleCloseEditGameModal = () => {
    editGameModal.closeModal();
    resetModalFields()
  }

  const handleNewGameRatingChange = (evt) => {
    const value = parseFloat(evt.target.value);
    setCurrentGameRating(value);
  };

  const handleNewGameTitleChange = (evt) => {
    setCurrentGameTitle(evt.target.value);
  };

  const handleNewGameStatusChange = (evt) => {
    setCurrentGameStatus(evt.target.id);
  }

  const saveNewGame = async () => {
    await createGame({ id: uuid(), title: currentGameTitle, rating: currentGameRating, status: currentGameStatus });
    await fetchGames();

    addGameModal.closeModal();
    resetModalFields();
  }

  const saveEditedGame = async () => {
    await editGame(currentGameId, currentGameTitle, currentGameRating, currentGameStatus);
    await fetchGames();

    editGameModal.closeModal();
    resetModalFields();
  }

  const resetModalFields = () => {
    setCurrentGameId("");
    setCurrentGameRating(-0.1);
    setCurrentGameTitle("");
    setCurrentGameStatus(STATUS.WANT_TO_PLAY);
  }

  const statusSelectorStyle = {
    backgroundColor: "#ffdf52",
    textShadow: "0px 0px 10px rgba(0, 0, 0, 0.3)",
    color: "black"
  }
  const gameStatusOptions = [
    STATUS.WANT_TO_PLAY,
    STATUS.PLAYING,
    STATUS.PLAYED,
    STATUS.ABANDONED
  ];

  return (
    <div className="app">
      <Modal
        title={"ADD GAME"}
        visible={addGameModal.isVisible}
        onSave={saveNewGame}
        onClose={handleCloseAddGameModal}
      >
        <input
          className={styles.game_title_input}
          value={currentGameTitle}
          placeholder="Enter your game"
          onChange={handleNewGameTitleChange}
        />

        <div className={styles.status_selector}>
          {gameStatusOptions.map(statusOption => (
            <span
              className={styles.status_selector_button}
              key={statusOption}
              style={currentGameStatus === statusOption ? statusSelectorStyle : {}}
              onClick={handleNewGameStatusChange}
              id={statusOption}
            >
              {statusOption}
            </span>
          ))}
        </div>

        <div style={{ display: "flex", alignItems: "center", margin: "10px 5px 10px 10px" }}>
          <p>Rating</p>
          <input className={styles.rating_slider} type="range" min={-0.1} max={10} step={0.1} value={currentGameRating} onChange={handleNewGameRatingChange} />
          <p className={styles.rating_value} style={{ color: currentGameRating === -0.1 ? "rgba(255, 255, 255, 0.25)" : "white" }}>
            {currentGameRating === -0.1 ? "No rating" : currentGameRating}
          </p>
        </div>
      </Modal>

      <Modal
        title={"EDIT GAME"}
        visible={editGameModal.isVisible}
        onSave={saveEditedGame}
        onClose={handleCloseEditGameModal}
      >
        <input
          className={styles.game_title_input}
          value={currentGameTitle}
          placeholder="Enter your game"
          onChange={handleNewGameTitleChange}
        />

        <div className={styles.status_selector}>
          {gameStatusOptions.map(statusOption => (
            <span
              className={styles.status_selector_button}
              key={statusOption}
              style={currentGameStatus === statusOption ? statusSelectorStyle : {}}
              onClick={handleNewGameStatusChange}
              id={statusOption}
            >
              {statusOption}
            </span>
          ))}
        </div>

        <div style={{ display: "flex", alignItems: "center", margin: "10px 5px 10px 10px" }}>
          <p>Rating</p>
          <input className={styles.rating_slider} type="range" min={-0.1} max={10} step={0.1} value={currentGameRating} onChange={handleNewGameRatingChange} />
          <p className={styles.rating_value} style={{ color: currentGameRating === -0.1 ? "rgba(255, 255, 255, 0.25)" : "white" }}>
            {currentGameRating === -0.1 ? "No rating" : currentGameRating}
          </p>
        </div>
      </Modal>

      <div style={{ display: "flex" }}>
        <List
          title={STATUS.WANT_TO_PLAY}
          listStatus={STATUS.WANT_TO_PLAY}
          data={games.wantToPlayGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick(STATUS.WANT_TO_PLAY)}
          onClickGame={handleGameClick}
        />

        <List
          title={STATUS.PLAYING}
          listStatus={STATUS.PLAYING}
          data={games.playingGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick(STATUS.PLAYING)}
          onClickGame={handleGameClick}
        />

        <List
          title={STATUS.PLAYED}
          listStatus={STATUS.PLAYED}
          data={games.playedGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick(STATUS.PLAYED)}
          onClickGame={handleGameClick}
        />

        <List
          title={STATUS.ABANDONED}
          listStatus={STATUS.ABANDONED}
          data={games.abandonedGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick(STATUS.ABANDONED)}
          onClickGame={handleGameClick}
        />
      </div>
    </div>
  );
}

export default App;