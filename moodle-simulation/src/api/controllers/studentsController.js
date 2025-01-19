import api from "../axiosConfig.js";
import API_CONFIG from "../config";
import axios from "axios";

export const fetchStudents = async () => {
  const token = localStorage.getItem("authToken");
  const response = await axios.get(`${API_CONFIG.REST_BASE_URL}/api/students`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data.data; // Extract the "data" array
};