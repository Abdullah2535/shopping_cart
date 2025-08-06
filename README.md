Shopping Cart API
A Spring Boot–based RESTful API for managing shopping carts, user authentication, and product orders.  
The project follows a layered architecture to ensure clean separation of concerns and adheres to modern backend development best practices.

Features

1) Authentication and Authorization
  - JSON Web Token (JWT)–based authentication  
  - Role-based access control ( Administrator, Customer)

2) Shopping Cart Management
  - Add, update, and remove items from a shopping cart  
  - Retrieve cart details including item information

  3) Product Management
  - Create, read, update, and delete (CRUD) operations for products

  4) Order Management
  - Convert cart contents into a customer order

  5) Database Migrations
  - Managed with Flyway for schema versioning

  6) Secure REST Endpoints
  - Configured with Spring Security


Technology Stack

1) Backend Framework: Spring Boot  
2) Database: MySQL  
3) ORM Framework: JPA / Hibernate  
4) Database Migrations: Flyway  
5) Authentication: JWT  
6) Build Tool: Maven  
7) Programming Language: Java 17   


Project Structure

shopping_cart/
│── src/main/java/com/shoppingcart

│ ├── config # Security and application configuration

│ ├── controller # REST controllers

│ ├── dto # Data Transfer Objects

│ ├── entity # JPA entities

│ ├── exception # Exception handling classes

│ ├── repository # Spring Data repositories

│ ├── security # JWT and authentication logic

│ ├── service # Business services

│ └── ShoppingCartApplication.java

│
│── src/main/resources

│ ├── application.yml # Application configuration

│ └── db/migration # Flyway migration scripts

│
└── pom.xml

