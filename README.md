# E-commerce Shopping Cart Microservice

This Spring Boot microservice is a backend application designed to manage the lifecycle of a shopping cart. It handles all cart-related operations, from adding and removing items to processing payments, built on a robust and scalable microservice architecture.

## Key Technologies

The project leverages the following technologies to ensure high performance, flexibility, and scalability:

  * **Spring Boot**: The core framework for building a standalone, production-grade Spring-based application.
  * **MongoDB**: Used as the primary data store for cart information, offering a flexible and scalable NoSQL solution.
  * **Redis**: Implemented as a caching layer to manage real-time cart state, significantly improving performance and reducing database load.
  * **OpenFeign**: A declarative REST client used for inter-service communication, simplifying API calls to other microservices (e.g., a payment or product service).
  * **Docker & Docker Compose**: Used to containerize the application and its dependencies (MongoDB, Redis), providing a consistent and portable development and deployment environment.

## Features

The service provides a set of RESTful endpoints to handle all shopping cart functionalities:

  * **Create Cart**: Initialize a new shopping cart for a user.
  * **Add Item**: Add a product to an existing cart.
  * **Update Item**: Modify the quantity of an item in the cart.
  * **Remove Item**: Remove a specific item from the cart.
  * **View Cart**: Retrieve the contents and total value of a cart.
  * **Pay Basket**: Process the payment for a cart, marking it as paid and finalizing the transaction.

## Getting Started

To run this project locally, you need to have Docker and Docker Compose installed.

1.  **Clone the Repository:**

    ```bash
    git clone [YOUR_REPO_URL]
    cd [project-folder]
    ```

2.  **Run with Docker Compose:**
    The `docker-compose.yml` file will handle starting the application, MongoDB, and Redis containers for you.

    ```bash
    docker-compose up --build
    ```

    The application will be accessible at `http://localhost:8080`.

## 🤝 Contributing

We welcome contributions\! If you have any suggestions for new features, code improvements, or bug fixes, please feel free to open an issue or submit a pull request.

-----

*This project serves as a demonstration of microservice architecture principles using modern Java and Spring Boot technologies.*
