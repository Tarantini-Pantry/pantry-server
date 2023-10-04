import axios from 'axios';

// const baseUrl = 'https://api.kitchen-pantry.com/v1';
const baseUrl = 'http://localhost:8080/v1';

export default axios.create({
  baseURL: baseUrl,
  withCredentials: true
});
