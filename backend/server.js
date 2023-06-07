const express = require("express");
const fs = require("fs");
const cors = require("cors");
const bodyParser = require("body-parser");

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

app.listen(8080, console.log("Server started on port 8080..."));
