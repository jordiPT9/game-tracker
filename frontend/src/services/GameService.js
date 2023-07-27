const API_BASE_URL = 'http://localhost:9000';

export const searchGame = async (searchTerm) => {
  const response = await fetch(`${API_BASE_URL}/games/search?word=${searchTerm}`);
  const searchResults = await response.json();
  return searchResults;
}

export const getAllGames = async () => {
  const response = await fetch(`${API_BASE_URL}/games`);
  const games = await response.json();
  return games;
}

export const createGame = async ({ id, title, rating, status }) => {
  try {
    const response = await fetch(`${API_BASE_URL}/games`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ id, title, rating, status }),
    });

    if (response.ok) {
      console.log('Game added successfully');
    } else {
      console.log('Error adding game:', response.statusText);
    }
  } catch (error) {
    console.log('Error adding game:', error);
  }
}

export const updateGame = async ({ id, title, rating, status }) => {
  try {
    const response = await fetch(`${API_BASE_URL}/games`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ id, title, rating, status }),
    });

    if (response.ok) {
      console.log('Game edited successfully');
    } else {
      console.log('Error editing game:', response.statusText);
    }
  } catch (error) {
    console.log('Error editing game:', error);
  }
}

export const deleteGame = async (gameId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/games?id=${gameId}`, { method: 'DELETE' });

    if (response.ok) {
      console.log('Game deleted successfully');
    } else {
      console.log('Error deleting game:', response.statusText);
    }
  } catch (error) {
    console.log('Error deleting game:', error);
  }
}
