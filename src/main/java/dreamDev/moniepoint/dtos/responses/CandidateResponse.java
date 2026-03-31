package dreamDev.moniepoint.dtos.responses;

import lombok.Data;

@Data
public class CandidateResponse {
    private String id;
    private String name;
    private String party;
    private Integer voteCount;
    private String electionId;
}