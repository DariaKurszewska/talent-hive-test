package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Candidate {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("positionApplied")
    private String positionApplied;

    @JsonProperty("status")
    private String status;

    @JsonProperty("interviewDate")
    private String interviewDate;

    @JsonProperty("recruiter")
    private String recruiter;
}
