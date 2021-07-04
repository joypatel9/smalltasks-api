# SmallTasks API

An opensource REST API for a marketplace of small tasks, where users can post local tasks that other users can see and
execute. As of now, this has only basic features. But the idea was to serve as a model for developing Secure Stateless
REST APIs using Spring Boot. E.g., currently it demonstrates how to

1. Develop a stateless REST API using JWT authentication
1. Configure Spring Security to suit API development, e.g., returning 200 or 401 responses on login, configuring CORS,
   etc.
1. Elegantly do validations and exceptions and send precise errors to the client
1. Code automated unit tests using Mockito
1. Code automated integration using Spring MVC test and in-memory DB
1. Use querydsl to code dynamic queries
1. Code small, modular classes, following clean coding practices
