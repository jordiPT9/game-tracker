const express = require("express");
const fs = require("fs");
const cors = require("cors");
const bodyParser = require("body-parser");
const gateway = require('./gateway.js');

const gamesFilePath = "games.json";
const corsOptions = {
  origin: "*",
  credentials: true,
  optionSuccessStatus: 200,
};

const app = express();

app.use(express.json());
app.use(cors(corsOptions));
app.use(bodyParser.json());

app.post("/games", addGame);
app.delete("/games", removeGame);
app.put("/games", modifyGame);
app.get("/games", getAllGames);
app.get("/games/search", searchGame);

async function searchGame(req, res) {
  const { word } = req.query;

  try {
    const games = await gateway.searchGame(word);

    const sortedGames = games.sort(sortingAlgorithm);
    const topGames = sortedGames.slice(0, 10);
    const gamesWithCover = await Promise.all(topGames.map(game => getGameWithCover(game)));
    return res.json(gamesWithCover);
  } catch (error) {
    return res.status(500).json({ error: 'Internal server error' });
  }
}

async function getGameWithCover(game) {
  try {
    const cover = await gateway.getCover(game.cover);
    return { ...game, url: cover[0].url };
  } catch (error) {
    return game;
  }
}

function sortingAlgorithm(a, b) {
  const followsA = a.follows || 0;
  const followsB = b.follows || 0;
  return followsB - followsA;
}

function addGame(req, res) {
  const { id, title, rating, status } = req.body;

  if (!id || !title || !rating || !status) {
    return res.status(400).json({ error: "Missing required query parameters" });
  }

  const games = loadGames();
  games.push({ id, title, rating, status });
  saveGames(games);

  res.json({ message: "Game added successfully" });
}

function removeGame(req, res) {
  const { id } = req.query;

  if (!id) {
    return res
      .status(400)
      .json({ error: "Missing required query parameter: id" });
  }

  const games = loadGames();
  const index = games.findIndex((game) => game.id === id);

  if (index === -1) {
    return res.status(404).json({ error: "Game not found" });
  }

  games.splice(index, 1);
  saveGames(games);

  res.json({ message: "Game removed successfully" });
}

function modifyGame(req, res) {
  const { id, title, rating, status } = req.body;

  if (!id) {
    return res
      .status(400)
      .json({ error: "Missing required query parameter: id" });
  }

  const games = loadGames();
  const game = games.find((game) => game.id === id);

  if (!game) {
    return res.status(404).json({ error: "Game not found" });
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

  res.json({ message: "Game modified successfully" });
}

function getAllGames(req, res) {
  const games = loadGames();
  res.json(games);
}

function loadGames() {
  const data = fs.readFileSync(gamesFilePath);
  return JSON.parse(data);
}

function saveGames(games) {
  fs.writeFileSync(gamesFilePath, JSON.stringify(games, null, 2));
}

app.listen(9000, console.log("Server started on port 8080..."));
