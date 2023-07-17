# Music Streamer Application Back-End Project

This is the repository for the Back-End project of the Music and Images application. This project was developed using Spring Boot, Java 17, and Maven, providing functionalities for uploading and downloading music and images, as well as the possibility of playing music. The database used is PostgreSQL, managed and versioned automatically by Flyway. Additionally, security is ensured through token-based authentication provided by Spring Security, and all endpoint documentation is available on Swagger.

## System Requirements

- Java 17 (JDK 17)
- Maven
- PostgreSQL

## How to Run the Project

1. Make sure you have Java 17 installed correctly and Maven configured in your environment.

2. Create a PostgreSQL database on your local server or in an appropriate environment. Take note of the connection information, such as URL, username, and password.

3. Clone this repository to your local machine:
   git clone https://github.com/VitorTenor/musicStreamerApplicationJavaApi.git


4. Update the database configurations in the `application.yml` file to connect the application to your PostgreSQL database.

5. Run the application using Maven or through your IDE.

6. The server will be running on the default port 8081. You can access the endpoint documentation through Swagger at the following link: `http://localhost:8081/swagger-ui.html`.

## Features

- Upload of music and images.
- Download of preloaded music and images.
- Music playback through specific endpoints.
- Security with token-based authentication through Spring Security.
- PostgreSQL database with automatic migration management and versioning using Flyway.

## Contributions

Contributions are welcome! If you have any suggestions, corrections, or new features to add, feel free to open a Pull Request. We will review and incorporate relevant contributions into the project.


