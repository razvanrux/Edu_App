import axios from "axios";
import API_CONFIG from "./config";

const api = axios.create({
  baseURL: API_CONFIG.REST_BASE_URL,
  headers: {"ngrok-skip-browser-warning":"true"}
});

api.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem("authToken");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

export default api;
