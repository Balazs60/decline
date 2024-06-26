# Decline

## About the project

Decline is an application intended to practice German adjective declension.

The idea came to me because I couldn't find another application in this topic
that works in this way. This project is still in the early stages of
development. I am using Java, Spring, PostgreSQL, Typescript, Angular, and
Tailwind CSS technologies.

## Built with

* Java
* Spring
* Postgresql
* TypeScript
* Angular
* Tailwind CSS

## Getting started

### Prerequisites

* Docker Desktop, or Docker with Docker Compose.

### Installation

1.  Clone the repository:

    ```
    git clone https://github.com/Balazs60/decline
    ```

2.  Navigate to the `decline/decline-backend` directory and create a copy from
    `env_example` called `.env`.

3.  Fill out the variables in `.env` with values of your choice:

    ```
    POSTGRES_USER=
    POSTGRES_PASSWORD=
    POSTGRES_DB_NAME=
    ```

4.  Build the Docker images, and start the Docker containers:

    ```
    docker compose up
    ```

5.  Open the following page in a web browser:

    <http://localhost:4200/>

## Author

Balázs Füredi

## Contact

LinkedIn: <https://www.linkedin.com/in/balázs60>
Email: f.balozs60@gmail.com
