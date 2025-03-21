# Talent Hive Service - API Testing

This repository contains automated tests for the [Talent Hive Service](https://github.com/kkurszewski/talent-hive-service) API. The purpose of this project is to practice API testing in Java using automated tools, as a next step after manual testing in Postman.

## Purpose of the Project
This project was created as part of my learning journey in API testing with Java. Initially, I performed manual API testing using Postman, and now I am developing my skills in automating REST API tests.

### What do I want to learn?
- Writing API tests in Java using RestAssured.
- Structuring tests with JUnit 5.
- Automating API tests as part of the CI/CD process.
- Integrating tests with an application running in Docker.

## Technologies Used
- **Java**
- **JUnit 5**
- **RestAssured**
- **Maven**

## Prerequisites
To run the tests successfully, you need to have the Talent Hive Service application running in a Docker container. Follow the instructions in the official repository to set up and start the application:

[Talent Hive Service Repository](https://github.com/kkurszewski/talent-hive-service)

Additionally, ensure that all dependencies in the `pom.xml` file are up to date. Check for the latest versions of libraries such as RestAssured, JUnit, and Lombok before running the tests.

## Running the Tests
To execute the tests, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/DariaKurszewska/talent-hive-test.git
   ```
2. Navigate to the project directory:
   ```bash
   cd talent-hive-test
   ```
3. Ensure that the Talent Hive Service application is running in Docker:
   ```bash
   docker-compose up -d
   ```
4. Run the tests using Maven:
   ```bash
   mvn test
   ```

## Implemented Tests
Currently, the test suite includes:

## Implemented Tests
Currently, the test suite includes:

- **POST /api/v1/candidates/create** – Happy path test for adding a new candidate and verifying the response.
- **PUT /api/v1/candidates/{id}** – Happy path test for updating candidate information and verifying the response.

## Example Test
Here is an example of a test that adds a new candidate:

```java
package apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.Candidate;
import model.CreateCandidateData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class CreateCandidatesTest {

    private static final String BASE_URL = "http://localhost:8080/api/v1/candidates";
    private Candidate candidate;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        candidate = CreateCandidateData.generateCandidate();
    }

    @AfterEach
    public void cleanUp() {
        given()
                .when()
                .delete("/" + candidate.getId())
                .then()
                .statusCode(204)
                .log()
                .all();
    }

    @Test
    public void testCreateCandidateAndExtractId() {
        log.info("Creating candidate with data: {}", candidate.toString());

        Response response = given()
                .contentType("application/json")
                .body(candidate)
                .when()
                .post("/create")
                .then()
                .log()
                .all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo(candidate.getName()))
                .body("email", equalTo(candidate.getEmail()))
                .body("positionApplied", equalTo(candidate.getPositionApplied()))
                .body("status", equalTo(candidate.getStatus()))
                .body("interviewDate", equalTo(candidate.getInterviewDate()))
                .body("recruiter", equalTo(candidate.getRecruiter()))
                .extract().response();

        candidate.setId(response.path("id"));

        log.info("New candidate created with ID: {}", candidate.getId());
    }
}
```

## Contribution
Feel free to contribute by adding more tests and improving the existing ones. Make sure to check and update dependencies in `pom.xml` before submitting a pull request.

## License
This project is licensed under the MIT License.
