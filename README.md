# Table of Contents

1. [Introduction](#intro)

2. [Authentication](#auth)

3. [Validation](#validation)

4. [Global Exception Handling](#geh)

5. [Logging](#log)

6. [Installation and Prequisites](#install)

<a name="intro"/>

# Introduction

**NOTE: The database used for the project is remote MySQL database, and the tables are empty.**

Welcome to the documentation for the ReadingIsGood Service API. 

ReadingIsGood is an online book retail firm that operates solely on the Internet. Their main goal is to provide customers with quick delivery of books from their centralized warehouse, typically within the same day. To achieve this, ReadingIsGood places a high priority on maintaining stock consistency. 

This API provides various functionalities to interact with the ReadingIsGood system, including registering new customers, placing orders, tracking book stock, viewing order details, and querying monthly statistics. This documentation outlines the endpoints available and their respective request and response formats.

The API is implemented using Java Spring Boot, and utilizes a MySQL database for storing data. The API supports the following features:

* Registering New Customer
* Placing a new order
* Tracking the stock of books
* List all orders of the customer
* Viewing the order details
* Query Monthly Statistics

Before a customer can use the service, they must first register and log in to the system. Once authenticated, the customer will receive a JWT token that will be used to authenticate all subsequent requests.

This document outlines the structure and provides information about the API, This is not a **API specification**.
You can download and build the project and read the documentation provided by **Swagger** (more information below).

<a name="auth"/>

# Authentication
In order to use the API, customers must authenticate themselves with valid credentials.

Customers can register themselves by sending a POST request to the `/api/auth/register` endpoint with their email, username and password. After a successful registration, Customers must then send a POST request to the `/api/auth/login` endpoint with their registered credentials to receive a JWT token, which must be included in the Authorization header of all subsequent requests. If a Customer attempts to access a protected endpoint without a valid JWT token, they will receive an error response.

## Register
To register a new client, use the following endpoint:

**Endpoint: `/api/auth/register`**

**HTTP Verb: `POST`**

**Headers:**

* **Content-Type: `application/json`**

**Request body:**

```json
{
    "username" : "johndoe",
    "email" : "johdoe@gmail.com",
    "password" : "password123"
}
```

**Response:**

```json
{
    "message": "johdoe@gmail.com registered successfully, please login using your credentials to generate a token."
}

```

After successful registration, clients need to log in to the service to generate a JWT token.

## Login

To log in to the service and generate a JWT token, use the following endpoint:

**Endpoint: `/api/auth/login`**

**HTTP Verb: `POST`**

**Headers:**

* **Content-Type: `application/json`**

**Request body:**

```json
{
  "username": "johndoe",
  "password": "password123"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...<jwt token>"
}

```

<a name="validation"/>

# Validation

The API validates all incoming requests, whether the information is passed through request parameters or request bodies, using the *Jakarta*, Any values that are considered invalid are reported and are responded to the client with appropriate error messages.

**Example:**

Here is an example of aninvalid submission to the endpoint `api/service/book` to create a book is as follows:


```json
{
    "title" : "Lost in the Wind",
    "author" : "Derek Roberts",
    "quantityInStock" : -2,
    "totalPages" : 322
}
```

**Example Response:**

When the API detects invalid data, it returns an error response to the client, including the current timestamp, an error message, and a detailed list of specific errors:

```json
{
    "time": "2023-04-06T03:34:31.8785142",
    "error": "Constraint Validation Failed",
    "errors": {
        "quantityInStock": "must be greater than or equal to 0",
        "price": "must not be null"
    }
}
```

<a name="geh"/>

# Global Exception Handling

To ensure that the API can handle any exceptions that may occur during runtime, a global exception handling mechanism has been implemented in the codebase. This mechanism ensures that any unhandled exceptions are caught and dealt with in a standardized manner across the API.

Here is an example that handles all of the exceptions that occur during authentication:

```java
    @ExceptionHandler(value = {JwtException.class,
            AuthenticationException.class, InsufficientAuthenticationException.class})
    public ResponseEntity<Object> jwtExceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception e){
        log.error("exception",e);
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .error(e.getMessage())
                        .time(LocalDateTime.now())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }
```

<a name="log"/>

# Logging

The API logs any changes that occur on an entity using the EntityListeners annotation and passes them down to the relevant listener class for the entity. The listener class then logs the change with information on who made the change and when it was made.

For example, if a customer places an order to buy a book, the following log message will be generated:

*johndoe placed an order for 2 book(s) and paid 29.99$in total - 2023-04-06T03:48:59.532697100*

This allows for easy tracking and monitoring of changes made to entities within the API.

<a name="install"/>

# Open API Specification

he API documentation is generated using Swagger, which is implemented using the springdoc library. Once you have installed and built the source code(More info in the next section), you can access the API documentation from `localhost:8080/swagger-ui.html`.

The Swagger UI provides a web-based graphical interface that enables users to easily explore and interact with the API. It displays all the available endpoints, methods, input/output parameters, and response types. Additionally, the Swagger UI provides a live testing feature, which allows users to test the API endpoints directly from the documentation page.

The documentation is automatically updated whenever changes are made to the API codebase.

# Installation and Prerequisites

Before you begin, make sure you can satisfy the following versions:

* Java version 17
* Spring version 3.0.5
* Apache maven 3.8.1

## Download and Build the Code

1. Clone the repository to your local machine using Git:
```console
git clone https://github.com/dogukanozdemir/ReadingIsGood.git
```
2. Navigate to the root directory of the project:

```console
cd ReadingIsGood
```

3. Build the project using Maven:

**THIS STEP IS CRUCIAL TO USE DOCKER, WITHOUT THE GENERATION OF THE .JAR FILE, DOCKER WON'T BE ABLE BUILD**

```console
mvn clean install
```

This will download all the necessary dependencies and build the project.

Open the IDE of your choice to run or edit the program. Intellij IDEA is highly recommended

## Docker

Before starting with this step make sure you have followed all the steps above and produced a jar file inside the project folder
in the directory `/target/readingisgood-1.0.jar`

To build and run the project using Docker, first navigate to the project directory where the Dockerfile is placed.
Run the following command

```console
docker build -t readingapi .
```

This command builds a Docker image for the ReadingIsGood project, with a tag name of "readingapi".
Once the image is successfully built, you can run the application inside a Docker container. 
While still being in the project directory, Use the following command:

```console
docker run -p 8080:8080 readingapi
```

This command starts the container and maps port 8080 of the container to port 8080 of the host machine, allowing you to access the application through localhost:8080.


# License

This project is licensed under the terms of the MIT license.





  
  
