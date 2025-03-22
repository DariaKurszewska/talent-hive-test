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
        log.info("Creating candidate with data: {}", candidate);

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
