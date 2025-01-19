import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  const role = localStorage.getItem("userRole");

  const renderMenu = () => {
    if (role === "STUDENT") {
      return (
        <>
          <Link to="/my_courses">My Courses</Link>
          <Link to="/students">Students</Link>
        </>
      );
    } else if (role === "PROFESOR") {
      return (
        <>
          <Link to="/my_courses">My Courses</Link>
          <Link to="/change_courses">Change Courses</Link>
          <Link to="/students">Students</Link>
          <Link to="/professors">Professors</Link>
        </>
      );
    } else if (role === "ADMIN") {
      return (
        <>
          <Link to="/admin_dashboard">Admin Dashboard</Link>
          <Link to="/users">Manage Users</Link>
        </>
      );
    } else {
      return null; 
    }
  };

  return (
    <nav>
      <h2>Navbar</h2>
      {renderMenu()}
    </nav>
  );
};

export default Navbar;
