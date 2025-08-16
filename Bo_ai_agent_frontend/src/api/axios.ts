import axios from 'axios';

const http = axios.create({
  baseURL: '/api',
  timeout: 60000
});

http.interceptors.response.use(
  (res) => res,
  (err) => Promise.reject(err)
);

export default http;

