package dreamDev.moniepoint.dtos.requests;

import lombok.Data;

@Data
public class AddCandidateRequest {
    private String name;
    private String party;
    private String electionId;
}