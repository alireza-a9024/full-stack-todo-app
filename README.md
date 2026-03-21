# Full-Stack Task Management System (To-Do App) 

A complete, containerized task management application built with Spring Boot, Angular, and PostgreSQL. This project features a secure REST API with JWT authentication and a Single Page Application (SPA) frontend, orchestrated via Docker Compose.

##  Architecture & Tech Stack

* **Backend:** Java, Spring Boot, Spring Security (JWT), JPA
* **Frontend:** Angular, TypeScript, Nginx (for serving and SPA routing)
* **Database:** PostgreSQL, pgAdmin (for database management)
* **DevOps:** Docker, Docker Compose

##  Getting Started

This project is fully containerized. You do not need to install Java, Node.js, Angular CLI, or PostgreSQL on your local machine to run it—everything is packaged and runs inside Docker.

### Prerequisites

* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (or Docker Engine) installed and running.

### Installation & Running the App

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/alireza-a9024/full-stack-todo-app.git
    ```

2.  **Navigate to the root folder:**
    ```bash
    cd full-stack-todo-app
    ```

3.  **Build and spin up the containers:**
    Open your terminal (CMD, PowerShell, or Bash) in the root folder of the project and run the following command:
    ```bash
    docker-compose up --build
    ```
    *(Note: The `--build` flag ensures that both the Spring Boot `.jar` and the Angular production build are freshly compiled).*

4.  **Access the application:**
    Once the terminal shows that all containers are running successfully, you can access the services here:
    * **Frontend UI (Angular):** `http://localhost:4200`
    * **Backend API (Spring Boot):** `http://localhost:8080`
    * **Database Admin (pgAdmin):** `http://localhost:5050` (Login: `admin@admin.com` / `123`)

##  Security

The backend secures all private endpoints using JSON Web Tokens (JWT). The authentication flow is completely stateless. Upon successful login, the server issues a JWT, which the Angular frontend stores and automatically attaches as a `Bearer` token to the `Authorization` header for all subsequent API requests. 

---
*Developed by Alireza Asgari*