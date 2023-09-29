# Online Bookstore API

## Overview
This project is a RESTful API for an online bookstore, implemented using Java and Spring Boot. The API provides essential functionalities for both users and bookstore admin.

## Entities
1. **Book**
   - ISBN (unique identifier)
   - Title
   - Author
   - Price
   - Stock Quantity
   - CreatedAt
   - UpdatedAt
   
2. **User**
   - ID
   - Name
   - Email
   - Password (encrypted)
   - CreatedAt
   - UpdatedAt
   
3. **Order**
   - Order ID
   - User ID
   - Total Price
   - Order Request
   - Order Date
   - CreatedAt
   - UpdatedAt

## API Endpoints
### Book
- `GET /books`: Retrieve a list of all books ordered by creation date DESC.
- `GET /books/{isbn}`: Retrieve details of a book by ISBN.
- `POST /books`: Add a new book (Admin only).
- `PUT /books/{isbn}`: Update details of a book (Admin only).


### User
- `POST /users/signup`: Register a new user.
- `POST /users/login`: Authenticate a user and return a token (JWT preferred).

### Order
- `POST /orders`: Place a new order for a user with a minimum price of $25.
- `GET /orders/{userId}`: Get all orders for a specific user ordered by update date DESC.

## Features
- Implements Spring Boot's layered architecture: Controller, Service, Entity.
- Provides security measures: Passwords are securely hashed, and only authenticated users can place orders. Admin operations require admin privileges.
- Integrates with a database using Spring Data JPA (PostgreSQL).
- Includes basic error handling (e.g., Book not found, Insufficient stock, Unauthorized user, Minimum Price For Order).

## Bonus Features
- Pagination for the `GET /books` endpoint.
- Swagger UI integration.
- Simple rate-limiting mechanism to prevent abuse.
- Unit and integration tests using JUnit and MockMvc.



