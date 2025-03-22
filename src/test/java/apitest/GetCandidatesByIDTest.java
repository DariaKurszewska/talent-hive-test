package apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.Candidate;
import model.CreateCandidateData;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class GetCandidatesByIDTest {
    private static final String BASE_URL = "http://localhost:8080/api/v1/candidates";
    private Candidate candidate;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        candidate = CreateCandidateData.generateCandidate();
        createCandidateToRetrieve();
    }

    @AfterEach
    public void cleanUp() {
        log.info("Deleting candidate with ID: {}", candidate.getId());
        given()
                .when()
                .delete("/" + candidate.getId())
                .then()
                .statusCode(204)
                .log()
                .all();
    }

    @Test
    public void testGetCandidatesByID() {
        log.info("Retrieve candidate by ID: {}", candidate.getId());

        given()
                .contentType("application/json")
                .when()
                .get("/" + candidate.getId())
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("id", equalTo(candidate.getId()))
                .body("name", equalTo(candidate.getName()))
                .body("email", equalTo(candidate.getEmail()))
                .body("positionApplied", equalTo(candidate.getPositionApplied()))
                .body("status", equalTo(candidate.getStatus()))
                .body("interviewDate", equalTo(candidate.getInterviewDate()))
                .body("recruiter", equalTo(candidate.getRecruiter()));

        log.info("Candidate retrieved by ID: {}", candidate.getId());
    }

    private void createCandidateToRetrieve() {
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
