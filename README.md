# Edu App - short description

---

## Projects

### 1. **MySQL MicroService**
A robust backend service designed to handle academic-related data, including **students**, **professors**, and **disciplines**. It provides:
- **Authentication & Authorization:** Role-based access for Admins, Professors, and Students.
- **CRUD Operations:** Comprehensive management of students, professors, and disciplines.
- **REST API:** Fully functional endpoints for integrations with other services or frontends.

**What Works:**
- Secure login/logout mechanisms using JWT.
- CRUD operations for all entities.
- Role-based access control implemented at the endpoint level.

---

### 2. **MongoDB MicroService**
An extension of the MySQL service, focusing on **course materials** and **evaluation probes**. It ensures data consistency by relying on the existence of courses in the MySQL database before adding related content.

**What Works:**
- CRUD operations for course materials and evaluation probes.
- Weight validation for evaluation probes to ensure consistency.
- Integration with MySQL for course existence checks.

---

### 3. **Moodle Simulation**
A lightweight ReactJS frontend designed to simulate basic academic interactions:
- **Student Role:** View courses and student profiles.
- **Professor Role:** Manage courses and interact with student data.
- **Admin Role:** Oversee users and handle account management.

**What Works:**
- Role-based dashboards with partial functionality.
- Dynamic menu rendering based on user roles.
- Basic student listing fetched via the backend.

---

### 4. **Password Verifier**
A utility microservice for hashing and testing passwords. It simplifies secure password handling during development and testing phases.

**What Works:**
- Password hashing using industry-standard algorithms.
- Straightforward integration for password security testing.

---