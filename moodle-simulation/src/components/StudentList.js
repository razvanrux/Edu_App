import React, { useEffect, useState } from "react";
import { fetchStudents } from "../api/controllers/studentsController.js";

const StudentList = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const getStudents = async () => {
      try {
        const data = await fetchStudents();
        setStudents(data.map((item) => item.data)); // Map to extract student data
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    getStudents();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h1>Student List</h1>
      <ul>
        {students.map((student) => (
          <li key={student.id}>
            {student.nume} {student.prenume}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StudentList;