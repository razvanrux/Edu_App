import React, { useState } from "react";
import axios from "axios";
import api from "../api/axiosConfig.js";
import API_CONFIG from "../api/config.js";
import { useNavigate } from "react-router";

const Login = ({ onLogin }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [students, setStudents] = useState([]);
  let navigate = useNavigate();
  const fetchStudents = async () => {
    try {
      const token = localStorage.getItem("authToken"); 
      const response = await axios.get(`${API_CONFIG.REST_BASE_URL}/api/students`, {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
        withCredentials: true, 
      });

      setStudents(response.data); // Update the state with fetched student data
    } catch (err) {
      setError("Failed to fetch students.");
      console.error("Error fetching students:", err);
    }
  };
  
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(`${API_CONFIG.REST_BASE_URL}/auth/login`, {
        email,
        password,
      });
      const { token, role } = response.data;

      localStorage.setItem("authToken", token);
      localStorage.setItem("role", role);    
      
      if (role === "STUDENT") {
        navigate("/students");
      } else if (role === "PROFESOR") {
        navigate("/profesor-homepage");
      } 
      else if (role === "ADMIN") {
        navigate("/admin-dashboard");
      }
      onLogin(token);
      
    } catch (err) {
      setError("Invalid email or password.");
    }
  };

  return (
    <div>
      <h1>Login</h1>
      <form onSubmit={handleLogin}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
};

export default Login;
