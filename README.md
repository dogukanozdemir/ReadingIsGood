# Introduction
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

This document outlines the API endpoints, HTTP verbs, headers, responses, and tests that are used in this project.
Note: if you want to skip this section, you can download and build the project and read the documentation provided by Swagger.

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
  "username": "johndoe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...<jwt token>"
}

```

# Service API Endpoints
The following API endpoints are supported by the service?

## Create a new product

**Endpoint: `/product`**

**HTTP Verb: `POST`**

**Headers:**

* *Content-Type: `application/json`*
* *Authorization: `Bearer <jwt token>`*

**Request body:**

```json
{
    "name" : "Led TV 4K 3D",
    "description" : "4K TV with 3D glasses",
    "color" : "Black",
    "brand" : "Samsung",
    "price" : 500
}
```

**Response:**

```json
{
    "id": 102,
    "clientId": 1,
    "name": "Led TV 4K 3D",
    "description": "4K TV with 3D glasses",
    "price": 500.0,
    "brand": "Samsung",
    "color": "Black"
}
```

## Modify a product

**Endpoint: `/product/{id}`**

**HTTP Verb: `PUT`**

**Headers:**

* **Content-Type: `application/json`**
* **Authorization: `Bearer <jwt token>`**
  
**Request body:**
  
```json
{
    "name" : "LED TV",
    "description" : "4K TV with 3D glasses, discounted",
    "color" : "Red",
    "brand" : "Samsung",
    "price" : 999.99
}
```

**Response: **

```json
{
    "id": 102,
    "clientId": 1,
    "name": "LED TV",
    "description": "4K TV with 3D glasses, discounted",
    "price": 999.99,
    "brand": "Samsung",
    "color": "Red"
}
```

## Delete a product
**Endpoint: `/product/{id}`**

**HTTP Verb: `DELETE`**

**Headers:**

* **Authorization: `Bearer <jwt token>`**

**Response:**
  
```json
{
    "message": "Product with id 102 has been deleted"
}
```

## View all products

**Endpoint: `/products`**

**HTTP Verb: `GET`**

**Headers:**

* **Authorization: Bearer `<jwt token>`**

**Response:**

```json 
[
    {
        "id": 1,
        "clientId": 1,
        "name": "Led TV",
        "description": "4K TV with 3D glasses",
        "price": 500.0,
        "brand": "Samsung",
        "color": "Black"
    },
    {
        "id": 2,
        "clientId": 1,
        "name": "Water gun",
        "description": "Water gun to splash your friends!",
        "price": 29.99,
        "brand": "Toys R us",
        "color": "Blue"
    }
]
```

# Installation and Prerequisites

Before you begin, make sure you can satisfy the following versions:

* Java version 17
* Spring version 3.0.4
* Apache maven 3.8.1

## Download and Build the Code

1. Clone the repository to your local machine using Git:
```console
git clone https://github.com/dogukanozdemir/Product-App.git
```
2. Navigate to the root directory of the project:

```console
cd Product-App
```

3. Build the project using Maven:

```console
mvn clean install
```

This will download all the necessary dependencies and build the project.

Open the IDE of your choice to run or edit the program. Intellij IDEA is highly recommended

# License

This project is licensed under the terms of the MIT license.





  
  
