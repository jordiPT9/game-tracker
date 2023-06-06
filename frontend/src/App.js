import React, { useEffect, useState } from 'react';
import styles from './App.module.css';
import { v4 as uuid } from 'uuid';
import { getGames, createGame, updateGame, deleteGame } from './api/GameApi';

import { List } from './components/list/List';
import { Modal } from './components/modal/Modal';

const App = () => {
  const [wantToPlayGames, setWantToPlayGames] = useState([]);
  const [playingGames, setPlayingGames] = useState([]);
  const [playedGames, setPlayedGames] = useState([]);
  const [abandonedGames, setAbandonedGames] = useState([]);

  const [isAddGameModalVisible, setIsAddGameModalVisible] = useState(false);
  const [isEditGameModalVisible, setIsEditGameModalVisible] = useState(false);

  const [currentGameId, setCurrentGameId] = useState("");
  const [currentGameRating, setCurrentGameRating] = useState("-0.1");
  const [currentGameTitle, setCurrentGameTitle] = useState("");
  const [currentGameStatus, setCurrentGameStatus] = useState("Want to play");

  useEffect(() => {
    loadGames();
  });

  async function loadGames() {
    getGames()
      .then(gameList => {
        const sortedWantToPlayGames = gameList.filter(game => game.status === 'Want to play').sort(compareGamesByRating);
        const sortedPlayingGames = gameList.filter(game => game.status === 'Playing').sort(compareGamesByRating);
        const sortedPlayedGames = gameList.filter(game => game.status === 'Played').sort(compareGamesByRating);
        const sortedAbandonedGames = gameList.filter(game => game.status === 'Abandoned').sort(compareGamesByRating);

        setWantToPlayGames(sortedWantToPlayGames);
        setPlayingGames(sortedPlayingGames);
        setPlayedGames(sortedPlayedGames);
        setAbandonedGames(sortedAbandonedGames);
      })
      .catch(error => {
        setWantToPlayGames([]);
        setPlayingGames([]);
        setPlayedGames([]);
        setAbandonedGames([]);
        console.log(error)
      })
  }

  function compareGamesByRating(a, b) {
    const ratingA = parseFloat(a.rating);
    const ratingB = parseFloat(b.rating);

    if (ratingA === -0.1 && ratingB !== -0.1) {
      return 1;
    } else if (ratingB === -0.1 && ratingA !== -0.1) {
      return -1;
    } else if (ratingA === -0.1 && ratingB === -0.1) {
      return a.title.localeCompare(b.title)
    }

    return ratingB - ratingA;
  }

  async function editGame(id, title, rating, status) {
    await updateGame({ id, title, rating, status });
    await loadGames();
  };

  async function editGameStatus(id, status) {
    await updateGame({ id, status });
    await loadGames();
  };

  async function removeGame(id) {
    await deleteGame(id)
    await loadGames();
  }

  function handleGameClick(id, title, rating, status) {
    console.log(id)
    setCurrentGameId(id);
    setCurrentGameTitle(title);
    setCurrentGameRating(rating);
    setCurrentGameStatus(status);
    setIsEditGameModalVisible(true);
  }

  function handleListTitleClick(status) {
    setCurrentGameStatus(status);
    setIsAddGameModalVisible(true);
  }

  function handleCloseAddGameModal() {
    setIsAddGameModalVisible(false);
    resetModalFields()
  }

  function handleCloseEditGameModal() {
    setIsEditGameModalVisible(false);
    resetModalFields()
  }

  function handleNewGameRatingChange(evt) {
    const value = parseFloat(evt.target.value).toFixed(1);
    setCurrentGameRating(value.toString());
  };

  function handleNewGameTitleChange(evt) {
    setCurrentGameTitle(evt.target.value);
  };

  function handleNewGameStatusChange(evt) {
    setCurrentGameStatus(evt.target.id);
  }

  async function saveNewGame() {
    await createGame({ id: uuid(), title: currentGameTitle, rating: currentGameRating.toString(), status: currentGameStatus });
    await loadGames();

    setIsAddGameModalVisible(false);
    resetModalFields();
  }

  async function saveEditedGame() {
    await editGame(currentGameId, currentGameTitle, currentGameRating.toString(), currentGameStatus);
    await loadGames();

    setIsEditGameModalVisible(false);
    resetModalFields();
  }

  function resetModalFields() {
    setCurrentGameId("");
    setCurrentGameRating("-0.1");
    setCurrentGameTitle("");
    setCurrentGameStatus("Want to play");
  }

  const statusSelectorStyle = { backgroundColor: "#ffdf52", textShadow: "0px 0px 10px rgba(0, 0, 0, 0.3)", color: "black" }
  const gameStatusOptions = ["Want to play", "Playing", "Played", "Abandoned"];

  return (
    <div className="app">
      <Modal
        title={"ADD GAME"}
        visible={isAddGameModalVisible}
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
          <p className={styles.rating_value} style={{ color: currentGameRating === "-0.1" ? "rgba(255, 255, 255, 0.25)" : "white" }}>
            {currentGameRating === "-0.1" ? "No rating" : currentGameRating}
          </p>
        </div>
      </Modal>

      <Modal
        title={"EDIT GAME"}
        visible={isEditGameModalVisible}
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
          <p className={styles.rating_value} style={{ color: currentGameRating === "-0.1" ? "rgba(255, 255, 255, 0.25)" : "white" }}>
            {currentGameRating === "-0.1" ? "No rating" : currentGameRating}
          </p>
        </div>
      </Modal>

      <div>
        <List
          title="Want to play"
          listStatus="Want to play"
          data={wantToPlayGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick("Want to play")}
          onClickGame={handleGameClick}
        />

        <List
          title="Playing"
          listStatus="Playing"
          data={playingGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick("Playing")}
          onClickGame={handleGameClick}
        />

        <List
          title="Played"
          listStatus="Played"
          data={playedGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick("Played")}
          onClickGame={handleGameClick}
        />

        <List
          title="Abandoned"
          listStatus="Abandoned"
          data={abandonedGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={() => handleListTitleClick("Abandoned")}
          onClickGame={handleGameClick}
        />
      </div>
    </div>
  );
}

export default App;