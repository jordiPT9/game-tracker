const axios = require('axios');

const clientId = '3w9bxydpqm8mukoc9c8h1qo62lx6ci';
const accessToken = 'nd1zylrs3n4dom0zoutrdlgt754s1g';

async function getCover(id) {
  const url = 'https://api.igdb.com/v4/covers';
  const headers = { 'Client-ID': clientId, 'Authorization': `Bearer ${accessToken}`, 'Content-Type': 'text/plain' };
  const body = `fields *; where id = ${id};`;

  try {
    const response = await axios.post(url, body, { headers });
    return response.data;
  } catch (err) {
    return err;
  }
}

async function searchGame(word) {
  const url = 'https://api.igdb.com/v4/games';
  const headers = { 'Client-ID': clientId, 'Authorization': `Bearer ${accessToken}`, 'Content-Type': 'text/plain' };
  const body = `fields *; search "${word}"; limit 500;`;

  try {
    const response = await axios.post(url, body, { headers });
    return response.data;
  } catch (err) {
    throw new Error(err);
  }
}

module.exports = {
  getCover,
  searchGame
};