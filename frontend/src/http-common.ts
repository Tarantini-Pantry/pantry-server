import axios from 'axios';

const baseUrl = 'https://api.kitchen-pantry.com/v1';

export default axios.create({
  baseURL: baseUrl,
  headers: {
    'Content-type': 'application/json',
  },
});
