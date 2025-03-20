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
public class UpdateCandidatesTest {

    private static final String BASE_URL = "http://localhost:8080/api/v1/candidates";
    private Candidate candidate;
    private Candidate updateCandidate;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        candidate = CreateCandidateData.generateCandidate();
        createCandidateForUpdate();
    }

    public void createCandidateForUpdate() {
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

    @AfterEach
    public void cleanUp() {
        log.info("Deleting candidate with ID: {}", updateCandidate.getId());
        given()
                .when()
                .delete("/" + updateCandidate.getId())
                .then()
                .statusCode(204)
                .log()
                .all();
    }

    @Test
    public void testUpdateCandidate() {
        updateCandidate = CreateCandidateData.generateCandidate();
        updateCandidate.setId(candidate.getId());
        updateCandidate.setEmail(candidate.getEmail());

        log.info("Updating candidate with data: {}", updateCandidate.toString());

        given()
                .contentType("application/json")
                .body(updateCandidate)
                .when()
                .put("/" + updateCandidate.getId())
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("id", equalTo(updateCandidate.getId()))
                .body("name", equalTo(updateCandidate.getName()))
                .body("email", equalTo(updateCandidate.getEmail()))
                .body("positionApplied", equalTo(updateCandidate.getPositionApplied()))
                .body("status", equalTo(updateCandidate.getStatus()))
                .body("interviewDate", equalTo(updateCandidate.getInterviewDate()))
                .body("recruiter", equalTo(updateCandidate.getRecruiter()));

        log.info("Candidate updated with ID: {}", updateCandidate.getId());
    }
}
