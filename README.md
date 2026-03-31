# Dream Dev Election Management System

An election management REST API built with Spring Boot and MongoDB, providing user authentication, election creation, candidate management, and voting capabilities.
This project aims to introduce students into Maven and give them a soft landing when learning Spring Boot.

## Table of Contents

- [What It Does](#what-it-does)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Building the Project](#building-the-project)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
  - [Register a User](#register-a-user)
  - [Login](#login)
  - [Logout](#logout)
  - [Create Election](#create-election)
  - [End Election](#end-election)
  - [Get All Elections](#get-all-elections)
  - [Add Candidate](#add-candidate)
  - [Get Candidates for Election](#get-candidates-for-election)
  - [Cast Vote](#cast-vote)
  - [Get Votes for Election](#get-votes-for-election)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Testing](#testing)
- [Contributing](#contributing)
- [Support](#support)

## What It Does

Dream Dev Election Management System is a production-ready REST API that handles end-to-end election workflows. It provides secure user registration, authentication, election lifecycle management, candidate registration, and voting with built-in duplicate vote prevention, error handling, and validation.

## Features

- **User Registration** - Create new user accounts with email validation and role assignment
- **User Authentication** - Secure login with email and password verification
- **Session Management** - Track user login/logout status via unique login IDs
- **Election Management** - Create and close elections with status tracking (OPEN/CLOSE)
- **Candidate Management** - Register candidates to specific elections with party affiliation
- **Voting System** - Cast votes for candidates in active elections with duplicate vote prevention
- **Vote Counting** - Track vote counts per candidate in real-time
- **Duplicate Prevention** - Prevent duplicate email registrations and duplicate votes
- **Error Handling** - Comprehensive exception handling with meaningful error messages
- **MongoDB Integration** - Persistent data storage with MongoDB
- **Standardized API Responses** - Uniform response format across all endpoints
- **Unit & Integration Tests** - Comprehensive test coverage

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MongoDB 4.0+ (running and accessible)

## Getting Started

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/election.git
   cd election
   ```

2. **Configure MongoDB connection:**

   Update the `application.properties` file in `src/main/resources/`:

   **application.properties:**
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/election
   ```

   **Or using application.yml:**
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/election
   ```

3. **Verify MongoDB is running:**
   ```bash
   mongosh  # or mongo for older versions
   ```

### Building the Project

```bash
mvn clean install
```

This will:
- Compile the source code
- Run all unit and integration tests
- Package the application as a JAR file

### Running the Application

```bash
mvn spring-boot:run
```

Or run the compiled JAR:
```bash
java -jar target/election-1.0-SNAPSHOT.jar
```

The application will start on `http://localhost:8080` by default.

## API Endpoints

### Register a User

**Endpoint:** `POST /user`

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "Password123!"
}
```

**Success Response (HTTP 201):**
```json
{
  "success": true,
  "data": {
    "id": "69cb82d8614f1279cb66e1b0",
    "email": "john.doe@example.com",
    "role": "USER"
  }
}
```

**Error Response (HTTP 400):**
```json
{
  "success": false,
  "data": "User with email john.doe@example.com already exists"
}
```

### Login

**Endpoint:** `PATCH /user/login`

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "Password123!"
}
```

**Success Response (HTTP 200):**
```json
{
  "success": true,
  "data": {
    "userId": "69cb82d8614f1279cb66e1b0",
    "loginId": "f8aa6a65-d354-49d8-aa3f-e862e3c5d9c8",
    "message": "Login successful"
  }
}
```

**Error Response (HTTP 400):**
```json
{
  "success": false,
  "data": "Invalid email or password"
}
```

### Logout

**Endpoint:** `PATCH /user/logout`

**Request Body:**
```json
{
  "loginId": "f8aa6a65-d354-49d8-aa3f-e862e3c5d9c8"
}
```

**Success Response (HTTP 200):**
```json
{
  "success": true,
  "data": {
    "message": "Logout successful"
  }
}
```

**Error Response (HTTP 400):**
```json
{
  "success": false,
  "data": "User is not logged in"
}
```

### Create Election

**Endpoint:** `POST /election`

**Request Body:**
```json
{
  "title": "presidential Election"
}
```

**Success Response (HTTP 201):**
```json
{
  "success": true,
  "data": {
    "id": "69cbba2609e5fe02458b17ff",
    "title": "presidential Election",
    "status": "OPEN"
  }
}
```

### End Election

**Endpoint:** `PATCH /election`

**Request Body:**
```json
{
  "electionId": "69cbba2609e5fe02458b17ff"
}
```

**Success Response (HTTP 200):**
```json
{
  "success": true,
  "data": {
    "id": "69cbba2609e5fe02458b17ff",
    "title": "presidential Election",
    "status": "CLOSE"
  }
}
```

**Error Response (HTTP 400):**
```json
{
  "success": false,
  "data": "Election not found"
}
```

### Get All Elections

**Endpoint:** `GET /election`

**Success Response (HTTP 200):**
```json
{
  "success": true,
  "data": [
    {
      "id": "69cbba2609e5fe02458b17ff",
      "title": "presidential Election",
      "status": "OPEN"
    }
  ]
}
```

### Add Candidate

**Endpoint:** `POST /candidate`

**Request Body:**
```json
{
  "name": "zainab",
  "party": "pdp",
  "electionId": "69cbba2609e5fe02458b17ff"
}
```

**Success Response (HTTP 201):**
```json
{
  "success": true,
  "data": {
    "id": "69cbc43977e7e63822df8b2d",
    "name": "zainab",
    "party": "pdp",
    "voteCount": 0,
    "electionId": "69cbba2609e5fe02458b17ff"
  }
}
```

### Get Candidates for Election

**Endpoint:** `GET /candidate/{electionId}`

**Path Parameters:**
- `electionId` - The ID of the election

**Success Response (HTTP 200):**
```json
{
  "success": true,
  "data": [
    {
      "id": "69cbbf6e605d86bbe2fe999b",
      "name": "jojo",
      "party": "APC",
      "voteCount": 0,
      "electionId": "69cbba2609e5fe02458b17ff"
    },
    {
      "id": "69cbc43977e7e63822df8b2d",
      "name": "zainab",
      "party": "pdp",
      "voteCount": 0,
      "electionId": "69cbba2609e5fe02458b17ff"
    }
  ]
}
```

### Cast Vote

**Endpoint:** `POST /vote`

**Request Body:**
```json
{
  "candidateId": "69cbd4bf299543724d6f6166",
  "electionId": "69cbd4aa299543724d6f6165",
  "loginId": "8b7e4da6-1eb5-41e9-8422-11c47d60891e"
}
```

**Success Response (HTTP 201):**
```json
{
  "success": true,
  "data": {
    "voteId": "69cbd67ba1aaf89fdf569167",
    "candidateId": "69cbd4bf299543724d6f6166",
    "electionId": "69cbd4aa299543724d6f6165",
    "createdAt": "2026-03-31T07:13:15.8327304"
  }
}
```

**Error Response (HTTP 400):**
```json
{
  "success": false,
  "data": "Vote has be casted"
}
```

### Get Votes for Election

**Endpoint:** `GET /vote/{electionId}`

**Path Parameters:**
- `electionId` - The ID of the election

**Success Response (HTTP 200):**
```json
{
  "success": true,
  "data": [
    {
      "voteId": "69cbd67ba1aaf89fdf569167",
      "candidateId": "69cbd4bf299543724d6f6166",
      "electionId": "69cbd4aa299543724d6f6165",
      "createdAt": "2026-03-31T07:13:15.8327304"
    }
  ]
}
```

## Project Structure

```
election/
├── src/
│   ├── main/
│   │   ├── java/dreamDev/moniepoint/
│   │   │   ├── Main.java                              # Spring Boot entry point
│   │   │   ├── controllers/
│   │   │   │   ├── UserController.java                # User REST endpoints
│   │   │   │   ├── ElectionController.java            # Election REST endpoints
│   │   │   │   ├── CandidateController.java           # Candidate REST endpoints
│   │   │   │   └── VoteController.java                # Vote REST endpoints
│   │   │   ├── services/
│   │   │   │   ├── UserService.java                   # User service interface
│   │   │   │   ├── UserServiceImpl.java               # User service implementation
│   │   │   │   ├── ElectionService.java               # Election service interface
│   │   │   │   ├── ElectionServiceImpl.java           # Election service implementation
│   │   │   │   ├── CandidateService.java              # Candidate service interface
│   │   │   │   ├── CandidateServiceImpl.java          # Candidate service implementation
│   │   │   │   ├── VoteService.java                   # Vote service interface
│   │   │   │   └── VoteServiceImpl.java               # Vote service implementation
│   │   │   ├── data/
│   │   │   │   ├── models/
│   │   │   │   │   ├── User.java                      # User entity
│   │   │   │   │   ├── Election.java                  # Election entity
│   │   │   │   │   ├── Candidate.java                 # Candidate entity
│   │   │   │   │   └── Vote.java                      # Vote entity
│   │   │   │   └── repositories/
│   │   │   │       ├── UserRepository.java            # User data access layer
│   │   │   │       ├── ElectionRepository.java        # Election data access layer
│   │   │   │       ├── CandidateRepository.java       # Candidate data access layer
│   │   │   │       └── VoteRepository.java            # Vote data access layer
│   │   │   ├── dtos/
│   │   │   │   ├── requests/
│   │   │   │   │   ├── UserRegistrationRequest.java
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   ├── LogoutRequest.java
│   │   │   │   │   ├── CreateElectionRequest.java
│   │   │   │   │   ├── UpdateElectionRequest.java
│   │   │   │   │   ├── AddCandidateRequest.java
│   │   │   │   │   └── CastVoteRequest.java
│   │   │   │   └── responses/
│   │   │   │       ├── ApiResponse.java               # Unified response wrapper
│   │   │   │       ├── UserRegistrationResponse.java
│   │   │   │       ├── LoginResponse.java
│   │   │   │       ├── LogoutResponse.java
│   │   │   │       ├── ElectionResponse.java
│   │   │   │       ├── CandidateResponse.java
│   │   │   │       └── VoteResponse.java
│   │   │   ├── enums/
│   │   │   │   ├── Role.java                          # USER, ADMIN
│   │   │   │   └── ElectionStatus.java                # OPEN, CLOSE
│   │   │   ├── exceptions/
│   │   │   │   ├── UserException.java                 # Base user exception
│   │   │   │   ├── DuplicateUserException.java
│   │   │   │   ├── InvalidCredentialsException.java
│   │   │   │   ├── UserNotLoggedInException.java
│   │   │   │   ├── ElectionException.java             # Base election exception
│   │   │   │   ├── ElectionNotFoundException.java
│   │   │   │   ├── ElectionNotActiveException.java
│   │   │   │   └── CandidateNotFoundException.java
│   │   │   └── utils/
│   │   │       └── Mapper.java                        # DTO/Entity mapping
│   │   └── resources/
│   │       └── application.properties                 # Configuration
│   └── test/
│       └── java/dreamDev/moniepoint/
│           └── services/
│               ├── UserServiceImplTest.java
│               ├── ElectionServiceImplTest.java
│               └── VoteServiceImplTest.java
├── pom.xml                                            # Maven configuration
└── README.md                                          # This file
```

## Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.11
  - Spring Web (REST API)
  - Spring Data MongoDB
- **Database:** MongoDB
- **Build Tool:** Maven
- **Dependencies:**
  - Lombok - Reduce boilerplate code
  - Spring Boot Test - Unit and integration testing
  - Spring Boot Web - Spring REST Controller
  - Spring Data MongoDB - MongoDB integration

## Testing

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=UserServiceImplTest
```

The project includes:
- **Unit Tests** - `UserServiceImplTest.java`, `ElectionServiceImplTest.java`, `VoteServiceImplTest.java`
- **Controller Tests** - `UserControllerTest.java`

## Contributing

We welcome contributions! To get started:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/YourFeature`
3. Commit your changes: `git commit -am 'Add YourFeature'`
4. Push to the branch: `git push origin feature/YourFeature`
5. Submit a Pull Request

### Code Guidelines

- Follow standard Java naming conventions
- Write unit tests for new features
- Ensure all tests pass before submitting a PR
- Use meaningful commit messages
- Add appropriate Javadoc comments

### Reporting Issues

Found a bug? Please create an issue on GitHub with:
- Clear description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Java and MongoDB versions

## Support

### Documentation

- For API documentation, see the [API Endpoints](#api-endpoints) section above
- For setup help, refer to [Getting Started](#getting-started)

### Community

- Create an issue on GitHub for bug reports and feature requests
- For questions, use GitHub Discussions

### Common Issues

**MongoDB Connection Failed:**
- Ensure MongoDB is running: `mongosh` or `mongo`
- Check connection string in `application.properties`
- Default URI: `mongodb://localhost:27017/election`

**Test Failures:**
- Ensure MongoDB is accessible during testing
- Clear Maven cache: `mvn clean`
- Check Java version: `java -version` (should be 21+)

**Election Already Closed:**
- Votes can only be cast on elections with status `OPEN`
- Once an election is ended (status `CLOSE`), no more votes can be cast

**Duplicate Vote Error:**
- Each user can only vote once per election
- Attempting to vote again will return an error

---

**Version:** 1.0-SNAPSHOT  
**Last Updated:** March 31, 2026  
**Maintainer:** Dream Dev Team
