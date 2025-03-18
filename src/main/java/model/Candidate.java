package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.google.gson.annotations.SerializedName;

@Getter
@Setter
@ToString
public class Candidate {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("positionApplied")
    private String positionApplied;

    @SerializedName("status")
    private String status;

    @SerializedName("interviewDate")
    private String interviewDate;

    @SerializedName("recruiter")
    private String recruiter;
}
