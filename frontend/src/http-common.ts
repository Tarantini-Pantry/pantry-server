import axios from 'axios';

const baseUrl = 'http://api.kitchen-pantry.com:8080/v1';

export default axios.create({
  baseURL: baseUrl,
  headers: {
    'Content-type': 'application/json',
  },
});
