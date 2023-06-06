export const getGames = async () => {
  const response = await fetch('http://localhost:8080/games');
  const games = await response.json();
  return games;
}

export const createGame = async ({ id, title, rating, status }) => {
  try {
    const response = await fetch('http://localhost:8080/games', {
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
    const response = await fetch('http://localhost:8080/games', {
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

export const deleteGame = async (id) => {
  try {
    const response = await fetch(`http://localhost:8080/games?id=${id}`, { method: 'DELETE' });

    if (response.ok) {
      console.log('Game deleted successfully');
    } else {
      console.log('Error deleting game:', response.statusText);
    }
  } catch (error) {
    console.log('Error deleting game:', error);
  }
}