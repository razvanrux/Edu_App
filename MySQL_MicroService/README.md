# Backend API Test Commands

This document outlines test instructions for verifying the functionality of the backend API endpoints using Postman. The commands cover **Students**, **Professors**, **Disciplines**, and **Utilizatori** modules, as well as authentication mechanisms. Totally not written by my good ol' friend ChatGPT

## Base URLs
- **REST API:** `http://localhost:8082/api`
- **Authentication:** `http://localhost:8082/auth/login`
- **Logout:** `http://localhost:8082/auth/logout`

---

## Authentication

### Login
**Endpoint:**
```
POST /auth/login
```
**Request Body:**
```json
{
  "email": "admin@example.com",
  "password": "secureAdminPass123"
}
```
**Expected Response:**
- Status Code: 200
- Response Body:
```json
{
  "token": "<JWT_TOKEN>"
}
```
**Invalid Request:**
- Incorrect email or password.
- Expected Status: 401 Unauthorized

### Logout
**Endpoint:**
```
POST /auth/logout
```
**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```
**Expected Response:**
- Status Code: 200
- Response Body:
```json
{
  "message": "Successfully logged out"
}
```

---

## Students

### Get All Students
**Endpoint:**
```
GET /students
```
**Expected Response:**
- Status Code: 200
- Response Body:
```json
[
  {
    "id": 1,
    "nume": "Popescu",
    "prenume": "Ion",
    "email": "ion.popescu@student.university.edu",
    "cicluStudii": "LICENTA",
    "anStudiu": 1,
    "grupa": 101
  }
]
```

### Get Student by ID
**Endpoint:**
```
GET /students/{id}
```
**Example:**
```
GET /students/1
```
**Expected Response:**
- Status Code: 200
- Response Body:
```json
{
  "id": 1,
  "nume": "Popescu",
  "prenume": "Ion",
  "email": "ion.popescu@student.university.edu",
  "cicluStudii": "LICENTA",
  "anStudiu": 1,
  "grupa": 101
}
```
**Error Response:**
- Status Code: 404 Not Found
- Message: "Student with ID {id} not found"

### Add a New Student
**Endpoint:**
```
POST /students
```
**Request Body:**
```json
{
  "nume": "Ionescu",
  "prenume": "Maria",
  "email": "maria.ionescu@student.university.edu",
  "cicluStudii": "MASTER",
  "anStudiu": 2,
  "grupa": 202
}
```
**Expected Response:**
- Status Code: 201 Created
- Response Body:
```json
{
  "id": 2,
  "nume": "Ionescu",
  "prenume": "Maria",
  "email": "maria.ionescu@student.university.edu",
  "cicluStudii": "MASTER",
  "anStudiu": 2,
  "grupa": 202
}
```
**Error Response:**
- Status Code: 409 Conflict
- Message: "A student with the email {email} already exists"

### Delete a Student
**Endpoint:**
```
DELETE /students/{id}
```
**Example:**
```
DELETE /students/1
```
**Expected Response:**
- Status Code: 200 OK
- Response Body:
```json
{
  "message": "Student deleted successfully"
}
```
**Error Response:**
- Status Code: 404 Not Found
- Message: "Student with ID {id} not found"

---

## Professors

### Get All Professors
**Endpoint:**
```
GET /professors
```
**Expected Response:**
- Status Code: 200
- Response Body:
```json
[
  {
    "id": 1,
    "nume": "Georgescu",
    "prenume": "Ana",
    "email": "ana.georgescu@university.edu",
    "gradDidactic": "PROF",
    "tipAsociere": "TITULAR",
    "afiliere": "Departamentul de Fizica"
  }
]
```
**Error Response:**
- Status Code: 404 Not Found
- Message: "No professors found"

### Add a New Professor
**Endpoint:**
```
POST /professors
```
**Request Body:**
```json
{
  "nume": "Popescu",
  "prenume": "Ion",
  "email": "ion.popescu@university.edu",
  "gradDidactic": "CONF",
  "tipAsociere": "ASOCIAT",
  "afiliere": "Departamentul de Matematica"
}
```
**Expected Response:**
- Status Code: 201 Created
  **Error Response:**
- Status Code: 409 Conflict
- Message: "A professor with the email {email} already exists"

---

## Utilizatori

### Create a New User
**Method:**
```
UserService.CreateUser
```
**Request Body:**
```json
{
  "email": "newuser@example.com",
  "password": "securepassword123",
  "role": "STUDENT"
}
```
**Expected Response:**
- Status Code: 200
- Response Body:
```json
{
  "id": 3,
  "email": "newuser@example.com",
  "role": "STUDENT"
}
```
**Error Response:**
- Status Code: 409 Conflict
- Message: "A user with the email {email} already exists"

---
