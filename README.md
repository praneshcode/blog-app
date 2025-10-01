# Blog Application Backend

A RESTful API for creating, reading, and managing posts/blogs. Developed using Spring Boot, this REST API provides fast and secure access to all the functionality you need.

## Built With
- [Spring Boot](https://spring.io/)
- [Maven](https://maven.apache.org/)
- [JPA](https://spring.io/projects/spring-data-jpa)
- [MySQL](https://www.mysql.com/)

## Overview
Users can create posts, each associated with a category or topic. The API provides signup and login functionality for users, with password hashing for security. Users can fetch posts from other users or view all posts under a particular topic. Data validation is applied on create/update endpoints.

## Features
- Posts CRUD
- Users CRUD
- Category CRUD
- Comments on posts CRUD
- Post sorting
- Role-based authentication
- Custom exception handling
- JWT authentication
- DTO pattern
- Post searching by keyword
- Data validation
- Documentation using Swagger
