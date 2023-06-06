const express = require('express');
const fs = require('fs');
const cors = require("cors");
const bodyParser = require('body-parser');

const corsOptions = {
  origin: '*',
  credentials: true,
  optionSuccessStatus: 200,
}

const app = express();
const gamesFilePath = 'games.json';

app.use(express.json());
app.use(cors(corsOptions))
app.use(bodyParser.json());

app.post('/games', (req, res) => {
  const { id, title, rating, status } = req.body;

  if (!id || !title || !rating || !status) {
    return res.status(400).json({ error: 'Missing required query parameters' });
  }

  const games = getAllGames();
  games.push({ id, title, rating, status });
  saveGames(games);

  res.json({ message: 'Game added successfully' });
});

app.delete('/games', (req, res) => {
  const { id } = req.query;

  if (!id) {
    return res.status(400).json({ error: 'Missing required query parameter: id' });
  }

  const games = getAllGames();
  const index = games.findIndex(game => game.id === id);

  if (index === -1) {
    return res.status(404).json({ error: 'Game not found' });
  }

  games.splice(index, 1);
  saveGames(games);

  res.json({ message: 'Game removed successfully' });
});

app.put('/games', (req, res) => {
  const { id, title, rating, status } = req.body;

  if (!id) {
    return res.status(400).json({ error: 'Missing required query parameter: id' });
  }

  const games = getAllGames();
  const game = games.find(game => game.id === id);

  if (!game) {
    return res.status(404).json({ error: 'Game not found' });
  }

  if (title) {
    game.title = title;
  }

  if (rating) {
    game.rating = rating;
  }

  if (status) {
    game.status = status;
  }

  saveGames(games);

  res.json({ message: 'Game modified successfully' });
});

app.get('/games', (req, res) => {
  const games = getAllGames();
  res.json(games);
});

function getAllGames() {
  const data = fs.readFileSync(gamesFilePath);
  return JSON.parse(data);
}

function saveGames(games) {
  fs.writeFileSync(gamesFilePath, JSON.stringify(games, null, 2));
}

app.listen(8080, console.log('Server started on port 8080...'));
