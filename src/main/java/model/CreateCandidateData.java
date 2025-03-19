package model;

import com.github.javafaker.Faker;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CreateCandidateData {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");


    public static Candidate generateCandidate() {
        Candidate candidate = new Candidate();
        candidate.setName(faker.name().fullName());
        candidate.setEmail(faker.internet().emailAddress());
        candidate.setPositionApplied(faker.job().title());

        String status = generateStatus();
        candidate.setStatus(status);

        candidate.setInterviewDate(generateFutureDate());

        candidate.setRecruiter(faker.name().fullName());

        return candidate;
    }

    private static String generateStatus() {
        return random.nextBoolean() ? "Interview Scheduled" : "Not Scheduled";
    }

    private static String generateFutureDate() {
        ZonedDateTime futureDate = ZonedDateTime.now(ZoneId.of("UTC")).plusDays(random.nextInt(22) + 7);

        // Formatting the date and manually appending the "+00:00" timezone offset
        // This is done manually to ensure the time zone is set as "+00:00" instead of "Z"
        // which could lead to a defect or mismatch when comparing with expected results.
        return futureDate.format(formatter) + "+00:00";

    }
}
