package apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCandidatesTest {

    private static final String BASE_URL = "http://localhost:8080/api/v1/candidates";
    private static int candidateId;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreateCandidateAndExtractId() {

        String candidateData;

        try {
            candidateData = new String(Files.readAllBytes(Paths.get("src/test/resources/create-candidate.json")));
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        Response response = given()
                .contentType("application/json")
                .body(candidateData)
                .when()
                .post("/create")
                .then()
                .log()
                .all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("John Doe"))
                .body("email", equalTo("johndoe@example.com"))
                .body("positionApplied", equalTo("Software Engineer"))
                .body("status", equalTo("Interview Scheduled"))
                .body("interviewDate", equalTo("2024-12-31T10:00:00.000+00:00"))
                .body("recruiter", equalTo("Jane Smith"))
                .extract().response();

        candidateId = response.path("id");

        System.out.println("New candidate created with ID: " + candidateId);
    }

    @AfterAll
    public static void cleanUp() {

        Response response = given()
                .when()
                .delete("/" + candidateId)
                .then()
                .statusCode(204)
                .log()
                .all()
                .extract()
                .response();
    }
}
