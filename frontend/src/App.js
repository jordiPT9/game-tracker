import React, { useState } from 'react';
import './App.css';
import { v4 as uuid } from 'uuid';
import { createGame, updateGame, deleteGame } from './services/gameService';
import { STATUS, NO_RATING } from './constants/constants'
import { GameList } from './components/gameList/GameList';
import { Modal } from './components/modal/Modal';
import { Rating } from './components/rating/Rating';
import { StatusSelector } from './components/statusSelector/StatusSelector';
import { Input } from './components/input/Input';
import { useFetchGames } from './hooks/useFetchGames';
import { useModal } from './hooks/useModal';

const { WANT_TO_PLAY, PLAYING, PLAYED, ABANDONED } = STATUS;
const DEFAULT_GAME = {
  id: "",
  title: "",
  status: WANT_TO_PLAY,
  rating: NO_RATING
}

const App = () => {
  const { games, fetchGames } = useFetchGames();
  const addGameModal = useModal();
  const editGameModal = useModal();

  const [currentGame, setCurrentGame] = useState(DEFAULT_GAME);

  const handleGameClick = (id, title, rating, status) => {
    setCurrentGame({ id, title, rating, status });
    editGameModal.showModal();
  }

  const handleListTitleClick = (status) => {
    setCurrentGame(prevGame => { return { ...prevGame, status } });
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

  const handleCurrentGameRatingChange = (rating) => {
    setCurrentGame(prevGame => { return { ...prevGame, rating } });
  };

  const handleCurrentGameTitleChange = (title) => {
    setCurrentGame(prevGame => { return { ...prevGame, title } });

  };

  const handleCurrentGameStatusChange = (status) => {
    setCurrentGame(prevGame => { return { ...prevGame, status } });
  }

  const editGameStatus = async (id, status) => {
    await updateGame({ id, status });
    fetchGames();
  };

  const removeGame = async (id) => {
    await deleteGame(id)
    fetchGames();
  }

  const saveNewGame = async () => {
    await createGame({ ...currentGame, id: uuid() });
    fetchGames();

    addGameModal.closeModal();
    resetModalFields();
  }

  const saveEditedGame = async () => {
    const { id, title, rating, status } = currentGame;
    await updateGame({ id, title, rating, status });
    fetchGames();

    editGameModal.closeModal();
    resetModalFields();
  }

  const resetModalFields = () => {
    setCurrentGame(DEFAULT_GAME);
  }

  const gameStatusOptions = [
    WANT_TO_PLAY,
    PLAYING,
    PLAYED,
    ABANDONED
  ];

  return (
    <div className="app">
      <Modal
        title={"ADD GAME"}
        visible={addGameModal.isVisible}
        onSave={saveNewGame}
        onClose={handleCloseAddGameModal}
      >
        <Input value={currentGame.title} handleValueChange={handleCurrentGameTitleChange} />
        <StatusSelector options={gameStatusOptions} value={currentGame.status} handleValueChange={handleCurrentGameStatusChange} />
        <Rating value={currentGame.rating} handleValueChange={handleCurrentGameRatingChange} />
      </Modal>

      <Modal
        title={"EDIT GAME"}
        visible={editGameModal.isVisible}
        onSave={saveEditedGame}
        onClose={handleCloseEditGameModal}
      >
        <Input value={currentGame.title} handleValueChange={handleCurrentGameTitleChange} />
        <StatusSelector options={gameStatusOptions} value={currentGame.status} handleValueChange={handleCurrentGameStatusChange} />
        <Rating value={currentGame.rating} handleValueChange={handleCurrentGameRatingChange} />
      </Modal>

      <div style={{ display: "flex" }}>

        <GameList
          title="Want to play"
          listStatus={WANT_TO_PLAY}
          data={games.wantToPlayGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={handleListTitleClick}
          onClickGame={handleGameClick}
        />

        <GameList
          title="Playing"
          listStatus={PLAYING}
          data={games.playingGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={handleListTitleClick}
          onClickGame={handleGameClick}
        />

        <GameList
          title="Played"
          listStatus={PLAYED}
          data={games.playedGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={handleListTitleClick}
          onClickGame={handleGameClick}
        />

        <GameList
          title="Abandoned"
          listStatus={ABANDONED}
          data={games.abandonedGames}
          updateGameStatus={editGameStatus}
          deleteGame={removeGame}
          onClickTitle={handleListTitleClick}
          onClickGame={handleGameClick}
        />
      </div>
    </div>
  );
}

export default App;