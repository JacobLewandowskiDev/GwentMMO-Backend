# Gwent: The Card Game MMORPG Backend
## Description
The Gwent MMORPG is a full-stack multiplayer online RPG that allows players to engage in strategic card games based on the beloved Gwent from The Witcher 3. 
This backend application, built with Spring Boot, facilitates the game's logic for player creation, data validation and data tracking (player in-game movements, player selections, player scoreboards etc.).
The player to server interactions are handled via WebSockets, allowing for live updates of all the players whereabouts and in-game global chat function. Data management uses jOOQ to communicate with a PostgreSQL database for an extra layer of security from SQL injections while fetching/updating data within the database.

## Technologies Used
* Java: Primary programming language for the backend.
* Spring Boot: Framework for building the RESTful API.
* JOOQ: Library for type-safe SQL query construction and execution.
* PostgreSQL: Relational database management system for storing game data.
* Maven: Build automation tool used for managing dependencies and building the project.
* Git: Version control system for tracking changes in the codebase.

## Getting Started
### Prerequisites
Make sure you have the following installed on your machine:
* Java JDK 21,
* PostgreSQL,
* Maven,
* Git,

## Running the Application
  1. Clone the Repository
  2. Set up the PostgreSQL database:
    * Create a new database for the application.
    * Update the application.properties file in src/main/resources to include your database connection details:
    
    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

  3. Build the Project

    ```
    mvn clean install
    ```

  4. Run the Application

## API Documentation
The backend provides a RESTful API, which allows users to interact with eachother during their gameplay. 
You can find the API endpoints documented within the code or by using tools like Postman to test the endpoints.

## Front-end portion of the Application
This is merely the backend portion of the project. In order to see the codebase for the frontend of the application please follow the link below to the front-end repository:
https://github.com/JacobLewandowskiDev/GwentMMO-front
