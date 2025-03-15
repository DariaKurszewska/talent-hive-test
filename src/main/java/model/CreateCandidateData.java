package model;

import com.github.javafaker.Faker;
import model.Candidate;

public class CreateCandidateData {

    private static final Faker faker = new Faker();

    public static Candidate generateCandidate() {
        Candidate candidate = new Candidate();

        candidate.setName(faker.name().fullName());
        candidate.setEmail(faker.internet().emailAddress());
        candidate.setPositionApplied(faker.job().title());
        candidate.setStatus("Interview Scheduled");
        candidate.setInterviewDate("2025-12-31T10:00:00.000+00:00");
        candidate.setRecruiter(faker.name().fullName());

        return candidate;
    }
}
