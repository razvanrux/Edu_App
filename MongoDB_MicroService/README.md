# MongoDB Microservice Project: Testing Instructions

## Authentication and Authorization

### Test Credentials:

- **Professor:**
  - Username: `professor`
  - Password: `prof123`
- **Student:**
  - Username: `student`
  - Password: `stud123`

---

## Commands to Test

### 1. As Professor

#### **Create a Course**
- **Method**: `POST`
- **URL**: `http://localhost:8080/courses`
- **Body**:
    ```json
    {
        "id": "CS101",
        "courseName": "Advanced Databases",
        "studyYear": 1,
        "evaluationProbes": [
            { "type": "Lab", "weight": 30 },
            { "type": "Project", "weight": 40 }
        ]
    }
    ```
- **Expected Response**: `200`

#### **Create an Invalid Course (Does Not Exist in MySQL)**
- **Method**: `POST`
- **URL**: `http://localhost:8080/courses`
- **Body**:
    ```json
    {
        "id": "CS999",
        "courseName": "Non-Existent Course",
        "studyYear": 3
    }
    ```
- **Expected Response**: `400`

#### **Update a Course**
- **Method**: `PUT`
- **URL**: `http://localhost:8080/courses/{courseId}`
- **Body**:
    ```json
    {
        "courseName": "Advanced Databases - Updated",
        "evaluationProbes": [
            { "type": "Lab", "weight": 30 },
            { "type": "Project", "weight": 40 },
            { "type": "Final Exam", "weight": 30 }
        ]
    }
    ```
- **Expected Response**: `200`

#### **Delete a Course**
- **Method**: `DELETE`
- **URL**: `http://localhost:8080/courses/{courseId}`
- **Expected Response**: `200`

#### **Add Evaluation Probe**
- **Method**: `POST`
- **URL**: `http://localhost:8080/courses/{courseId}/evaluation-probes`
- **Body**:
    ```json
    {
        "type": "Quiz",
        "weight": 10
    }
    ```
- **Expected Response**: `200`

#### **Validation for Weight**
- Add probes such that total weight exceeds 100%.
- **Expected Response**: `400`

#### **Add Course Materials**
- **Method**: `POST`
- **URL**: `http://localhost:8080/courses/{courseId}/materials`
- **Body**:
    ```json
    {
        "name": "Lecture 1",
        "type": "PDF",
        "content": "Base64EncodedContentHere"
    }
    ```
- **Expected Response**: `200`

### 2. As Student

#### **View All Courses**
- **Method**: `GET`
- **URL**: `http://localhost:8080/courses`
- **Expected Response**: `200`

#### **View Specific Course**
- **Method**: `GET`
- **URL**: `http://localhost:8080/courses/{courseId}`
- **Expected Response**: `200`

#### **Attempt to Create a Course**
- **Method**: `POST`
- **URL**: `http://localhost:8080/courses`
- **Expected Response**: `403`

#### **View Course Materials**
- **Method**: `GET`
- **URL**: `http://localhost:8080/courses/{courseId}/materials`
- **Expected Response**: `200`

---

## Summary of Expected Codes

- `200`: Successful operation
- `400`: Validation error (e.g., total weight exceeds 100%, course not found in MySQL)
- `403`: Forbidden (e.g., student trying to create a course)
- `404`: Not found (e.g., non-existent course ID)
- `401`: Unauthorized (e.g., invalid credentials)

