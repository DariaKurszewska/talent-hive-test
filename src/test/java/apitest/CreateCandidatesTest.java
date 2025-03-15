package apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Candidate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.CreateCandidateData;


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

        Candidate candidate = CreateCandidateData.generateCandidate();

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

        candidateId = response.path("id");

        System.out.println("New candidate created with ID: " + candidateId);
    }

    @AfterAll
    public static void cleanUp() {

        given()
                .when()
                .delete("/" + candidateId)
                .then()
                .statusCode(204)
                .log()
                .all();
    }
}
