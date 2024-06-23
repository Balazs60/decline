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

Java JDK
Docker Desktop

### Installation

1.  Clone the repository:

    ```
    git clone https://github.com/Balazs60/decline
    ```


2.  Find the `env_example` file in the `decline/decline-backend` folder and fill
    out the properties:

    ```
    POSTGRES_USER=
    POSTGRES_PASSWORD=
    POSTGRES_DB_NAME=
    ```

3.  Finally, rename the `env_example` file to `.env`.

4.  Navigate to the required directory:

    ```
    cd decline/decline-backend
    ```

5.  Run command:

    ```
    docker compose up
    ```

6.  Use the following port:

    <http://localhost:4200/>

## Author

Balázs Füredi

## Contact

LinkedIn: <https://www.linkedin.com/in/balázs60>
Email: f.balozs60@gmail.com
