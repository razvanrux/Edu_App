
import './App.css';
import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import StudentList from "./components/StudentList.js";
import Navbar from './components/Navbar.jsx';
import ProfesorHomepage from "./pages/ProfesorHomepage.jsx";
import AdminDashboard from "./pages/AdminDashboard.jsx";
import Login from "./pages/Login.jsx";
import StudentProfile from "./pages/StudentProfile.jsx";
function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("authToken"));

  const handleLogin = () => {
    setIsLoggedIn(true);
  };
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route
          path="/login"
          element={<Login onLogin={handleLogin} />}
        />
        <Route
          path="/students"
          element={
              <StudentList />
          }
        />
        <Route path="/students" element={<StudentProfile />} />
        <Route path="/professor-dashboard" element={<ProfesorHomepage />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
      </Routes>
  </Router>
  );
}

export default App;
