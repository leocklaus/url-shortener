# URL SHORTENER API built with SPRING [![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

Simple Java Spring API that allows users to get a shorter link for some URL.

## About this project

This project aims to build an API where users can input some URL, for example "https://github.com/leocklaus/url-shortener/tree/main" and turn it into a shorter URL like "mylink.com/a5d3g". Whenever a user navigates to "mylink.com/a5d3g", it should redirect to the original GitHub URL.

### The main goals for this project are:

* User can create an account
* User can log in and receive a JWT Token
* Logged users can submit a URL and receive as response a shorter one
* New URL must be persisted along with the original one
* The new URL should redirect to the original URL
* Every time a user accesses a shorter URL, a view count is added
* User can check how many access his URL has
* User can manage the URLs he owns, including deleting it

## How to run this project locally

1. Clone this project
2. Open the folder you just created on your favorite IDE
3. Install dependencies with Maven
4. Access local folder with `cd local`
5. Run `docker compose up`, which will create a mysql docker container.
6. Local MySQL variables are already configured on application.properties. You may change it if necessary
7. Start the project
8. Project will run on `http://localhost:8080`

## Documentation

This project has been fully documented with SpringDoc/Swagger. You can find all the endpoints, together with the expected inputs/outputs following the steps bellow.

### How to access documentation

1. Navigate to `http://localhost:8080/swagger-ui/index.html#/`
2. If you're trying to test some protected endpoints (marked with a padlock), make sure you authenticate before. A JWT will be necessary.
3. You can use create user endpoint to create a new user and login endpoint to get a JWT which will allow you to access protected endpoints.

## Technologies

* Java 21 and Spring Boot 3.3.2
* Spring Security and Spring Security Oauth2
* JWT authentication
* Spring WEB and Spring Data JPA
* Bean validation with Hibernate Validator
* Lombok
* SpringDoc/Swagger
* MySQL for the DB
*  GitHub Actions

### GitHub Actions Workflows

This API has a GitHub Actions Workflow that builds and tests the project every time something is pushed into `main` branch or evey time a PR is created.
You can find it on the folder workflows, file `maven.yml`, by the name of `Java CI with Maven`.

## Found a bug or want to contribute to this project?

If you've found a bug, make sure you open an Issue on this project repository. Also, all users are welcome to submit pull requests, but remember to mention on the PR which Issue are you fixing on it.

## Work in progress

I'm currently working on making the URL stats better, so the user can have not just the total access number, but also the access by day, month and year, which can be presented as a graphic on a future front end to be developed.


